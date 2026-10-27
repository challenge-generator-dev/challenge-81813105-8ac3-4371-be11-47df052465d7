package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.RiskAssessmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Component
public class RestRiskAssessmentService implements RiskAssessmentService {

    private static final Logger log = LoggerFactory.getLogger(RestRiskAssessmentService.class);
    private static final String DEFAULT_RISK_ASSESSMENT = "MEDIUM_RISK";
    private static final String TIMEOUT_RISK_ASSESSMENT = "HIGH_RISK";

    private final WebClient webClient;
    private final String riskServiceBaseUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public RestRiskAssessmentService(
            WebClient.Builder webClientBuilder,
            @Value("${app.services.risk-assessment.base-url:http://localhost:8082}") String riskServiceBaseUrl,
            @Value("${app.services.risk-assessment.timeout.connection:5000}") int connectionTimeout,
            @Value("${app.services.risk-assessment.timeout.read:5000}") int readTimeout) {
        this.webClient = webClientBuilder
                .baseUrl(riskServiceBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.riskServiceBaseUrl = riskServiceBaseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    @CircuitBreaker(name = "riskAssessmentCircuitBreaker", fallbackMethod = "assessRiskFallback")
    @TimeLimiter(name = "riskAssessmentTimeLimiter", fallbackMethod = "assessRiskTimeoutFallback")
    @Retry(name = "riskAssessmentRetry")
    public Mono<String> assessRisk(Payment payment) {
        log.info("Assessing risk for payment: {} with timeout: {}ms", payment.transactionReference(), readTimeout);

        Map<String, Object> requestBody = buildRiskAssessmentRequest(payment);

        Supplier<Mono<String>> riskAssessmentSupplier = () -> webClient.post()
                .uri("/api/v1/risk-assessment")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Risk assessment service returned 4xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Risk assessment rejected by service"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Risk assessment service returned 5xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Risk assessment service unavailable"));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(result -> log.info("Risk assessment result for {}: {}", payment.transactionReference(), result))
                .doOnError(e -> log.error("Risk assessment failed for {}: {}", payment.transactionReference(), e.getMessage()));

        return riskAssessmentSupplier.get();
    }

    @Override
    public Mono<String> assessRiskWithFallback(Payment payment, Throwable error) {
        if (error instanceof TimeoutException || error.getCause() instanceof TimeoutException) {
            log.warn("Timeout detected in risk assessment for payment: {}", payment.transactionReference());
            return Mono.just(TIMEOUT_RISK_ASSESSMENT);
        }
        log.warn("Using fallback for risk assessment due to: {}", error.getMessage());
        return Mono.just(DEFAULT_RISK_ASSESSMENT);
    }

    private Mono<String> assessRiskFallback(Payment payment, Throwable error) {
        log.error("Circuit breaker fallback triggered for risk assessment: {}", error.getMessage());
        return Mono.just(DEFAULT_RISK_ASSESSMENT);
    }

    private Mono<String> assessRiskTimeoutFallback(Payment payment, Throwable error) {
        log.error("TimeLimiter fallback triggered for risk assessment - timeout exceeded for payment: {}", payment.transactionReference());
        return Mono.just(TIMEOUT_RISK_ASSESSMENT);
    }

    private Map<String, Object> buildRiskAssessmentRequest(Payment payment) {
        return Map.of(
                "transactionReference", payment.transactionReference(),
                "amount", payment.amount().toString(),
                "currency", payment.currency(),
                "payerId", payment.payerId(),
                "payeeId", payment.payeeId(),
                "paymentMethod", payment.paymentMethod(),
                "channel", payment.channel(),
                "accountType", payment.accountType(),
                "merchantCategory", payment.merchantCategory()
        );
    }
}