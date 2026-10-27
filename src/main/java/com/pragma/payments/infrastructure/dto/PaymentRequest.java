package com.pragma.payments.infrastructure.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO que representa la solicitud de creación de un pago.
 * Contiene todos los campos requeridos para procesar una transacción de pago
 * con las validaciones necesarias para garantizar la integridad de los datos.
 */
public record PaymentRequest(
    @NotBlank(message = "La referencia de transacción es obligatoria")
    @Size(min = 1, max = 100, message = "La referencia debe tener entre 1 y 100 caracteres")
    String transactionReference,

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    BigDecimal amount,

    @NotBlank(message = "La moneda es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La moneda debe ser un código ISO de 3 letras")
    String currency,

    @NotBlank(message = "La cuenta de origen es obligatoria")
    @Size(min = 1, max = 50, message = "La cuenta de origen debe tener entre 1 y 50 caracteres")
    String sourceAccount,

    @NotBlank(message = "La cuenta de destino es obligatoria")
    @Size(min = 1, max = 50, message = "La cuenta de destino debe tener entre 1 y 50 caracteres")
    String destinationAccount,

    @NotBlank(message = "El canal de solicitud es obligatorio")
    @Pattern(regexp = "^(API|MOVIL|WEB|CAJERO|TRANSACCIONAL)$", 
             message = "El canal debe ser uno de: API, MOVIL, WEB, CAJERO, TRANSACCIONAL")
    String channel,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String description,

    @Pattern(regexp = "^[A-Z]{2}$", message = "El país debe ser un código ISO de 2 letras")
    String country,

    @Size(max = 50, message = "El identificador de dispositivo no puede exceder 50 caracteres")
    String deviceId,

    @Pattern(regexp = "^[0-9]{1,3}(\\.[0-9]{1,3}){3}$", message = "IP inválida")
    String ipAddress,

    @Size(max = 20, message = "El tipo de dispositivo no puede exceder 20 caracteres")
    String deviceType,

    @Size(max = 100, message = "El identificador de sesión no puede exceder 100 caracteres")
    String sessionId
) {
    /**
     * Valida que la cuenta de origen no sea igual a la de destino.
     */
    public boolean isSourceEqualsDestination() {
        return sourceAccount != null && sourceAccount.equals(destinationAccount);
    }

    /**
     * Obtiene el monto en formato numérico para logging.
     */
    public String getAmountFormatted() {
        return amount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * Obtiene una versión sanitizada del DTO para logging sin datos sensibles.
     */
    public String toSafeString() {
        return "PaymentRequest{transactionReference='%s', amount=%s, currency='%s', channel='%s'}"
            .formatted(transactionReference, getAmountFormatted(), currency, channel);
    }
}