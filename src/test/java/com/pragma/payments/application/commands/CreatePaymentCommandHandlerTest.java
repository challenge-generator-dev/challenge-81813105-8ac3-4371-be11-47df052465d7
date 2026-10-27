package com.pragma.payments.application.commands;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.BankingCoreService;
import com.pragma.payments.domain.ports.FraudDetectionService;
import com.pragma.payments.domain.ports.PaymentGateway;
import com.pragma.payments.domain.ports.PaymentRepository;
import com.pragma.payments.domain.ports.RiskAssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreatePaymentCommandHandler - Tests Unitarios")
class CreatePaymentCommandHandlerTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private FraudDetectionService fraudDetectionService;

    @Mock
    private RiskAssessmentService riskAssessmentService;

    @Mock
    private BankingCoreService bankingCoreService;

    @Mock
    private PaymentGateway paymentGateway;

    private CreatePaymentCommandHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreatePaymentCommandHandler(
            paymentRepository,
            fraudDetectionService,
            riskAssessmentService,
            bankingCoreService,
            paymentGateway
        );
    }

    @Test
    @DisplayName("Debe crear un pago exitosamente cuando todas las validaciones pasan")
    void handle_debeCrearPagoExitosamente_cuandoTodasValidacionesPasan() {
        CreatePaymentCommand command = new CreatePaymentCommand(
            "ORIG-001",
            "CHANNEL-001",
            new BigDecimal("1000.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago de servicio"
        );

        Payment paymentGuardado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8))
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentGuardado);
        when(fraudDetectionService.assessFraudRisk(any(Payment.class))).thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentService.assessRisk(any(Payment.class))).thenReturn(Mono.just("LOW_RISK"));
        when(bankingCoreService.processPayment(any(Payment.class))).thenReturn(Mono.just("SUCCESS"));
        when(paymentGateway.executePayment(any(Payment.class))).thenReturn(Mono.just("COMPLETED"));

        StepVerifier.create(handler.handle(command))
            .assertNext(payment -> {
                assertThat(payment).isNotNull();
                assertThat(payment.operationNumber()).isEqualTo(command.operationNumber());
                assertThat(payment.channel()).isEqualTo(command.channel());
                assertThat(payment.amount()).isEqualByComparingTo(command.amount());
            })
            .verifyComplete();

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el servicio antifraude retorna fraude detectado")
    void handle_debeRechazarPago_cuandoFraudDetectionRetornaFraude() {
        CreatePaymentCommand command = new CreatePaymentCommand(
            "ORIG-002",
            "CHANNEL-002",
            new BigDecimal("5000.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago怀疑"
        );

        Payment paymentGuardado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8))
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentGuardado);
        when(fraudDetectionService.assessFraudRisk(any(Payment.class))).thenReturn(Mono.just("FRAUD_DETECTED"));

        StepVerifier.create(handler.handle(command))
            .assertNext(payment -> {
                assertThat(payment.isRejected()).isTrue();
                assertThat(payment.status()).isEqualTo("REJECTED");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe marcar pago como Pendiente cuando el buró de riesgos hace timeout")
    void handle_debeMarcarPagoComoPendiente_cuandoRiskAssessmentTimeout() {
        CreatePaymentCommand command = new CreatePaymentCommand(
            "ORIG-003",
            "CHANNEL-003",
            new BigDecimal("2000.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago con timeout"
        );

        Payment paymentGuardado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8))
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentGuardado);
        when(fraudDetectionService.assessFraudRisk(any(Payment.class))).thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentService.assessRisk(any(Payment.class)))
            .thenReturn(Mono.error(new RuntimeException("Timeout del buró de riesgos")));
        when(riskAssessmentService.assessRiskWithFallback(any(Payment.class), any()))
            .thenReturn(Mono.just("PENDING_REVIEW"));

        StepVerifier.create(handler.handle(command))
            .assertNext(payment -> {
                assertThat(payment.isPending()).isTrue();
                assertThat(payment.status()).isEqualTo("PENDING");
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe incrementar retry count cuando el procesamiento falla temporalmente")
    void handle_debeIncrementarRetryCount_cuandoProcesamientoFalla() {
        CreatePaymentCommand command = new CreatePaymentCommand(
            "ORIG-004",
            "CHANNEL-004",
            new BigDecimal("1500.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago con retry"
        );

        Payment paymentGuardado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8))
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .retryCount(0)
            .build();

        Payment paymentConRetry = Payment.builder()
            .id(paymentGuardado.id())
            .transactionReference(paymentGuardado.transactionReference())
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .retryCount(1)
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentGuardado, paymentConRetry);
        when(fraudDetectionService.assessFraudRisk(any(Payment.class))).thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentService.assessRisk(any(Payment.class))).thenReturn(Mono.just("LOW_RISK"));
        when(bankingCoreService.processPayment(any(Payment.class)))
            .thenReturn(Mono.error(new RuntimeException("Error temporal")));

        StepVerifier.create(handler.handle(command))
            .assertNext(payment -> {
                assertThat(payment.retryCount()).isGreaterThan(0);
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe rechazar pago cuando el banking core retorna rechazo")
    void handle_debeRechazarPago_cuandoBankingCoreRetornaRechazo() {
        CreatePaymentCommand command = new CreatePaymentCommand(
            "ORIG-005",
            "CHANNEL-005",
            new BigDecimal("8000.00"),
            "USD",
            "1234567890",
            "0987654321",
            "Pago rechazado por banco"
        );

        Payment paymentGuardado = Payment.builder()
            .id(UUID.randomUUID())
            .transactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8))
            .operationNumber(command.operationNumber())
            .channel(command.channel())
            .amount(command.amount())
            .currency(command.currency())
            .sourceAccount(command.sourceAccount())
            .destinationAccount(command.destinationAccount())
            .description(command.description())
            .status("PENDING")
            .build();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentGuardado);
        when(fraudDetectionService.assessFraudRisk(any(Payment.class))).thenReturn(Mono.just("APPROVED"));
        when(riskAssessmentService.assessRisk(any(Payment.class))).thenReturn(Mono.just("LOW_RISK"));
        when(bankingCoreService.processPayment(any(Payment.class))).thenReturn(Mono.just("INSUFFICIENT_FUNDS"));

        StepVerifier.create(handler.handle(command))
            .assertNext(payment -> {
                assertThat(payment.isRejected()).isTrue();
                assertThat(payment.status()).isEqualTo("REJECTED");
            })
            .verifyComplete();
    }
}