package com.pragma.payments.domain.exception;

import java.util.UUID;

/**
 * Excepción personalizada para manejar errores generales en el procesamiento de pagos.
 * Se lanza cuando ocurre un error durante el flujo de pago que no es un timeout.
 */
public class PaymentProcessingException extends RuntimeException {

    private final UUID paymentId;
    private final String transactionReference;
    private final String errorCode;
    private final boolean retryable;

    public PaymentProcessingException(String message) {
        super(message);
        this.paymentId = null;
        this.transactionReference = null;
        this.errorCode = "PAYMENT_ERROR";
        this.retryable = false;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.paymentId = null;
        this.transactionReference = null;
        this.errorCode = "PAYMENT_ERROR";
        this.retryable = false;
    }

    public PaymentProcessingException(UUID paymentId, String transactionReference, 
                                       String errorCode, String message) {
        super(message);
        this.paymentId = paymentId;
        this.transactionReference = transactionReference;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    public PaymentProcessingException(UUID paymentId, String transactionReference, 
                                       String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.paymentId = paymentId;
        this.transactionReference = transactionReference;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    private static boolean determineRetryable(String errorCode) {
        return "FRAUD_CHECK_ERROR".equals(errorCode) || 
               "RISK_ASSESSMENT_ERROR".equals(errorCode) ||
               "BANKING_CORE_ERROR".equals(errorCode) ||
               "GATEWAY_ERROR".equals(errorCode);
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static PaymentProcessingException fraudCheckFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "FRAUD_CHECK_ERROR",
            "Error en la verificación antifraude"
        );
    }

    public static PaymentProcessingException riskAssessmentFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "RISK_ASSESSMENT_ERROR",
            "Error en la evaluación de riesgo"
        );
    }

    public static PaymentProcessingException bankingCoreFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "BANKING_CORE_ERROR",
            "Error en la comunicación con el core bancario"
        );
    }

    public static PaymentProcessingException gatewayError(UUID paymentId, String transactionReference, Throwable cause) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "GATEWAY_ERROR",
            "Error en el gateway de pagos",
            cause
        );
    }
}