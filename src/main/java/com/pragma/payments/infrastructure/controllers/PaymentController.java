package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.application.commands.CreatePaymentCommand;
import com.pragma.payments.application.commands.CreatePaymentCommandHandler;
import com.pragma.payments.application.queries.GetPaymentQuery;
import com.pragma.payments.application.queries.GetPaymentQueryHandler;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.infrastructure.dto.PaymentRequest;
import com.pragma.payments.infrastructure.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payments", description = "API para procesamiento de pagos")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final CreatePaymentCommandHandler createPaymentCommandHandler;
    private final GetPaymentQueryHandler getPaymentQueryHandler;

    public PaymentController(
            CreatePaymentCommandHandler createPaymentCommandHandler,
            GetPaymentQueryHandler getPaymentQueryHandler) {
        this.createPaymentCommandHandler = createPaymentCommandHandler;
        this.getPaymentQueryHandler = getPaymentQueryHandler;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Procesa una solicitud de pago con manejo de idempotencia")
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request,
            @RequestHeader(value = "X-Idempotency-Key", required = false) String idempotencyKey,
            @RequestHeader(value = "X-Request-Channel", required = false, defaultValue = "API") String channel) {

        log.info("Recibida solicitud de pago: idempotencyKey={}, channel={}, amount={}",
                idempotencyKey, channel, request.amount());

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "X-Idempotency-Key es requerido para garantizar la idempotencia");
        }

        CreatePaymentCommand command = CreatePaymentCommand.builder()
                .idempotencyKey(idempotencyKey)
                .channel(channel)
                .transactionReference(request.transactionReference())
                .amount(request.amount())
                .currency(request.currency())
                .sourceAccount(request.sourceAccount())
                .destinationAccount(request.destinationAccount())
                .paymentType(request.paymentType())
                .description(request.description())
                .build();

        try {
            Payment payment = createPaymentCommandHandler.handle(command);
            PaymentResponse response = toResponse(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al procesar pago: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al procesar el pago: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Recupera los detalles de un pago por su identificador")
    public ResponseEntity<PaymentResponse> getPayment(
            @Parameter(description = "UUID del pago") @PathVariable UUID id) {

        log.info("Consultando pago: id={}", id);

        GetPaymentQuery query = new GetPaymentQuery(id);
        Payment payment = getPaymentQueryHandler.handle(query);

        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pago no encontrado con ID: " + id);
        }

        return ResponseEntity.ok(toResponse(payment));
    }

    @GetMapping("/reference/{transactionReference}")
    @Operation(summary = "Obtener pago por referencia", description = "Recupera los detalles de un pago por su referencia de transacción")
    public ResponseEntity<PaymentResponse> getPaymentByReference(
            @Parameter(description = "Referencia de transacción") @PathVariable String transactionReference) {

        log.info("Consultando pago por referencia: {}", transactionReference);

        GetPaymentQuery query = new GetPaymentQuery(transactionReference);
        Payment payment = getPaymentQueryHandler.handle(query);

        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pago no encontrado con referencia: " + transactionReference);
        }

        return ResponseEntity.ok(toResponse(payment));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de pagos")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.id())
                .transactionReference(payment.transactionReference())
                .amount(payment.amount())
                .currency(payment.currency())
                .status(payment.status())
                .sourceAccount(payment.sourceAccount())
                .destinationAccount(payment.destinationAccount())
                .paymentType(payment.paymentType())
                .fraudDetectionResult(payment.fraudDetectionResult())
                .riskAssessmentResult(payment.riskAssessmentResult())
                .bankingCoreResult(payment.bankingCoreResult())
                .createdAt(payment.createdAt())
                .updatedAt(payment.updatedAt())
                .build();
    }
}