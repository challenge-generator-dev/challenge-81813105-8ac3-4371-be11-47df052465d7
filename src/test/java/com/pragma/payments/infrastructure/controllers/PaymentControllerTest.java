package com.pragma.payments.infrastructure.controllers;

import com.pragma.payments.application.commands.CreatePaymentCommand;
import com.pragma.payments.application.commands.CreatePaymentCommandHandler;
import com.pragma.payments.application.queries.GetPaymentQueryHandler;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.infrastructure.dto.PaymentRequest;
import com.pragma.payments.infrastructure.dto.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(PaymentController.class)
@DisplayName("PaymentController - Tests de Integración")
class PaymentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreatePaymentCommandHandler createPaymentCommandHandler;

    @MockBean
    private GetPaymentQueryHandler getPaymentQueryHandler;

    private PaymentRequest paymentRequestValido;
    private Payment paymentRespuesta;
    private PaymentResponse paymentResponse;

    @BeforeEach
    void setUp() {
        paymentRequestValido = new PaymentRequest(
            "OP-2024-001",
            "MOBILE_APP",
            new BigDecimal("1500.50"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago de prueba"
        );

        paymentRespuesta = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-ABC123")
            .operationNumber("OP-2024-001")
            .channel("MOBILE_APP")
            .amount(new BigDecimal("1500.50"))
            .currency("USD")
            .sourceAccount("1234567890")
            .destinationAccount("0987654321")
            .description("Pago de prueba")
            .status("APPROVED")
            .build();

        paymentResponse = new PaymentResponse(
            paymentRespuesta.id().toString(),
            paymentRespuesta.transactionReference(),
            paymentRespuesta.operationNumber(),
            paymentRespuesta.channel(),
            paymentRespuesta.amount(),
            paymentRespuesta.currency(),
            paymentRespuesta.status()
        );
    }

    @Test
    @DisplayName("POST /api/payments debe retornar 201 cuando el pago se crea exitosamente")
    void crearPago_debeRetornar201_cuandoPagoSeCreaExitosamente() {
        when(createPaymentCommandHandler.handle(any(CreatePaymentCommand.class)))
            .thenReturn(Mono.just(paymentRespuesta));

        webTestClient.post()
            .uri("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(paymentRequestValido)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(paymentResponse.id())
            .jsonPath("$.transactionReference").isEqualTo(paymentResponse.transactionReference())
            .jsonPath("$.status").isEqualTo("APPROVED");
    }

    @Test
    @DisplayName("POST /api/payments debe retornar 400 cuando la solicitud tiene datos inválidos")
    void crearPago_debeRetornar400_cuandoSolicitudEsInvalida() {
        PaymentRequest requestInvalida = new PaymentRequest(
            null,
            "MOBILE_APP",
            new BigDecimal("-100.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago inválido"
        );

        webTestClient.post()
            .uri("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestInvalida)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("POST /api/payments debe retornar 422 cuando el pago es rechazado por reglas de negocio")
    void crearPago_debeRetornar422_cuandoPagoEsRechazado() {
        Payment paymentRechazado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-REJECTED")
            .operationNumber("OP-2024-002")
            .channel("MOBILE_APP")
            .amount(new BigDecimal("5000.00"))
            .currency("USD")
            .sourceAccount("1234567890")
            .destinationAccount("0987654321")
            .description("Pago rechazado")
            .status("REJECTED")
            .rejectionReason("Monto excede límite permitido")
            .build();

        when(createPaymentCommandHandler.handle(any(CreatePaymentCommand.class)))
            .thenReturn(Mono.just(paymentRechazado));

        webTestClient.post()
            .uri("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(paymentRequestValido)
            .exchange()
            .expectStatus().isEqualTo(422);
    }

    @Test
    @DisplayName("GET /api/payments/{id} debe retornar 200 con los datos del pago")
    void obtenerPago_debeRetornar200_cuandoPagoExiste() {
        UUID paymentId = UUID.randomUUID();

        when(getPaymentQueryHandler.handle(eq(paymentId.toString())))
            .thenReturn(Mono.just(paymentRespuesta));

        webTestClient.get()
            .uri("/api/payments/{id}", paymentId)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(paymentResponse.id())
            .jsonPath("$.transactionReference").isEqualTo(paymentResponse.transactionReference())
            .jsonPath("$.status").isEqualTo(paymentResponse.status());
    }

    @Test
    @DisplayName("GET /api/payments/{id} debe retornar 404 cuando el pago no existe")
    void obtenerPago_debeRetornar404_cuandoPagoNoExiste() {
        UUID paymentId = UUID.randomUUID();

        when(getPaymentQueryHandler.handle(eq(paymentId.toString())))
            .thenReturn(Mono.empty());

        webTestClient.get()
            .uri("/api/payments/{id}", paymentId)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("GET /api/payments/transaction/{reference} debe retornar 200 con los datos del pago")
    void obtenerPagoPorReferencia_debeRetornar200_cuandoReferenciaExiste() {
        String transactionRef = "TXN-ABC123";

        when(getPaymentQueryHandler.handleByTransactionReference(eq(transactionRef)))
            .thenReturn(Mono.just(paymentRespuesta));

        webTestClient.get()
            .uri("/api/payments/transaction/{reference}", transactionRef)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.transactionReference").isEqualTo(transactionRef);
    }

    @Test
    @DisplayName("POST /api/payments debe retornar 500 cuando ocurre un error interno")
    void crearPago_debeRetornar500_cuandoOcurreErrorInterno() {
        when(createPaymentCommandHandler.handle(any(CreatePaymentCommand.class)))
            .thenReturn(Mono.error(new RuntimeException("Error interno del sistema")));

        webTestClient.post()
            .uri("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(paymentRequestValido)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("GET /api/payments debe retornar 200 con lista de pagos cuando se consulta por cuenta")
    void listarPagosPorCuenta_debeRetornar200_cuandoExistenPagos() {
        String accountNumber = "1234567890";

        Payment otroPago = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-DEF456")
            .operationNumber("OP-2024-003")
            .channel("WEB")
            .amount(new BigDecimal("750.25"))
            .currency("USD")
            .sourceAccount(accountNumber)
            .destinationAccount("1111111111")
            .description("Otro pago")
            .status("APPROVED")
            .build();

        when(getPaymentQueryHandler.handleByAccount(eq(accountNumber)))
            .thenReturn(Mono.just(java.util.List.of(paymentRespuesta, otroPago)));

        webTestClient.get()
            .uri("/api/payments?account={account}", accountNumber)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(PaymentResponse.class)
            .hasSize(2);
    }
}