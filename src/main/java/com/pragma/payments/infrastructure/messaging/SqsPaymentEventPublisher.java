package com.pragma.payments.infrastructure.messaging;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.PaymentEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SqsPaymentEventPublisher implements PaymentEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SqsPaymentEventPublisher.class);
    private static final String PAYMENT_EVENTS_QUEUE = "payment-events";
    private static final String PAYMENT_STATUS_CHANGED_EVENT = "PaymentStatusChanged";

    private final AmazonSQSAsync amazonSqsAsync;
    private final String queueUrl;

    public SqsPaymentEventPublisher(
            AmazonSQSAsync amazonSqsAsync,
            @Value("${aws.sqs.payment-events-queue-url:}") String queueUrl) {
        this.amazonSqsAsync = amazonSqsAsync;
        this.queueUrl = queueUrl;
    }

    @Override
    public Mono<Void> publishPaymentCreated(Payment payment) {
        return publishEvent(PAYMENT_STATUS_CHANGED_EVENT, payment, "CREATED");
    }

    @Override
    public Mono<Void> publishPaymentApproved(Payment payment) {
        return publishEvent(PAYMENT_STATUS_CHANGED_EVENT, payment, "APPROVED");
    }

    @Override
    public Mono<Void> publishPaymentRejected(Payment payment) {
        return publishEvent(PAYMENT_STATUS_CHANGED_EVENT, payment, "REJECTED");
    }

    @Override
    public Mono<Void> publishPaymentFailed(Payment payment, String failureReason) {
        return publishEvent(PAYMENT_STATUS_CHANGED_EVENT, payment, "FAILED", failureReason);
    }

    private Mono<Void> publishEvent(String eventType, Payment payment, String newStatus) {
        return publishEvent(eventType, payment, newStatus, null);
    }

    private Mono<Void> publishEvent(String eventType, Payment payment, String newStatus, String failureReason) {
        Map<String, Object> eventPayload = buildEventPayload(payment, newStatus, failureReason);
        
        String messageBody = serializeEvent(eventType, eventPayload);
        String messageGroupId = determineMessageGroupId(payment);
        String deduplicationId = generateDeduplicationId(payment, eventType);

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody)
                .withMessageGroupId(messageGroupId)
                .withMessageDeduplicationId(deduplicationId)
                .withDelaySeconds(0);

        return Mono.fromFuture(() -> amazonSqsAsync.sendMessageAsync(sendMessageRequest))
                .doOnSuccess(result -> log.info(
                        "Evento publicado exitosamente - EventType: {}, PaymentId: {}, Status: {}",
                        eventType, payment.id(), newStatus))
                .doOnError(error -> log.error(
                        "Error al publicar evento - EventType: {}, PaymentId: {}, Error: {}",
                        eventType, payment.id(), error.getMessage(), error))
                .then();
    }

    private Map<String, Object> buildEventPayload(Payment payment, String newStatus, String failureReason) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventId", UUID.randomUUID().toString());
        payload.put("eventType", PAYMENT_STATUS_CHANGED_EVENT);
        payload.put("timestamp", Instant.now().toString());
        payload.put("paymentId", payment.id().toString());
        payload.put("transactionReference", payment.transactionReference());
        payload.put("amount", payment.amount().toString());
        payload.put("currency", payment.currency());
        payload.put("previousStatus", payment.status());
        payload.put("newStatus", newStatus);
        payload.put("channel", payment.channel());
        
        if (failureReason != null) {
            payload.put("failureReason", failureReason);
        }
        
        if (payment.fraudDetectionResult() != null) {
            payload.put("fraudDetectionResult", payment.fraudDetectionResult());
        }
        
        if (payment.riskAssessmentResult() != null) {
            payload.put("riskAssessmentResult", payment.riskAssessmentResult());
        }
        
        if (payment.bankingCoreResult() != null) {
            payload.put("bankingCoreResult", payment.bankingCoreResult());
        }
        
        return payload;
    }

    private String serializeEvent(String eventType, Map<String, Object> payload) {
        StringBuilder json = new StringBuilder("{\"eventType\":\"");
        json.append(eventType).append("\",\"payload\":{");
        
        int index = 0;
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            if (index > 0) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":");
            
            Object value = entry.getValue();
            if (value instanceof String) {
                json.append("\"").append(escapeJsonString((String) value)).append("\"");
            } else {
                json.append("\"").append(escapeJsonString(value.toString())).append("\"");
            }
            index++;
        }
        
        json.append("}}}");
        return json.toString();
    }

    private String escapeJsonString(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String determineMessageGroupId(Payment payment) {
        return payment.channel() != null ? 
                "channel-" + payment.channel() : 
                "channel-default";
    }

    private String generateDeduplicationId(Payment payment, String eventType) {
        String uniqueKey = payment.id().toString() + "-" + eventType + "-" + System.currentTimeMillis();
        return UUID.nameUUIDFromBytes(uniqueKey.getBytes()).toString();
    }

    public String getQueueUrl() {
        return queueUrl;
    }

    public boolean isQueueAvailable() {
        try {
            amazonSqsAsync.getQueueUrl(PAYMENT_EVENTS_QUEUE);
            return true;
        } catch (Exception e) {
            log.warn("Cola SQS no disponible: {}", e.getMessage());
            return false;
        }
    }
}