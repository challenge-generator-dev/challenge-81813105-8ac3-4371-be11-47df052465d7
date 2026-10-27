package com.pragma.payments.application.queries;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Component
public class GetPaymentQueryHandler {

    private static final Logger log = LoggerFactory.getLogger(GetPaymentQueryHandler.class);

    private final PaymentRepository paymentRepository;

    public GetPaymentQueryHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Mono<Payment> handle(GetPaymentQuery query) {
        log.info("Handling GetPaymentQuery for paymentId: {}", query.paymentId());

        return Mono.fromCallable(() -> paymentRepository.findById(query.paymentId()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(paymentOpt -> {
                    if (paymentOpt.isEmpty()) {
                        log.warn("Payment not found with id: {}", query.paymentId());
                        return Mono.empty();
                    }
                    Payment payment = paymentOpt.get();
                    log.debug("Payment found: {} with status: {}", payment.id(), payment.status());
                    return Mono.just(payment);
                })
                .onErrorResume(e -> {
                    log.error("Error retrieving payment {}: {}", query.paymentId(), e.getMessage(), e);
                    return Mono.error(e);
                });
    }

    public Mono<Payment> handleByTransactionReference(String transactionReference) {
        log.info("Handling GetPaymentQuery for transactionReference: {}", transactionReference);

        return Mono.fromCallable(() -> paymentRepository.findByTransactionReference(transactionReference))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(paymentOpt -> {
                    if (paymentOpt.isEmpty()) {
                        log.warn("Payment not found with transactionReference: {}", transactionReference);
                        return Mono.empty();
                    }
                    Payment payment = paymentOpt.get();
                    log.debug("Payment found: {} with status: {}", payment.id(), payment.status());
                    return Mono.just(payment);
                })
                .onErrorResume(e -> {
                    log.error("Error retrieving payment by reference {}: {}", transactionReference, e.getMessage(), e);
                    return Mono.error(e);
                });
    }
}