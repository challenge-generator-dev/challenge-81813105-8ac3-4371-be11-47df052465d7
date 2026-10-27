package com.pragma.payments.application.commands;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreatePaymentCommand(
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    BigDecimal amount,

    @NotBlank(message = "La moneda es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La moneda debe ser un código ISO 4217 de 3 letras")
    String currency,

    @NotBlank(message = "El número de cuenta del originador es obligatorio")
    @Size(min = 10, max = 34, message = "El número de cuenta debe tener entre 10 y 34 caracteres")
    String senderAccountNumber,

    @NotBlank(message = "El número de cuenta del beneficiario es obligatorio")
    @Size(min = 10, max = 34, message = "El número de cuenta debe tener entre 10 y 34 caracteres")
    String recipientAccountNumber,

    @NotBlank(message = "El canal es obligatorio")
    @Pattern(regexp = "^(API|MOVIL|WEB|CAJERO)$", message = "El canal debe ser API, MOVIL, WEB o CAJERO")
    String channel,

    @NotBlank(message = "El número de operación es obligatorio")
    @Size(min = 1, max = 50, message = "El número de operación debe tener entre 1 y 50 caracteres")
    String operationNumber,

    String description,

    @Pattern(regexp = "^[A-Z]{2}$", message = "El país debe ser un código ISO 3166-1 alpha-2")
    String country
) {
    public CreatePaymentCommand {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }
}