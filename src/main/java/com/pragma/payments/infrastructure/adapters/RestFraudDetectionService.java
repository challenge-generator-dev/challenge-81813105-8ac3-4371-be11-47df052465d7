package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.FraudDetectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
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
import java.util.function.Supplier;

@Component
public class RestFraudDetectionService implements FraudDetectionService {

    private static final Logger log = LoggerFactory.getLogger(RestFraudDetectionService.class);
    private static final String DEFAULT_FRAUD_ASSESSMENT = "APPROVED";

    private final WebClient webClient;
    private final String fraudServiceBaseUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public RestFraudDetectionService(
            WebClient.Builder webClientBuilder,
            @Value("${app.services.fraud-detection.base-url:http://localhost:8081}") String fraudServiceBaseUrl,
            @Value("${app.services.fraud-detection.timeout.connection:5000}") int connectionTimeout,
            @Value("${app.services.fraud-detection.timeout.read:3000}") int readTimeout) {
        this.webClient = webClientBuilder
                .baseUrl(fraudServiceBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.fraudServiceBaseUrl = fraudServiceBaseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    @CircuitBreaker(name = "fraudDetectionCircuitBreaker", fallbackMethod = "assessFraudRiskFallback")
    @Retry(name = "fraudDetectionRetry")
    public Mono<String> assessFraudRisk(Payment payment) {
        log.info("Assessing fraud risk for payment: {}", payment.transactionReference());

        Map<String, Object> requestBody = buildFraudAssessmentRequest(payment);

        Supplier<Mono<String>> fraudAssessmentSupplier = () -> webClient.post()
                .uri("/api/v1/fraud-assessment")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Fraud detection service returned 4xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Fraud assessment rejected by service"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Fraud detection service returned 5xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Fraud detection service unavailable"));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(result -> log.info("Fraud assessment result for {}: {}", payment.transactionReference(), result))
                .doOnError(e -> log.error("Fraud assessment failed for {}: {}", payment.transactionReference(), e.getMessage()));

        return fraudAssessmentSupplier.get();
    }

    @Override
    public Mono<String> assessFraudRiskWithFallback(Payment payment, Throwable error) {
        log.warn("Using fallback for fraud assessment due to: {}", error.getMessage());
        return Mono.just(DEFAULT_FRAUD_ASSESSMENT);
    }

    private Mono<String> assessFraudRiskFallback(Payment payment, Throwable error) {
        log.error("Circuit breaker fallback triggered for fraud detection: {}", error.getMessage());
        return Mono.just(DEFAULT_FRAUD_ASSESSMENT);
    }

    private Map<String, Object> buildFraudAssessmentRequest(Payment payment) {
        return Map.of(
                "transactionReference", payment.transactionReference(),
                "amount", payment.amount().toString(),
                "currency", payment.currency(),
                "payerId", payment.payerId(),
                "payeeId", payment.payeeId(),
                "paymentMethod", payment.paymentMethod(),
                "channel", payment.channel()
        );
    }
}