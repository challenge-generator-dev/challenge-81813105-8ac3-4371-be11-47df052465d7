package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Puerto para el servicio del core bancario.
 * Define la контракт for interacting with the bank's core system
 * to process payments, verify accounts, and manage transactions.
 * This port is implemented by an adapter in the infrastructure layer.
 */
public interface BankingCoreService {

    /**
     * Procesa un pago a través del core bancario.
     * Esta operación implica la transferencia de fondos desde la cuenta
     * origen hacia la cuenta destino, actualizando los saldos correspondientes.
     *
     * @param payment El pago a procesar
     * @return Mono con el resultado del procesamiento bancario
     */
    Mono<String> processPayment(Payment payment);

    /**
     * Verifica si una cuenta existe y está activa en el core bancario.
     * Utilizado para validar las cuentas origen y destino antes de procesar.
     *
     * @param accountNumber Número de cuenta a verificar
     * @return Mono con el estado de la cuenta
     */
    Mono<String> verifyAccount(String accountNumber);

    /**
     * Obtiene el saldo disponible de una cuenta.
     *
     * @param accountNumber Número de cuenta
     * @return Mono con el saldo disponible
     */
    Mono<BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Confirma un pago previamente procesado.
     * Utilizado para confirmar la ejecución exitosa de una transacción.
     *
     * @param transactionId ID de la transacción
     * @return Mono con la confirmación
     */
    Mono<String> confirmPayment(UUID transactionId);

    /**
     * Revierte un pago previamente procesado.
     * Se utiliza en casos de fallo o disputa.
     *
     * @param transactionId ID de la transacción a revertir
     * @param reason Razón de la reversión
     * @return Mono con el resultado de la reversión
     */
    Mono<String> reversePayment(UUID transactionId, String reason);

    /**
     * Verifica si el procesamiento fue exitoso basado en el resultado.
     *
     * @param result Resultado del procesamiento
     * @return true si el procesamiento fue exitoso
     */
    default boolean isPaymentSuccessful(String result) {
        return result != null &&
               (result.contains("APPROVED") ||
                result.contains("SUCCESS") ||
                result.contains("COMPLETED"));
    }

    /**
     * Obtiene el mensaje de error del resultado bancario para logging.
     *
     * @param result Resultado del procesamiento
     * @return Mensaje de error o null si no hay error
     */
    default String extractErrorMessage(String result) {
        if (result == null) {
            return "No response from banking core";
        }
        if (result.contains("ERROR:")) {
            int start = result.indexOf("ERROR:") + 6;
            int end = result.indexOf(";", start);
            return end > start ? result.substring(start, end) : result.substring(start);
        }
        return null;
    }
}