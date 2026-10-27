package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    Optional<Payment> findByTransactionReference(String transactionReference);

    void deleteById(UUID id);
}