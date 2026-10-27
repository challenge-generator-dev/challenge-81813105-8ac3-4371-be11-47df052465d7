package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.BankingCoreService;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.infrastructure.dto.PaymentRequest;
import com.pragma.payments.infrastructure.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RestBankingCoreService implements BankingCoreService {

    private static final Logger log = LoggerFactory.getLogger(RestBankingCoreService.class);

    private final WebClient webClient;
    private final String baseUrl;
    private final Duration timeout;

    public RestBankingCoreService(
            @Value("${app.banking-core.base-url:http://localhost:8081}") String baseUrl,
            @Value("${app.banking-core.timeout-ms:5000}") int timeoutMs) {
        this.baseUrl = baseUrl;
        this.timeout = Duration.ofMillis(timeoutMs);
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Mono<String> processPayment(Payment payment) {
        log.info("Enviando pago al core bancario: transactionReference={}, amount={}",
                payment.transactionReference(), payment.amount());

        PaymentRequest request = PaymentRequest.builder()
                .transactionReference(payment.transactionReference())
                .amount(payment.amount())
                .currency(payment.currency())
                .sourceAccount(payment.sourceAccount())
                .destinationAccount(payment.destinationAccount())
                .paymentType(payment.paymentType())
                .build();

        return webClient.post()
                .uri("/api/v1/payments")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(
                                        new PaymentProcessingException("Error del core bancario: " + error))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(
                                        new PaymentProcessingException("Fallo interno del core bancario: " + error))))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(response -> {
                    log.info("Respuesta del core bancario: reference={}, status={}",
                            response.reference(), response.status());
                    return response.status();
                })
                .doOnError(error -> log.error("Error al procesar pago en core bancario: {}", error.getMessage()));
    }

    @Override
    public Mono<String> confirmPayment(String transactionReference) {
        log.info("Confirmando pago en core bancario: {}", transactionReference);

        return webClient.post()
                .uri("/api/v1/payments/{reference}/confirm", transactionReference)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new PaymentProcessingException("Error al confirmar pago")))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(PaymentResponse::status)
                .doOnError(error -> log.error("Error al confirmar pago: {}", error.getMessage()));
    }

    @Override
    public Mono<String> reversePayment(String transactionReference, String reason) {
        log.info("Revirtiendo pago en core bancario: reference={}, reason={}",
                transactionReference, reason);

        return webClient.post()
                .uri("/api/v1/payments/{reference}/reverse", transactionReference)
                .bodyValue(java.util.Map.of("reason", reason))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new PaymentProcessingException("Error al revertir pago")))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(PaymentResponse::status)
                .doOnError(error -> log.error("Error al revertir pago: {}", error.getMessage()));
    }
}