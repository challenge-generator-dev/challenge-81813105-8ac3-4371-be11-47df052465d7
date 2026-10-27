package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

public interface FraudDetectionService {
    Mono<String> assessFraudRisk(Payment payment);

    default Mono<String> assessFraudRiskWithFallback(Payment payment, Throwable error) {
        return Mono.just("FALLBACK: Fraud detection unavailable");
    }
}