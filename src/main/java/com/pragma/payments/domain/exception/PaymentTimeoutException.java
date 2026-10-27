package com.pragma.payments.domain.exception;

/**
 * Excepción personalizada para manejar timeouts en el flujo de pago.
 * Se lanza cuando el buró de riesgos excede el tiempo máximo de respuesta (2 segundos).
 */
public class PaymentTimeoutException extends RuntimeException {

    private final String serviceName;
    private final long timeoutMillis;
    private final long elapsedMillis;

    public PaymentTimeoutException(String serviceName, long timeoutMillis, long elapsedMillis) {
        super(String.format("Timeout en servicio %s: excedió %d ms (límite: %d ms)", 
                serviceName, elapsedMillis, timeoutMillis));
        this.serviceName = serviceName;
        this.timeoutMillis = timeoutMillis;
        this.elapsedMillis = elapsedMillis;
    }

    public PaymentTimeoutException(String serviceName, long timeoutMillis, long elapsedMillis, Throwable cause) {
        super(String.format("Timeout en servicio %s: excedió %d ms (límite: %d ms)", 
                serviceName, elapsedMillis, timeoutMillis), cause);
        this.serviceName = serviceName;
        this.timeoutMillis = timeoutMillis;
        this.elapsedMillis = elapsedMillis;
    }

    public PaymentTimeoutException(String message) {
        super(message);
        this.serviceName = "unknown";
        this.timeoutMillis = 0;
        this.elapsedMillis = 0;
    }

    public PaymentTimeoutException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "unknown";
        this.timeoutMillis = 0;
        this.elapsedMillis = 0;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public boolean isRetryable() {
        return true;
    }
}