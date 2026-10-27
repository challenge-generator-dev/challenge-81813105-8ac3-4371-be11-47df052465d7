package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto para el gateway de pagos externo.
 * Define la контракт que abstrae la comunicación con el proveedor
 * de procesamiento de pagos (PSP). El gateway maneja la comunicación
 * con redes de tarjetas, procesadores de pago y otros proveedores externos.
 * This port is implemented by an adapter in the infrastructure layer.
 */
public interface PaymentGateway {

    /**
     * Autoriza un pago a través del gateway externo.
     * Esta operación verifica que la tarjeta/cuenta tenga fondos suficientes
     * y que la transacción esté autorizada para proceder.
     *
     * @param payment El pago a autorizar
     * @return Mono con el resultado de la autorización
     */
    Mono<String> authorizePayment(Payment payment);

    /**
     * Captura un pago previamente autorizado.
     * La captura confirma el cargo a la tarjeta/cuenta del cliente.
     *
     * @param authorizationId ID de la autorización previa
     * @param amount Monto a capturar
     * @return Mono con el resultado de la captura
     */
    Mono<String> capturePayment(String authorizationId, java.math.BigDecimal amount);

    /**
     * Cancela una autorización previa.
     * Utilizado cuando no se va a completar la transacción.
     *
     * @param authorizationId ID de la autorización a cancelar
     * @return Mono con el resultado de la cancelación
     */
    Mono<String> cancelAuthorization(String authorizationId);

    /**
     * Reembolsa un pago capturado previamente.
     * Se utiliza para devoluciones o disputas.
     *
     * @param transactionId ID de la transacción a reembolsar
     * @param amount Monto a reembolsar
     * @return Mono con el resultado del reembolso
     */
    Mono<String> refundPayment(UUID transactionId, java.math.BigDecimal amount);

    /**
     * Consulta el estado de una transacción en el gateway.
     *
     * @param transactionId ID de la transacción
     * @return Mono con el estado actual
     */
    Mono<String> getTransactionStatus(UUID transactionId);

    /**
     * Verifica si la autorización fue exitosa.
     *
     * @param authorizationResult Resultado de la autorización
     * @return true si la autorización fue exitosa
     */
    default boolean isAuthorizationSuccessful(String authorizationResult) {
        return authorizationResult != null &&
               (authorizationResult.contains("AUTHORIZED") ||
                authorizationResult.contains("APPROVED") ||
                authorizationResult.contains("SUCCESS"));
    }

    /**
     * Extrae el ID de autorización del resultado para transacciones futuras.
     *
     * @param authorizationResult Resultado de la autorización
     * @return ID de autorización o null si no disponible
     */
    default String extractAuthorizationId(String authorizationResult) {
        if (authorizationResult == null) {
            return null;
        }
        if (authorizationResult.contains("AUTH_ID:")) {
            int start = authorizationResult.indexOf("AUTH_ID:") + 8;
            int end = authorizationResult.indexOf(";", start);
            return end > start ? authorizationResult.substring(start, end).trim()
                               : authorizationResult.substring(start).trim();
        }
        return null;
    }

    /**
     * Determina si el error es retryable (error transitorio).
     *
     * @param errorMessage Mensaje de error
     * @return true si se debe reintentar la operación
     */
    default boolean isRetryableError(String errorMessage) {
        if (errorMessage == null) {
            return false;
        }
        String lowerError = errorMessage.toLowerCase();
        return lowerError.contains("timeout") ||
               lowerError.contains("connection") ||
               lowerError.contains("unavailable") ||
               lowerError.contains("temporary");
    }
}