package com.pragma.payments.infrastructure.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO que representa la respuesta de un pago procesado.
 * Contiene toda la información relevante del pago incluyendo estado,
 * identificadores y timestamps para trazabilidad.
 */
public record PaymentResponse(
    UUID id,
    String transactionReference,
    String status,
    BigDecimal amount,
    String currency,
    String sourceAccount,
    String destinationAccount,
    String channel,
    String description,
    String fraudDetectionResult,
    String riskAssessmentResult,
    String bankingCoreResult,
    int retryCount,
    Instant createdAt,
    Instant updatedAt,
    String errorMessage
) {
    /**
     *Factory method para crear una respuesta exitosa de pago.
     */
    public static PaymentResponse success(UUID id, String transactionReference, 
            BigDecimal amount, String currency, String channel, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "APPROVED",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            null
        );
    }

    /**
     * Factory method para crear una respuesta de pago pendiente.
     */
    public static PaymentResponse pending(UUID id, String transactionReference,
            BigDecimal amount, String currency, String channel, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "PENDING",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            null
        );
    }

    /**
     * Factory method para crear una respuesta de pago rechazado.
     */
    public static PaymentResponse rejected(UUID id, String transactionReference,
            BigDecimal amount, String currency, String channel, String reason, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "REJECTED",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            reason
        );
    }

    /**
     * Factory method para crear una respuesta de error.
     */
    public static PaymentResponse error(String transactionReference, String errorMessage) {
        return new PaymentResponse(
            null,
            transactionReference,
            "ERROR",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            Instant.now(),
            Instant.now(),
            errorMessage
        );
    }

    /**
     * Verifica si el pago fue aprobado.
     */
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    /**
     * Verifica si el pago está pendiente.
     */
    public boolean isPending() {
        return "PENDING".equals(status);
    }

    /**
     * Verifica si el pago fue rechazado.
     */
    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    /**
     * Verifica si hubo un error en el procesamiento.
     */
    public boolean isError() {
        return "ERROR".equals(status);
    }

    /**
     * Obtiene una descripción legible del estado del pago.
     */
    public String getStatusDescription() {
        return switch (status) {
            case "APPROVED" -> "Pago aprobado exitosamente";
            case "REJECTED" -> "Pago rechazado";
            case "PENDING" -> "Pago en proceso de validación";
            case "ERROR" -> "Error en el procesamiento del pago";
            default -> "Estado desconocido";
        };
    }

    /**
     * Obtiene un resumen corto para logging.
     */
    public String toSummary() {
        return "PaymentResponse{id=%s, ref=%s, status=%s, amount=%s}"
            .formatted(
                id != null ? id.toString().substring(0, 8) : "null",
                transactionReference != null ? transactionReference.substring(0, 
                    Math.min(8, transactionReference.length())) : "null",
                status,
                amount != null ? amount.toPlainString() : "null"
            );
    }
}