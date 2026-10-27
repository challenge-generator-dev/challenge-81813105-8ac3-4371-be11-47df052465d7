package com.pragma.payments.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Payment(
    @NotNull
    UUID id,

    @NotNull
    @Positive
    BigDecimal amount,

    @NotBlank
    @Size(max = 3)
    String currency,

    @NotNull
    UUID customerId,

    @NotNull
    UUID orderId,

    @NotBlank
    @Size(max = 50)
    String paymentMethod,

    @NotBlank
    @Size(max = 20)
    String status,

    @NotNull
    LocalDateTime createdAt,

    LocalDateTime updatedAt,

    @NotBlank
    @Size(max = 50)
    String transactionReference,

    @NotNull
    @PositiveOrZero
    Integer retryCount,

    String fraudDetectionResult,

    String riskAssessmentResult,

    String bankingCoreResult
) {
    public Payment {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be a 3-letter code");
        }
        if (retryCount == null) {
            retryCount = 0;
        }
        if (status == null) {
            status = "PENDING";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Payment withStatus(String newStatus) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public Payment withFraudDetectionResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount,
            result,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public Payment withRiskAssessmentResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            result,
            this.bankingCoreResult
        );
    }

    public Payment withBankingCoreResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            result
        );
    }

    public Payment incrementRetryCount() {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount + 1,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public boolean isApproved() {
        return "APPROVED".equals(this.status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(this.status);
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }
}