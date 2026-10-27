package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

/**
 * Puerto para el servicio de evaluación de riesgos (buró de riesgos).
 * Define la контракт for interacting with the external risk assessment service
 * that evaluates credit risk and determines risk scores for payments.
 * This port is implemented by an adapter in the infrastructure layer.
 */
public interface RiskAssessmentService {

    /**
     * Evalúa el riesgo asociado a un pago consultando el buró de riesgos.
     * Este método puede tomar más de 2 segundos en responder, por lo que
     * el caller debe configurar timeouts apropiados con Resilience4j.
     *
     * @param payment El pago a evaluar
     * @return Mono con el resultado de la evaluación de riesgo
     */
    Mono<String> assessRisk(Payment payment);

    /**
     * Evalúa el riesgo con fallback definido para manejar fallos.
     * El fallback se activa cuando el servicio del buró de riesgos
     * no responde dentro del timeout esperado o devuelve un error.
     *
     * @param payment El pago a evaluar
     * @param error El error que causó el fallback
     * @return Mono con el resultado del fallback
     */
    default Mono<String> assessRiskWithFallback(Payment payment, Throwable error) {
        return Mono.just("RISK_ASSESSMENT_PENDING_REVIEW");
    }

    /**
     * Verifica si el riesgo del pago está dentro de los límites aceptables.
     * Utiliza el resultado de assessRisk para determinar si el pago
     * puede proceder o debe ser bloqueado por alto riesgo.
     *
     * @param riskResult Resultado de la evaluación de riesgo
     * @return true si el riesgo es aceptable, false si debe ser rechazado
     */
    default boolean isRiskAcceptable(String riskResult) {
        return riskResult != null &&
               !riskResult.contains("HIGH_RISK") &&
               !riskResult.contains("BLOCKED") &&
               !riskResult.contains("REJECTED");
    }

    /**
     * Obtiene el código de riesgo interpretado para el logging.
     *
     * @param riskResult Resultado raw del servicio
     * @return Descripción legible del nivel de riesgo
     */
    default String getRiskLevelDescription(String riskResult) {
        if (riskResult == null) {
            return "UNKNOWN";
        }
        if (riskResult.contains("LOW_RISK")) {
            return "LOW";
        } else if (riskResult.contains("MEDIUM_RISK")) {
            return "MEDIUM";
        } else if (riskResult.contains("HIGH_RISK")) {
            return "HIGH";
        } else if (riskResult.contains("BLOCKED")) {
            return "BLOCKED";
        }
        return "UNDEFINED";
    }
}