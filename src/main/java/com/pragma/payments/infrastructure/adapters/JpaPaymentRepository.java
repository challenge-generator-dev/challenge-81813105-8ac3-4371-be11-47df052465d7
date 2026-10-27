package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.PaymentRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de persistencia que implementa el puerto PaymentRepository usando Spring Data JPA.
 * Convierte entre la entidad de dominio (Payment record) y la entidad JPA (PaymentEntity).
 */
@Repository
public class JpaPaymentRepository implements PaymentRepository {

    private final EntityManager entityManager;
    private final PaymentJpaRepository jpaRepository;

    public JpaPaymentRepository(EntityManager entityManager, PaymentJpaRepository jpaRepository) {
        this.entityManager = entityManager;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        if (payment.id() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
        entityManager.flush();
        entityManager.clear();
        return toDomain(entity);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        PaymentEntity entity = entityManager.find(PaymentEntity.class, id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    public Optional<Payment> findByTransactionReference(String transactionReference) {
        return jpaRepository.findByTransactionReference(transactionReference)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        PaymentEntity entity = entityManager.getReference(PaymentEntity.class, id);
        entityManager.remove(entity);
        entityManager.flush();
    }

    private PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        if (payment.id() != null) {
            entity.setId(payment.id());
        }
        entity.setAmount(payment.amount());
        entity.setCurrency(payment.currency());
        entity.setSourceAccount(payment.sourceAccount());
        entity.setDestinationAccount(payment.destinationAccount());
        entity.setStatus(payment.status());
        entity.setTransactionReference(payment.transactionReference());
        entity.setOperationNumber(payment.operationNumber());
        entity.setChannel(payment.channel());
        entity.setFraudDetectionResult(payment.fraudDetectionResult());
        entity.setRiskAssessmentResult(payment.riskAssessmentResult());
        entity.setBankingCoreResult(payment.bankingCoreResult());
        entity.setRetryCount(payment.retryCount());
        entity.setCreatedAt(payment.createdAt());
        entity.setUpdatedAt(payment.updatedAt());
        return entity;
    }

    private Payment toDomain(PaymentEntity entity) {
        return new Payment(
            entity.getId(),
            entity.getAmount(),
            entity.getCurrency(),
            entity.getSourceAccount(),
            entity.getDestinationAccount(),
            entity.getStatus(),
            entity.getTransactionReference(),
            entity.getOperationNumber(),
            entity.getChannel(),
            entity.getFraudDetectionResult(),
            entity.getRiskAssessmentResult(),
            entity.getBankingCoreResult(),
            entity.getRetryCount(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    /**
     * Interfaz de repositorio JPA para consultas específicas.
     */
    public interface PaymentJpaRepository {
        Optional<PaymentEntity> findByTransactionReference(String transactionReference);
    }

    /**
     * Entidad JPA para mapeo con la tabla de pagos.
     */
    @Entity
    @Table(name = "payments")
    public static class PaymentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, precision = 19, scale = 4)
        private java.math.BigDecimal amount;

        @Column(nullable = false, length = 3)
        private String currency;

        @Column(name = "source_account", nullable = false, length = 50)
        private String sourceAccount;

        @Column(name = "destination_account", nullable = false, length = 50)
        private String destinationAccount;

        @Column(nullable = false, length = 20)
        private String status;

        @Column(name = "transaction_reference", nullable = false, unique = true, length = 100)
        private String transactionReference;

        @Column(name = "operation_number", nullable = false, length = 50)
        private String operationNumber;

        @Column(nullable = false, length = 20)
        private String channel;

        @Column(name = "fraud_detection_result", length = 50)
        private String fraudDetectionResult;

        @Column(name = "risk_assessment_result", length = 50)
        private String riskAssessmentResult;

        @Column(name = "banking_core_result", length = 50)
        private String bankingCoreResult;

        @Column(name = "retry_count", nullable = false)
        private int retryCount = 0;

        @Column(name = "created_at", nullable = false)
        private java.time.Instant createdAt;

        @Column(name = "updated_at")
        private java.time.Instant updatedAt;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public java.math.BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(java.math.BigDecimal amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getSourceAccount() {
            return sourceAccount;
        }

        public void setSourceAccount(String sourceAccount) {
            this.sourceAccount = sourceAccount;
        }

        public String getDestinationAccount() {
            return destinationAccount;
        }

        public void setDestinationAccount(String destinationAccount) {
            this.destinationAccount = destinationAccount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransactionReference() {
            return transactionReference;
        }

        public void setTransactionReference(String transactionReference) {
            this.transactionReference = transactionReference;
        }

        public String getOperationNumber() {
            return operationNumber;
        }

        public void setOperationNumber(String operationNumber) {
            this.operationNumber = operationNumber;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getFraudDetectionResult() {
            return fraudDetectionResult;
        }

        public void setFraudDetectionResult(String fraudDetectionResult) {
            this.fraudDetectionResult = fraudDetectionResult;
        }

        public String getRiskAssessmentResult() {
            return riskAssessmentResult;
        }

        public void setRiskAssessmentResult(String riskAssessmentResult) {
            this.riskAssessmentResult = riskAssessmentResult;
        }

        public String getBankingCoreResult() {
            return bankingCoreResult;
        }

        public void setBankingCoreResult(String bankingCoreResult) {
            this.bankingCoreResult = bankingCoreResult;
        }

        public int getRetryCount() {
            return retryCount;
        }

        public void setRetryCount(int retryCount) {
            this.retryCount = retryCount;
        }

        public java.time.Instant getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(java.time.Instant createdAt) {
            this.createdAt = createdAt;
        }

        public java.time.Instant getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(java.time.Instant updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}