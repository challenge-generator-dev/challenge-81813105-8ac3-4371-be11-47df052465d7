package com.pragma.payments.application.queries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record GetPaymentQuery(
    @NotBlank(message = "El identificador del pago es obligatorio")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", 
             message = "El identificador debe ser un UUID válido")
    String paymentId
) {
    public UUID paymentUuid() {
        return UUID.fromString(paymentId);
    }
}