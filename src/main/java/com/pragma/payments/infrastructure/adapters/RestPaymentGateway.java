package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.PaymentGateway;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Adaptador que implementa el puerto PaymentGateway para comunicarse
 * con el gateway de pagos externo vía REST.
 * 
 * Este adaptador maneja la integración con el proveedor de pagos externo,
 * transformando las solicitudes del dominio en llamadas HTTP y procesando
 * las respuestas para actualizar el estado del pago.
 */
@Component
public class RestPaymentGateway implements PaymentGateway {

    private static final Logger log = LoggerFactory.getLogger(RestPaymentGateway.class);
    private static final String CIRCUIT_BREAKER_NAME = "paymentGateway";
    private static final String RETRY_NAME = "paymentGatewayRetry";

    private final RestTemplate restTemplate;
    private final String gatewayBaseUrl;
    private final String gatewayApiKey;
    private final int connectionTimeout;
    private final int readTimeout;

    public RestPaymentGateway(
            RestTemplate restTemplate,
            @Value("${payment.gateway.base-url:https://api.payment-gateway.example.com}") String gatewayBaseUrl,
            @Value("${payment.gateway.api-key:demo-api-key}") String gatewayApiKey,
            @Value("${payment.gateway.connection-timeout:5000}") int connectionTimeout,
            @Value("${payment.gateway.read-timeout:10000}") int readTimeout) {
        this.restTemplate = restTemplate;
        this.gatewayBaseUrl = gatewayBaseUrl;
        this.gatewayApiKey = gatewayApiKey;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "processPaymentFallback")
    @Retry(name = RETRY_NAME)
    public Mono<String> processPayment(Payment payment) {
        log.info("Iniciando procesamiento de pago con gateway externo: transactionRef={}, amount={} {}",
                payment.transactionReference(), payment.amount(), payment.currency());

        return Mono.fromCallable(() -> {
            HttpHeaders headers = createHeaders();
            Map<String, Object> requestBody = buildRequestBody(payment);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            String endpoint = gatewayBaseUrl + "/v1/payments";
            log.debug("Enviando solicitud al endpoint: {}", endpoint);

            Map<String, Object> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                request,
                Map.class
            ).getBody();

            if (response == null) {
                log.warn("Respuesta nula del gateway de pagos para referencia: {}", 
                    payment.transactionReference());
                return "ERROR:Respuesta nula del gateway";
            }

            return parseGatewayResponse(response, payment.transactionReference());
        }).timeout(Duration.ofMillis(readTimeout))
          .doOnSuccess(result -> log.info("Pago procesado exitosamente: ref={}, resultado={}",
              payment.transactionReference(), result))
          .doOnError(error -> log.error("Error al procesar pago en gateway: ref={}, error={}",
              payment.transactionReference(), error.getMessage()));
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "verifyPaymentFallback")
    public Mono<String> verifyPayment(String transactionReference) {
        log.info("Verificando estado de pago en gateway: {}", transactionReference);

        return Mono.fromCallable(() -> {
            HttpHeaders headers = createHeaders();
            String endpoint = gatewayBaseUrl + "/v1/payments/" + transactionReference;

            Map<String, Object> response = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
            ).getBody();

            if (response == null) {
                return "UNKNOWN";
            }

            return parseVerificationResponse(response);
        }).timeout(Duration.ofMillis(readTimeout))
          .doOnSuccess(result -> log.info("Verificación completada: ref={}, estado={}",
              transactionReference, result))
          .doOnError(error -> log.error("Error en verificación: ref={}, error={}",
              transactionReference, error.getMessage()));
    }

    @Override
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "cancelPaymentFallback")
    public Mono<String> cancelPayment(String transactionReference) {
        log.info("Cancelando pago en gateway: {}", transactionReference);

        return Mono.fromCallable(() -> {
            HttpHeaders headers = createHeaders();
            String endpoint = gatewayBaseUrl + "/v1/payments/" + transactionReference + "/cancel";

            Map<String, Object> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                Map.class
            ).getBody();

            if (response == null) {
                return "ERROR:Respuesta nula al intentar cancelar";
            }

            return parseCancellationResponse(response);
        }).timeout(Duration.ofMillis(readTimeout))
          .doOnSuccess(result -> log.info("Cancelación procesada: ref={}, resultado={}",
              transactionReference, result))
          .doOnError(error -> log.error("Error al cancelar pago: ref={}, error={}",
              transactionReference, error.getMessage()));
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", gatewayApiKey);
        headers.set("X-Request-ID", UUID.randomUUID().toString());
        headers.set("Accept", "application/json");
        return headers;
    }

    private Map<String, Object> buildRequestBody(Payment payment) {
        Map<String, Object> body = new HashMap<>();
        body.put("transaction_reference", payment.transactionReference());
        body.put("amount", payment.amount().toPlainString());
        body.put("currency", payment.currency());
        body.put("source_account", payment.sourceAccount());
        body.put("destination_account", payment.destinationAccount());
        body.put("channel", payment.channel());
        
        if (payment.description() != null) {
            body.put("description", payment.description());
        }
        if (payment.country() != null) {
            body.put("country", payment.country());
        }
        if (payment.deviceId() != null) {
            body.put("device_id", payment.deviceId());
        }
        if (payment.ipAddress() != null) {
            body.put("ip_address", payment.ipAddress());
        }
        if (payment.deviceType() != null) {
            body.put("device_type", payment.deviceType());
        }
        if (payment.sessionId() != null) {
            body.put("session_id", payment.sessionId());
        }
        
        return body;
    }

    private String parseGatewayResponse(Map<String, Object> response, String transactionRef) {
        Object statusObj = response.get("status");
        Object errorObj = response.get("error");
        Object gatewayRefObj = response.get("gateway_reference");

        if (statusObj == null) {
            log.warn("Respuesta del gateway sin campo status: ref={}", transactionRef);
            return "ERROR:Respuesta inválida del gateway";
        }

        String status = statusObj.toString().toUpperCase();
        
        if ("APPROVED".equals(status) || "SUCCESS".equals(status)) {
            String gatewayRef = gatewayRefObj != null ? gatewayRefObj.toString() : "N/A";
            log.debug("Pago aprobado por gateway: ref={}, gatewayRef={}", transactionRef, gatewayRef);
            return "APPROVED:" + gatewayRef;
        } else if ("PENDING".equals(status)) {
            log.debug("Pago pendiente en gateway: ref={}", transactionRef);
            return "PENDING:" + (errorObj != null ? errorObj.toString() : "Procesando");
        } else if ("REJECTED".equals(status) || "DECLINED".equals(status)) {
            String reason = errorObj != null ? errorObj.toString() : "Rechazado por el gateway";
            log.info("Pago rechazado por gateway: ref={}, razón={}", transactionRef, reason);
            return "REJECTED:" + reason;
        } else {
            log.warn("Estado desconocido del gateway: ref={}, status={}", transactionRef, status);
            return "ERROR:Estado desconocido - " + status;
        }
    }

    private String parseVerificationResponse(Map<String, Object> response) {
        Object statusObj = response.get("status");
        if (statusObj == null) {
            return "UNKNOWN";
        }
        return statusObj.toString().toUpperCase();
    }

    private String parseCancellationResponse(Map<String, Object> response) {
        Object statusObj = response.get("status");
        if (statusObj == null) {
            return "ERROR:Respuesta inválida";
        }
        String status = statusObj.toString().toUpperCase();
        return "CANCELLED".equals(status) ? "CANCELLED" : "ERROR:" + status;
    }

    @SuppressWarnings("unused")
    private Mono<String> processPaymentFallback(Payment payment, Throwable error) {
        log.error("Fallback activado para procesamiento de pago: ref={}, error={}",
            payment.transactionReference(), error.getMessage());
        return Mono.just("FALLBACK:Circuit breaker abierto o error temporal - " + error.getMessage());
    }

    @SuppressWarnings("unused")
    private Mono<String> verifyPaymentFallback(String transactionReference, Throwable error) {
        log.error("Fallback activado para verificación de pago: ref={}, error={}",
            transactionReference, error.getMessage());
        return Mono.just("UNKNOWN:Fallback activado");
    }

    @SuppressWarnings("unused")
    private Mono<String> cancelPaymentFallback(String transactionReference, Throwable error) {
        log.error("Fallback activado para cancelación de pago: ref={}, error={}",
            transactionReference, error.getMessage());
        return Mono.just("ERROR:Fallback activado - " + error.getMessage());
    }
}