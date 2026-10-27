# Optimización de Flujos de Pago

En el contexto de una plataforma de procesamiento de pagos en una institución financiera, debes diseñar y optimizar los flujos de pago para asegurar la eficiencia y seguridad en las transacciones. Los actores involucrados son el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario y el gateway de pagos. El sistema debe manejar un throughput de 1 500 solicitudes por segundo en hora pico y mantener una latencia máxima de 200 ms. La consistencia entre las solicitudes de pago y las confirmaciones del core bancario es crítica, y el sistema debe ser idempotente respecto al número de operación y canal de solicitud. El modo de falla específico a considerar es el timeout del buró mayor a 2 segundos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Experiencia en Frameworks |
| **Nivel** | advanced-l1 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Diseño Inicial del Flujo de Pago

**Objetivo:** Definir el flujo básico de un pago desde la solicitud hasta la confirmación.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar los actores y sus interacciones en el proceso de pago.
- Establecer los criterios de aceptación para el flujo básico, incluyendo la idempotencia y la consistencia.

**Entregable:** Diagrama de flujo y descripción textual del proceso de pago.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las propiedades operativas del dominio al definir el flujo.
- Piensa en cómo asegurar la idempotencia y la consistencia en cada paso.

</details>

### Fase 2: Implementación del Flujo de Pago

**Objetivo:** Implementar el flujo de pago definido, asegurando la eficiencia y la seguridad en las transacciones.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Implementar el flujo de pago, considerando la idempotencia y la consistencia.
- Asegurar que el sistema maneje el throughput y la latencia especificados.

**Entregable:** Implementación funcional del flujo de pago.

<details>
<summary>Pistas de conocimiento</summary>

- Utiliza las propiedades operativas del dominio para guiar tu implementación.
- Considera cómo manejar los errores y las fallas específicas del dominio.

</details>

### Fase 3: Optimización y Refactorización

**Objetivo:** Optimizar el flujo de pago para mejorar el rendimiento y la escalabilidad.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar áreas de mejora en el flujo de pago.
- Refactorizar el código para mejorar el rendimiento y la escalabilidad.

**Entregable:** Flujo de pago optimizado y refactorizado.

<details>
<summary>Pistas de conocimiento</summary>

- Considera las mejores prácticas de optimización y refactorización.
- Evalúa el impacto de tus cambios en el rendimiento y la escalabilidad del sistema.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es el flujo de pago y cuáles son sus componentes?
- **paraQueSirve**: ¿Para qué sirve el flujo de pago en el contexto de la plataforma de procesamiento de pagos?
- **comoSeUsa**: ¿Cómo se usa el flujo de pago para procesar una transacción?
- **erroresComunes**: ¿Cuáles son los errores comunes en el flujo de pago y cómo se manejan?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación y optimización del flujo de pago?

## Criterios de Evaluacion

- Diseño del flujo de pago considerando idempotencia y consistencia.
- Implementación funcional del flujo de pago con manejo de throughput y latencia.
- Optimización y refactorización del flujo de pago para mejorar rendimiento y escalabilidad.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
