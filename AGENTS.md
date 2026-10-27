# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Optimización de Flujos de Pago**.

| | |
|---|---|
| Tema | Experiencia en Frameworks |
| Nivel | advanced-l1 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con CQRS para segregación de comandos y consultas |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- org.springframework.boot:spring-boot-starter-aop n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- io.github.resilience4j:resilience4j-reactor 2.2.0
- org.springdoc:springdoc-openapi-starter-webmvc-ui 2.9.1
- com.amazonaws:aws-java-sdk-sqs n/a
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-junit-jupiter n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Diseño Inicial del Flujo de Pago**: Diagrama de flujo y descripción textual del proceso de pago.
- **Fase 2 — Implementación del Flujo de Pago**: Implementación funcional del flujo de pago.
- **Fase 3 — Optimización y Refactorización**: Flujo de pago optimizado y refactorizado.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (156)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `com.pragma.payments.domain.ports.PaymentEventPublisher`
      El import com.pragma.payments.domain.ports.PaymentEventPublisher usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/payments/domain/ports/FraudDetectionService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/domain/ports/RiskAssessmentService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/domain/ports/BankingCoreService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/domain/ports/PaymentGateway.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `io.swagger.v3`
      El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setId`
      Se invoca `setId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setAmount`
      Se invoca `setAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setCurrency`
      Se invoca `setCurrency` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setSourceAccount`
      Se invoca `setSourceAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.sourceAccount`
      Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setDestinationAccount`
      Se invoca `setDestinationAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setStatus`
      Se invoca `setStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setTransactionReference`
      Se invoca `setTransactionReference` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setOperationNumber`
      Se invoca `setOperationNumber` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.operationNumber`
      Se invoca `operationNumber` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setChannel`
      Se invoca `setChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setFraudDetectionResult`
      Se invoca `setFraudDetectionResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.fraudDetectionResult`
      Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setRiskAssessmentResult`
      Se invoca `setRiskAssessmentResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.riskAssessmentResult`
      Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setBankingCoreResult`
      Se invoca `setBankingCoreResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.bankingCoreResult`
      Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setRetryCount`
      Se invoca `setRetryCount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.retryCount`
      Se invoca `retryCount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.createdAt`
      Se invoca `createdAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.updatedAt`
      Se invoca `updatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getId`
      Se invoca `getId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getAmount`
      Se invoca `getAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getCurrency`
      Se invoca `getCurrency` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getSourceAccount`
      Se invoca `getSourceAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getDestinationAccount`
      Se invoca `getDestinationAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getStatus`
      Se invoca `getStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getTransactionReference`
      Se invoca `getTransactionReference` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getOperationNumber`
      Se invoca `getOperationNumber` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getChannel`
      Se invoca `getChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getFraudDetectionResult`
      Se invoca `getFraudDetectionResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getRiskAssessmentResult`
      Se invoca `getRiskAssessmentResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getBankingCoreResult`
      Se invoca `getBankingCoreResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getRetryCount`
      Se invoca `getRetryCount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getCreatedAt`
      Se invoca `getCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.sourceAccount`
      Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.description`
      Se invoca `description` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.country`
      Se invoca `country` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.deviceId`
      Se invoca `deviceId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.ipAddress`
      Se invoca `ipAddress` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.deviceType`
      Se invoca `deviceType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.sessionId`
      Se invoca `sessionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `RiskAssessmentService.assessRiskWithTimeout`
      Se invoca `assessRiskWithTimeout` sobre `RiskAssessmentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.fraudDetectionResult`
      Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.riskAssessmentResult`
      Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.bankingCoreResult`
      Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `PaymentGateway.confirmPayment`
      Se invoca `confirmPayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `GetPaymentQuery.paymentId`
      Se invoca `paymentId` sobre `GetPaymentQuery`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.payerId`
      Se invoca `payerId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.payeeId`
      Se invoca `payeeId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.paymentMethod`
      Se invoca `paymentMethod` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.payerId`
      Se invoca `payerId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.payeeId`
      Se invoca `payeeId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.paymentMethod`
      Se invoca `paymentMethod` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.accountType`
      Se invoca `accountType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.merchantCategory`
      Se invoca `merchantCategory` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.sourceAccount`
      Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.paymentType`
      Se invoca `paymentType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.transactionReference`
      Se invoca `transactionReference` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.sourceAccount`
      Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.destinationAccount`
      Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.paymentType`
      Se invoca `paymentType` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.description`
      Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.sourceAccount`
      Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.destinationAccount`
      Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.paymentType`
      Se invoca `paymentType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.fraudDetectionResult`
      Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.riskAssessmentResult`
      Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.bankingCoreResult`
      Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.createdAt`
      Se invoca `createdAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.updatedAt`
      Se invoca `updatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.fraudDetectionResult`
      Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.riskAssessmentResult`
      Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.bankingCoreResult`
      Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `PaymentGateway.executePayment`
      Se invoca `executePayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.id`
      Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.transactionReference`
      Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.operationNumber`
      Se invoca `operationNumber` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.channel`
      Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.amount`
      Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.currency`
      Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.status`
      Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.id`
      Se invoca `id` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.transactionReference`
      Se invoca `transactionReference` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.status`
      Se invoca `status` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `GetPaymentQueryHandler.handleByAccount`
      Se invoca `handleByAccount` sobre `GetPaymentQueryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (28)

- `pom.xml`
- `src/main/java/com/pragma/payments/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payments/domain/model/Payment.java`
- `src/main/java/com/pragma/payments/domain/ports/PaymentRepository.java`
- `src/main/java/com/pragma/payments/domain/ports/FraudDetectionService.java`
- `src/main/java/com/pragma/payments/domain/ports/RiskAssessmentService.java`
- `src/main/java/com/pragma/payments/domain/ports/BankingCoreService.java`
- `src/main/java/com/pragma/payments/domain/ports/PaymentGateway.java`
- `src/main/java/com/pragma/payments/domain/exception/PaymentTimeoutException.java`
- `src/main/java/com/pragma/payments/domain/exception/PaymentProcessingException.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java`
- `src/main/java/com/pragma/payments/infrastructure/dto/PaymentRequest.java`
- `src/main/java/com/pragma/payments/infrastructure/dto/PaymentResponse.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java`
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommand.java`
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java`
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQuery.java`
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java`
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java`
- `src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java`
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java`
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java`
- `src/main/resources/application-dev.yml`
- `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java`
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payments`
- `src/main/java/com/pragma/payments/application`
- `src/main/java/com/pragma/payments/application/commands`
- `src/main/java/com/pragma/payments/application/queries`
- `src/main/java/com/pragma/payments/domain`
- `src/main/java/com/pragma/payments/domain/model`
- `src/main/java/com/pragma/payments/domain/ports`
- `src/main/java/com/pragma/payments/domain/exception`
- `src/main/java/com/pragma/payments/infrastructure`
- `src/main/java/com/pragma/payments/infrastructure/adapters`
- `src/main/java/com/pragma/payments/infrastructure/config`
- `src/main/java/com/pragma/payments/infrastructure/controllers`
- `src/main/java/com/pragma/payments/infrastructure/dto`
- `src/main/java/com/pragma/payments/infrastructure/messaging`
- `src/main/resources`
- `src/test/java/com/pragma/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con CQRS para segregación de comandos y consultas**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Brecha que el reto ataca: Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM). � El candidato es un desarrollador Backend Advanced con experiencia en estructuras de datos y patrones GRASP. Est� listo para profundizar en frameworks como Spring Boot.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
