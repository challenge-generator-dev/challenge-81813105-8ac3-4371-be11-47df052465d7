package com.pragma.payments.application.commands;

import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.BankingCoreService;
import com.pragma.payments.domain.ports.FraudDetectionService;
import com.pragma.payments.domain.ports.PaymentGateway;
import com.pragma.payments.domain.ports.PaymentRepository;
import com.pragma.payments.domain.ports.RiskAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;

@Component
public class CreatePaymentCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CreatePaymentCommandHandler.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String FRAUD_CHECK_PASSED = "PASSED";
    private static final String RISK_LOW = "LOW";
    private static final String BANKING_SUCCESS = "SUCCESS";

    private final PaymentRepository paymentRepository;
    private final FraudDetectionService fraudDetectionService;
    private final RiskAssessmentService riskAssessmentService;
    private final BankingCoreService bankingCoreService;
    private final PaymentGateway paymentGateway;

    public CreatePaymentCommandHandler(
            PaymentRepository paymentRepository,
            FraudDetectionService fraudDetectionService,
            RiskAssessmentService riskAssessmentService,
            BankingCoreService bankingCoreService,
            PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.fraudDetectionService = fraudDetectionService;
        this.riskAssessmentService = riskAssessmentService;
        this.bankingCoreService = bankingCoreService;
        this.paymentGateway = paymentGateway;
    }

    public Mono<Payment> handle(CreatePaymentCommand command) {
        log.info("Iniciando procesamiento del comando de pago para operación: {}", command.operationNumber());
        
        return Mono.fromRunnable(() -> validateCommand(command))
                .then(Mono.defer(() -> checkIdempotency(command.operationNumber(), command.channel())))
                .flatMap(existingPayment -> {
                    if (existingPayment != null) {
                        log.info("Pago existente encontrado para operación: {}, retornando resultado anterior", 
                                command.operationNumber());
                        return Mono.just(existingPayment);
                    }
                    return executePaymentFlow(command);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    private void validateCommand(CreatePaymentCommand command) {
        if (command.amount() == null || command.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new PaymentProcessingException("El monto del pago debe ser mayor a cero");
        }
        if (command.senderAccountNumber() == null || command.senderAccountNumber().isBlank()) {
            throw new PaymentProcessingException("El número de cuenta del originador es obligatorio");
        }
        if (command.recipientAccountNumber() == null || command.recipientAccountNumber().isBlank()) {
            throw new PaymentProcessingException("El número de cuenta del beneficiario es obligatorio");
        }
        if (command.channel() == null || command.channel().isBlank()) {
            throw new PaymentProcessingException("El canal del pago es obligatorio");
        }
        if (command.operationNumber() == null || command.operationNumber().isBlank()) {
            throw new PaymentProcessingException("El número de operación es obligatorio");
        }
    }

    private Mono<Payment> checkIdempotency(String operationNumber, String channel) {
        return Mono.fromCallable(() -> paymentRepository.findByTransactionReference(operationNumber))
                .flatMap(Mono::just)
                .onErrorResume(e -> {
                    log.warn("Error al verificar idempotencia: {}", e.getMessage());
                    return Mono.just((Payment) null);
                });
    }

    private Mono<Payment> executePaymentFlow(CreatePaymentCommand command) {
        Payment initialPayment = createInitialPayment(command);
        Payment savedPayment = paymentRepository.save(initialPayment);
        log.info("Pago inicial guardado con ID: {}", savedPayment.id());

        return Mono.when(
                executeFraudDetection(savedPayment),
                executeRiskAssessment(savedPayment),
                executeBankingCoreRequest(savedPayment)
        )
        .then(Mono.defer(() -> {
            Payment finalPayment = determineFinalStatus(savedPayment);
            Payment updatedPayment = paymentRepository.save(finalPayment);
            log.info("Pago actualizado con estado final: {} para operación: {}", 
                    finalPayment.isApproved() ? "APPROVED" : "REJECTED", command.operationNumber());
            return triggerPaymentGateway(finalPayment);
        }))
        .onErrorResume(error -> {
            log.error("Error en el flujo de pago: {}", error.getMessage(), error);
            Payment failedPayment = savedPayment.withStatus(STATUS_REJECTED);
            paymentRepository.save(failedPayment);
            return Mono.error(new PaymentProcessingException("Error al procesar el pago: " + error.getMessage()));
        });
    }

    private Payment createInitialPayment(CreatePaymentCommand command) {
        return new Payment(
                UUID.randomUUID(),
                command.amount(),
                command.currency(),
                command.senderAccountNumber(),
                command.recipientAccountNumber(),
                command.channel(),
                command.operationNumber(),
                command.description(),
                command.country(),
                STATUS_PENDING,
                null,
                null,
                null,
                0,
                Instant.now(),
                Instant.now()
        );
    }

    private Mono<Payment> executeFraudDetection(Payment payment) {
        return fraudDetectionService.assessFraudRisk(payment)
                .doOnNext(result -> log.info("Resultado de detección de fraude: {} para pago: {}", 
                        result, payment.id()))
                .map(result -> payment.withFraudDetectionResult(result))
                .onErrorResume(error -> {
                    log.error("Error en detección de fraude: {}", error.getMessage());
                    return Mono.just(payment.withFraudDetectionResult("ERROR"));
                })
                .flatMap(updatedPayment -> {
                    Payment saved = paymentRepository.save(updatedPayment);
                    return Mono.just(saved);
                });
    }

    private Mono<Payment> executeRiskAssessment(Payment payment) {
        return riskAssessmentService.assessRiskWithTimeout(payment)
                .doOnNext(result -> log.info("Resultado de evaluación de riesgo: {} para pago: {}", 
                        result, payment.id()))
                .map(result -> payment.withRiskAssessmentResult(result))
                .onErrorResume(error -> {
                    log.error("Error en evaluación de riesgo: {}", error.getMessage());
                    return Mono.just(payment.withRiskAssessmentResult("TIMEOUT"));
                })
                .flatMap(updatedPayment -> {
                    Payment saved = paymentRepository.save(updatedPayment);
                    return Mono.just(saved);
                });
    }

    private Mono<Payment> executeBankingCoreRequest(Payment payment) {
        return bankingCoreService.processPayment(payment)
                .doOnNext(result -> log.info("Resultado del core bancario: {} para pago: {}", 
                        result, payment.id()))
                .map(result -> payment.withBankingCoreResult(result))
                .onErrorResume(error -> {
                    log.error("Error en el core bancario: {}", error.getMessage());
                    return Mono.just(payment.withBankingCoreResult("ERROR"));
                })
                .flatMap(updatedPayment -> {
                    Payment saved = paymentRepository.save(updatedPayment);
                    return Mono.just(saved);
                });
    }

    private Payment determineFinalStatus(Payment payment) {
        boolean fraudPassed = FRAUD_CHECK_PASSED.equals(payment.fraudDetectionResult());
        boolean riskLow = RISK_LOW.equals(payment.riskAssessmentResult());
        boolean bankingSuccess = BANKING_SUCCESS.equals(payment.bankingCoreResult());
        boolean riskTimeout = "TIMEOUT".equals(payment.riskAssessmentResult());

        if (!fraudPassed) {
            log.info("Pago rechazado por detección de fraude para ID: {}", payment.id());
            return payment.withStatus(STATUS_REJECTED);
        }

        if (!riskLow && !riskTimeout) {
            log.info("Pago rechazado por evaluación de riesgo para ID: {}", payment.id());
            return payment.withStatus(STATUS_REJECTED);
        }

        if (!bankingSuccess) {
            log.info("Pago rechazado por el core bancario para ID: {}", payment.id());
            return payment.withStatus(STATUS_REJECTED);
        }

        log.info("Pago aprobado para ID: {}", payment.id());
        return payment.withStatus(STATUS_APPROVED);
    }

    private Mono<Payment> triggerPaymentGateway(Payment payment) {
        return paymentGateway.confirmPayment(payment)
                .doOnSuccess(v -> log.info("Pago confirmado en gateway para ID: {}", payment.id()))
                .doOnError(error -> log.error("Error al confirmar en gateway: {}", error.getMessage()))
                .thenReturn(payment)
                .onErrorResume(error -> Mono.just(payment));
    }
}