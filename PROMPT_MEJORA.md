# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `com.pragma.payments.domain.ports.PaymentEventPublisher`: El import com.pragma.payments.domain.ports.PaymentEventPublisher usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/payments/domain/ports/FraudDetectionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/ports/RiskAssessmentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/ports/BankingCoreService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/domain/ports/PaymentGateway.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `io.swagger.v3`: El import io.swagger.v3.oas.annotations.Operation pertenece a io.swagger.v3, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setId`: Se invoca `setId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setAmount`: Se invoca `setAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setCurrency`: Se invoca `setCurrency` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setSourceAccount`: Se invoca `setSourceAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.sourceAccount`: Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setDestinationAccount`: Se invoca `setDestinationAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setStatus`: Se invoca `setStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setTransactionReference`: Se invoca `setTransactionReference` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setOperationNumber`: Se invoca `setOperationNumber` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.operationNumber`: Se invoca `operationNumber` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setChannel`: Se invoca `setChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setFraudDetectionResult`: Se invoca `setFraudDetectionResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.fraudDetectionResult`: Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setRiskAssessmentResult`: Se invoca `setRiskAssessmentResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.riskAssessmentResult`: Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setBankingCoreResult`: Se invoca `setBankingCoreResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.bankingCoreResult`: Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setRetryCount`: Se invoca `setRetryCount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.retryCount`: Se invoca `retryCount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.createdAt`: Se invoca `createdAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `Payment.updatedAt`: Se invoca `updatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getId`: Se invoca `getId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getAmount`: Se invoca `getAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getCurrency`: Se invoca `getCurrency` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getSourceAccount`: Se invoca `getSourceAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getDestinationAccount`: Se invoca `getDestinationAccount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getStatus`: Se invoca `getStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getTransactionReference`: Se invoca `getTransactionReference` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getOperationNumber`: Se invoca `getOperationNumber` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getChannel`: Se invoca `getChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getFraudDetectionResult`: Se invoca `getFraudDetectionResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getRiskAssessmentResult`: Se invoca `getRiskAssessmentResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getBankingCoreResult`: Se invoca `getBankingCoreResult` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getRetryCount`: Se invoca `getRetryCount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getCreatedAt`: Se invoca `getCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java` — `PaymentEntity.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.sourceAccount`: Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.description`: Se invoca `description` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.country`: Se invoca `country` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.deviceId`: Se invoca `deviceId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.ipAddress`: Se invoca `ipAddress` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.deviceType`: Se invoca `deviceType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java` — `Payment.sessionId`: Se invoca `sessionId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `RiskAssessmentService.assessRiskWithTimeout`: Se invoca `assessRiskWithTimeout` sobre `RiskAssessmentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.fraudDetectionResult`: Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.riskAssessmentResult`: Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `Payment.bankingCoreResult`: Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java` — `PaymentGateway.confirmPayment`: Se invoca `confirmPayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `GetPaymentQuery.paymentId`: Se invoca `paymentId` sobre `GetPaymentQuery`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.payerId`: Se invoca `payerId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.payeeId`: Se invoca `payeeId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.paymentMethod`: Se invoca `paymentMethod` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.payerId`: Se invoca `payerId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.payeeId`: Se invoca `payeeId` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.paymentMethod`: Se invoca `paymentMethod` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.accountType`: Se invoca `accountType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java` — `Payment.merchantCategory`: Se invoca `merchantCategory` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.sourceAccount`: Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java` — `Payment.paymentType`: Se invoca `paymentType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.transactionReference`: Se invoca `transactionReference` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.sourceAccount`: Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.destinationAccount`: Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.paymentType`: Se invoca `paymentType` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `PaymentRequest.description`: Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.sourceAccount`: Se invoca `sourceAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.destinationAccount`: Se invoca `destinationAccount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.paymentType`: Se invoca `paymentType` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.fraudDetectionResult`: Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.riskAssessmentResult`: Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.bankingCoreResult`: Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.createdAt`: Se invoca `createdAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java` — `Payment.updatedAt`: Se invoca `updatedAt` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.fraudDetectionResult`: Se invoca `fraudDetectionResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.riskAssessmentResult`: Se invoca `riskAssessmentResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java` — `Payment.bankingCoreResult`: Se invoca `bankingCoreResult` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `PaymentGateway.executePayment`: Se invoca `executePayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.id`: Se invoca `id` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.transactionReference`: Se invoca `transactionReference` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.operationNumber`: Se invoca `operationNumber` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.channel`: Se invoca `channel` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.amount`: Se invoca `amount` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.currency`: Se invoca `currency` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `Payment.status`: Se invoca `status` sobre `Payment`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.id`: Se invoca `id` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.transactionReference`: Se invoca `transactionReference` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `PaymentResponse.status`: Se invoca `status` sobre `PaymentResponse`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java` — `GetPaymentQueryHandler.handleByAccount`: Se invoca `handleByAccount` sobre `GetPaymentQueryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Contexto técnico original
Chapter Backend, Especialidad Desarrollador, Tecnolog�a Java, Advanced � Experiencia comprobable en al menos un marco de trabajo (Framework) en su lenguaje de programaci�n principal (por ejemplo, Spring Framework en Java o ASP .net core en C#) y puede realizar el enrutamiento de solicitudes, controlar las variables de entorno, generar registros de rastros (Logs) y conectarse con bases de datos utilizando un Mapeador Relacional de Objetos (ORM). � El candidato es un desarrollador Backend Advanced con experiencia en estructuras de datos y patrones GRASP. Est� listo para profundizar en frameworks como Spring Boot.

### Reto
- Tema: Experiencia en Frameworks
- Seniority: advanced-l1
- Tipo: practical
- Título: Optimización de Flujos de Pago
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Diseño Inicial del Flujo de Pago — objetivo: Definir el flujo básico de un pago desde la solicitud hasta la confirmación. — entregable (NO resolver): Diagrama de flujo y descripción textual del proceso de pago.
- Fase 2: Implementación del Flujo de Pago — objetivo: Implementar el flujo de pago definido, asegurando la eficiencia y la seguridad en las transacciones. — entregable (NO resolver): Implementación funcional del flujo de pago.
- Fase 3: Optimización y Refactorización — objetivo: Optimizar el flujo de pago para mejorar el rendimiento y la escalabilidad. — entregable (NO resolver): Flujo de pago optimizado y refactorizado.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payments</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payments</name>
    <description>Plataforma de procesamiento de pagos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- AWS SDK -->
        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-sqs</artifactId>
        </dependency>

        <!-- SpringDoc OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.9.1</version>
        </dependency>

        <!-- PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payments/Application.java ===
package com.pragma.payments;



import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.exception.PaymentTimeoutException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.pragma.payments.infrastructure.config.ResilienceConfig;

import java.time.Duration;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(ResilienceConfig.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CircuitBreakerConfig defaultCircuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .recordExceptions(
                    org.springframework.web.client.HttpServerErrorException.class,
                    org.springframework.web.client.ResourceAccessException.class,
                    com.pragma.payments.domain.exception.PaymentTimeoutException.class,
                    com.pragma.payments.domain.exception.PaymentProcessingException.class
                )
                .build();
    }

    @Bean
    public TimeLimiterConfig defaultTimeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(2))
                .cancelRunningFuture(true)
                .build();
    }

    @Bean(name = "riskAssessmentExecutor")
    public Executor riskAssessmentExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("risk-assessment-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "paymentProcessingExecutor")
    public Executor paymentProcessingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("payment-processing-");
        executor.initialize();
        return executor;
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  servlet:
    context-path: /api/payments

spring:
  application:
    name: payments-service
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_db
    username: payments_user
    password: payments_password
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
        jdbc:
          time_zone: UTC
        connection:
          provider_disables_autocommit: true

resilience4j:
  circuitbreaker:
    instances:
      riskAssessment:
        baseConfig: default
        failureRateThreshold: 60
        waitDurationInOpenState: 5000
        permittedNumberOfCallsInHalfOpenState: 5
        slidingWindowSize: 10
      bankingCore:
        baseConfig: default
        failureRateThreshold: 40
        waitDurationInOpenState: 3000
      paymentGateway:
        baseConfig: default
        failureRateThreshold: 30
        waitDurationInOpenState: 2000
  timelimiter:
    instances:
      riskAssessment:
        timeoutDuration: 2500ms
      bankingCore:
        timeoutDuration: 1500ms
      paymentGateway:
        timeoutDuration: 1000ms
  retry:
    instances:
      riskAssessment:
        maxRetryAttempts: 2
        waitDuration: 500ms
        retryExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - org.springframework.web.client.ResourceAccessException
          - com.pragma.payments.domain.exception.PaymentTimeoutException
      bankingCore:
        maxRetryAttempts: 3
        waitDuration: 300ms
      paymentGateway:
        maxRetryAttempts: 2
        waitDuration: 200ms
  ratelimiter:
    instances:
      riskAssessment:
        limitForPeriod: 100
        limitRefreshPeriod: 1s
        timeoutDuration: 100ms
      paymentProcessing:
        limitForPeriod: 1500
        limitRefreshPeriod: 1s
        timeoutDuration: 0

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}
    distribution:
      percentiles-histogram:
        http.server.requests: true
      slo:
        http.server.requests: 100ms,200ms,500ms

logging:
  level:
    root: INFO
    com.pragma.payments: DEBUG
    org.springframework.web: INFO
    org.hibernate: ERROR
    io.github.resilience4j: INFO

// === ARCHIVO: src/main/java/com/pragma/payments/domain/model/Payment.java ===
package com.pragma.payments.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Payment(
    @NotNull
    UUID id,

    @NotNull
    @Positive
    BigDecimal amount,

    @NotBlank
    @Size(max = 3)
    String currency,

    @NotNull
    UUID customerId,

    @NotNull
    UUID orderId,

    @NotBlank
    @Size(max = 50)
    String paymentMethod,

    @NotBlank
    @Size(max = 20)
    String status,

    @NotNull
    LocalDateTime createdAt,

    LocalDateTime updatedAt,

    @NotBlank
    @Size(max = 50)
    String transactionReference,

    @NotNull
    @PositiveOrZero
    Integer retryCount,

    String fraudDetectionResult,

    String riskAssessmentResult,

    String bankingCoreResult
) {
    public Payment {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be a 3-letter code");
        }
        if (retryCount == null) {
            retryCount = 0;
        }
        if (status == null) {
            status = "PENDING";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Payment withStatus(String newStatus) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public Payment withFraudDetectionResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount,
            result,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public Payment withRiskAssessmentResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            result,
            this.bankingCoreResult
        );
    }

    public Payment withBankingCoreResult(String result) {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.transactionReference,
            this.retryCount,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            result
        );
    }

    public Payment incrementRetryCount() {
        return new Payment(
            this.id,
            this.amount,
            this.currency,
            this.customerId,
            this.orderId,
            this.paymentMethod,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.transactionReference,
            this.retryCount + 1,
            this.fraudDetectionResult,
            this.riskAssessmentResult,
            this.bankingCoreResult
        );
    }

    public boolean isApproved() {
        return "APPROVED".equals(this.status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(this.status);
    }

    public boolean isPending() {
        return "PENDING".equals(this.status);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/PaymentRepository.java ===
package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    Optional<Payment> findByTransactionReference(String transactionReference);

    void deleteById(UUID id);
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/FraudDetectionService.java ===
package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

public interface FraudDetectionService {
    Mono<String> assessFraudRisk(Payment payment);

    default Mono<String> assessFraudRiskWithFallback(Payment payment, Throwable error) {
        return Mono.just("FALLBACK: Fraud detection unavailable");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/RiskAssessmentService.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/BankingCoreService.java ===
package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Puerto para el servicio del core bancario.
 * Define la контракт for interacting with the bank's core system
 * to process payments, verify accounts, and manage transactions.
 * This port is implemented by an adapter in the infrastructure layer.
 */
public interface BankingCoreService {

    /**
     * Procesa un pago a través del core bancario.
     * Esta operación implica la transferencia de fondos desde la cuenta
     * origen hacia la cuenta destino, actualizando los saldos correspondientes.
     *
     * @param payment El pago a procesar
     * @return Mono con el resultado del procesamiento bancario
     */
    Mono<String> processPayment(Payment payment);

    /**
     * Verifica si una cuenta existe y está activa en el core bancario.
     * Utilizado para validar las cuentas origen y destino antes de procesar.
     *
     * @param accountNumber Número de cuenta a verificar
     * @return Mono con el estado de la cuenta
     */
    Mono<String> verifyAccount(String accountNumber);

    /**
     * Obtiene el saldo disponible de una cuenta.
     *
     * @param accountNumber Número de cuenta
     * @return Mono con el saldo disponible
     */
    Mono<BigDecimal> getAccountBalance(String accountNumber);

    /**
     * Confirma un pago previamente procesado.
     * Utilizado para confirmar la ejecución exitosa de una transacción.
     *
     * @param transactionId ID de la transacción
     * @return Mono con la confirmación
     */
    Mono<String> confirmPayment(UUID transactionId);

    /**
     * Revierte un pago previamente procesado.
     * Se utiliza en casos de fallo o disputa.
     *
     * @param transactionId ID de la transacción a revertir
     * @param reason Razón de la reversión
     * @return Mono con el resultado de la reversión
     */
    Mono<String> reversePayment(UUID transactionId, String reason);

    /**
     * Verifica si el procesamiento fue exitoso basado en el resultado.
     *
     * @param result Resultado del procesamiento
     * @return true si el procesamiento fue exitoso
     */
    default boolean isPaymentSuccessful(String result) {
        return result != null &&
               (result.contains("APPROVED") ||
                result.contains("SUCCESS") ||
                result.contains("COMPLETED"));
    }

    /**
     * Obtiene el mensaje de error del resultado bancario para logging.
     *
     * @param result Resultado del procesamiento
     * @return Mensaje de error o null si no hay error
     */
    default String extractErrorMessage(String result) {
        if (result == null) {
            return "No response from banking core";
        }
        if (result.contains("ERROR:")) {
            int start = result.indexOf("ERROR:") + 6;
            int end = result.indexOf(";", start);
            return end > start ? result.substring(start, end) : result.substring(start);
        }
        return null;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/ports/PaymentGateway.java ===
package com.pragma.payments.domain.ports;

import com.pragma.payments.domain.model.Payment;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Puerto para el gateway de pagos externo.
 * Define la контракт que abstrae la comunicación con el proveedor
 * de procesamiento de pagos (PSP). El gateway maneja la comunicación
 * con redes de tarjetas, procesadores de pago y otros proveedores externos.
 * This port is implemented by an adapter in the infrastructure layer.
 */
public interface PaymentGateway {

    /**
     * Autoriza un pago a través del gateway externo.
     * Esta operación verifica que la tarjeta/cuenta tenga fondos suficientes
     * y que la transacción esté autorizada para proceder.
     *
     * @param payment El pago a autorizar
     * @return Mono con el resultado de la autorización
     */
    Mono<String> authorizePayment(Payment payment);

    /**
     * Captura un pago previamente autorizado.
     * La captura confirma el cargo a la tarjeta/cuenta del cliente.
     *
     * @param authorizationId ID de la autorización previa
     * @param amount Monto a capturar
     * @return Mono con el resultado de la captura
     */
    Mono<String> capturePayment(String authorizationId, java.math.BigDecimal amount);

    /**
     * Cancela una autorización previa.
     * Utilizado cuando no se va a completar la transacción.
     *
     * @param authorizationId ID de la autorización a cancelar
     * @return Mono con el resultado de la cancelación
     */
    Mono<String> cancelAuthorization(String authorizationId);

    /**
     * Reembolsa un pago capturado previamente.
     * Se utiliza para devoluciones o disputas.
     *
     * @param transactionId ID de la transacción a reembolsar
     * @param amount Monto a reembolsar
     * @return Mono con el resultado del reembolso
     */
    Mono<String> refundPayment(UUID transactionId, java.math.BigDecimal amount);

    /**
     * Consulta el estado de una transacción en el gateway.
     *
     * @param transactionId ID de la transacción
     * @return Mono con el estado actual
     */
    Mono<String> getTransactionStatus(UUID transactionId);

    /**
     * Verifica si la autorización fue exitosa.
     *
     * @param authorizationResult Resultado de la autorización
     * @return true si la autorización fue exitosa
     */
    default boolean isAuthorizationSuccessful(String authorizationResult) {
        return authorizationResult != null &&
               (authorizationResult.contains("AUTHORIZED") ||
                authorizationResult.contains("APPROVED") ||
                authorizationResult.contains("SUCCESS"));
    }

    /**
     * Extrae el ID de autorización del resultado para transacciones futuras.
     *
     * @param authorizationResult Resultado de la autorización
     * @return ID de autorización o null si no disponible
     */
    default String extractAuthorizationId(String authorizationResult) {
        if (authorizationResult == null) {
            return null;
        }
        if (authorizationResult.contains("AUTH_ID:")) {
            int start = authorizationResult.indexOf("AUTH_ID:") + 8;
            int end = authorizationResult.indexOf(";", start);
            return end > start ? authorizationResult.substring(start, end).trim()
                               : authorizationResult.substring(start).trim();
        }
        return null;
    }

    /**
     * Determina si el error es retryable (error transitorio).
     *
     * @param errorMessage Mensaje de error
     * @return true si se debe reintentar la operación
     */
    default boolean isRetryableError(String errorMessage) {
        if (errorMessage == null) {
            return false;
        }
        String lowerError = errorMessage.toLowerCase();
        return lowerError.contains("timeout") ||
               lowerError.contains("connection") ||
               lowerError.contains("unavailable") ||
               lowerError.contains("temporary");
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/exception/PaymentTimeoutException.java ===
package com.pragma.payments.domain.exception;

/**
 * Excepción personalizada para manejar timeouts en el flujo de pago.
 * Se lanza cuando el buró de riesgos excede el tiempo máximo de respuesta (2 segundos).
 */
public class PaymentTimeoutException extends RuntimeException {

    private final String serviceName;
    private final long timeoutMillis;
    private final long elapsedMillis;

    public PaymentTimeoutException(String serviceName, long timeoutMillis, long elapsedMillis) {
        super(String.format("Timeout en servicio %s: excedió %d ms (límite: %d ms)", 
                serviceName, elapsedMillis, timeoutMillis));
        this.serviceName = serviceName;
        this.timeoutMillis = timeoutMillis;
        this.elapsedMillis = elapsedMillis;
    }

    public PaymentTimeoutException(String serviceName, long timeoutMillis, long elapsedMillis, Throwable cause) {
        super(String.format("Timeout en servicio %s: excedió %d ms (límite: %d ms)", 
                serviceName, elapsedMillis, timeoutMillis), cause);
        this.serviceName = serviceName;
        this.timeoutMillis = timeoutMillis;
        this.elapsedMillis = elapsedMillis;
    }

    public PaymentTimeoutException(String message) {
        super(message);
        this.serviceName = "unknown";
        this.timeoutMillis = 0;
        this.elapsedMillis = 0;
    }

    public PaymentTimeoutException(String message, Throwable cause) {
        super(message, cause);
        this.serviceName = "unknown";
        this.timeoutMillis = 0;
        this.elapsedMillis = 0;
    }

    public String getServiceName() {
        return serviceName;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public boolean isRetryable() {
        return true;
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/domain/exception/PaymentProcessingException.java ===
package com.pragma.payments.domain.exception;

import java.util.UUID;

/**
 * Excepción personalizada para manejar errores generales en el procesamiento de pagos.
 * Se lanza cuando ocurre un error durante el flujo de pago que no es un timeout.
 */
public class PaymentProcessingException extends RuntimeException {

    private final UUID paymentId;
    private final String transactionReference;
    private final String errorCode;
    private final boolean retryable;

    public PaymentProcessingException(String message) {
        super(message);
        this.paymentId = null;
        this.transactionReference = null;
        this.errorCode = "PAYMENT_ERROR";
        this.retryable = false;
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.paymentId = null;
        this.transactionReference = null;
        this.errorCode = "PAYMENT_ERROR";
        this.retryable = false;
    }

    public PaymentProcessingException(UUID paymentId, String transactionReference, 
                                       String errorCode, String message) {
        super(message);
        this.paymentId = paymentId;
        this.transactionReference = transactionReference;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    public PaymentProcessingException(UUID paymentId, String transactionReference, 
                                       String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.paymentId = paymentId;
        this.transactionReference = transactionReference;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    private static boolean determineRetryable(String errorCode) {
        return "FRAUD_CHECK_ERROR".equals(errorCode) || 
               "RISK_ASSESSMENT_ERROR".equals(errorCode) ||
               "BANKING_CORE_ERROR".equals(errorCode) ||
               "GATEWAY_ERROR".equals(errorCode);
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static PaymentProcessingException fraudCheckFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "FRAUD_CHECK_ERROR",
            "Error en la verificación antifraude"
        );
    }

    public static PaymentProcessingException riskAssessmentFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "RISK_ASSESSMENT_ERROR",
            "Error en la evaluación de riesgo"
        );
    }

    public static PaymentProcessingException bankingCoreFailed(UUID paymentId, String transactionReference) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "BANKING_CORE_ERROR",
            "Error en la comunicación con el core bancario"
        );
    }

    public static PaymentProcessingException gatewayError(UUID paymentId, String transactionReference, Throwable cause) {
        return new PaymentProcessingException(
            paymentId,
            transactionReference,
            "GATEWAY_ERROR",
            "Error en el gateway de pagos",
            cause
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/JpaPaymentRepository.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/dto/PaymentRequest.java ===
package com.pragma.payments.infrastructure.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO que representa la solicitud de creación de un pago.
 * Contiene todos los campos requeridos para procesar una transacción de pago
 * con las validaciones necesarias para garantizar la integridad de los datos.
 */
public record PaymentRequest(
    @NotBlank(message = "La referencia de transacción es obligatoria")
    @Size(min = 1, max = 100, message = "La referencia debe tener entre 1 y 100 caracteres")
    String transactionReference,

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    BigDecimal amount,

    @NotBlank(message = "La moneda es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La moneda debe ser un código ISO de 3 letras")
    String currency,

    @NotBlank(message = "La cuenta de origen es obligatoria")
    @Size(min = 1, max = 50, message = "La cuenta de origen debe tener entre 1 y 50 caracteres")
    String sourceAccount,

    @NotBlank(message = "La cuenta de destino es obligatoria")
    @Size(min = 1, max = 50, message = "La cuenta de destino debe tener entre 1 y 50 caracteres")
    String destinationAccount,

    @NotBlank(message = "El canal de solicitud es obligatorio")
    @Pattern(regexp = "^(API|MOVIL|WEB|CAJERO|TRANSACCIONAL)$", 
             message = "El canal debe ser uno de: API, MOVIL, WEB, CAJERO, TRANSACCIONAL")
    String channel,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String description,

    @Pattern(regexp = "^[A-Z]{2}$", message = "El país debe ser un código ISO de 2 letras")
    String country,

    @Size(max = 50, message = "El identificador de dispositivo no puede exceder 50 caracteres")
    String deviceId,

    @Pattern(regexp = "^[0-9]{1,3}(\\.[0-9]{1,3}){3}$", message = "IP inválida")
    String ipAddress,

    @Size(max = 20, message = "El tipo de dispositivo no puede exceder 20 caracteres")
    String deviceType,

    @Size(max = 100, message = "El identificador de sesión no puede exceder 100 caracteres")
    String sessionId
) {
    /**
     * Valida que la cuenta de origen no sea igual a la de destino.
     */
    public boolean isSourceEqualsDestination() {
        return sourceAccount != null && sourceAccount.equals(destinationAccount);
    }

    /**
     * Obtiene el monto en formato numérico para logging.
     */
    public String getAmountFormatted() {
        return amount.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * Obtiene una versión sanitizada del DTO para logging sin datos sensibles.
     */
    public String toSafeString() {
        return "PaymentRequest{transactionReference='%s', amount=%s, currency='%s', channel='%s'}"
            .formatted(transactionReference, getAmountFormatted(), currency, channel);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/dto/PaymentResponse.java ===
package com.pragma.payments.infrastructure.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO que representa la respuesta de un pago procesado.
 * Contiene toda la información relevante del pago incluyendo estado,
 * identificadores y timestamps para trazabilidad.
 */
public record PaymentResponse(
    UUID id,
    String transactionReference,
    String status,
    BigDecimal amount,
    String currency,
    String sourceAccount,
    String destinationAccount,
    String channel,
    String description,
    String fraudDetectionResult,
    String riskAssessmentResult,
    String bankingCoreResult,
    int retryCount,
    Instant createdAt,
    Instant updatedAt,
    String errorMessage
) {
    /**
     *Factory method para crear una respuesta exitosa de pago.
     */
    public static PaymentResponse success(UUID id, String transactionReference, 
            BigDecimal amount, String currency, String channel, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "APPROVED",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            null
        );
    }

    /**
     * Factory method para crear una respuesta de pago pendiente.
     */
    public static PaymentResponse pending(UUID id, String transactionReference,
            BigDecimal amount, String currency, String channel, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "PENDING",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            null
        );
    }

    /**
     * Factory method para crear una respuesta de pago rechazado.
     */
    public static PaymentResponse rejected(UUID id, String transactionReference,
            BigDecimal amount, String currency, String channel, String reason, Instant createdAt) {
        return new PaymentResponse(
            id,
            transactionReference,
            "REJECTED",
            amount,
            currency,
            null,
            null,
            channel,
            null,
            null,
            null,
            null,
            0,
            createdAt,
            Instant.now(),
            reason
        );
    }

    /**
     * Factory method para crear una respuesta de error.
     */
    public static PaymentResponse error(String transactionReference, String errorMessage) {
        return new PaymentResponse(
            null,
            transactionReference,
            "ERROR",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            Instant.now(),
            Instant.now(),
            errorMessage
        );
    }

    /**
     * Verifica si el pago fue aprobado.
     */
    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    /**
     * Verifica si el pago está pendiente.
     */
    public boolean isPending() {
        return "PENDING".equals(status);
    }

    /**
     * Verifica si el pago fue rechazado.
     */
    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    /**
     * Verifica si hubo un error en el procesamiento.
     */
    public boolean isError() {
        return "ERROR".equals(status);
    }

    /**
     * Obtiene una descripción legible del estado del pago.
     */
    public String getStatusDescription() {
        return switch (status) {
            case "APPROVED" -> "Pago aprobado exitosamente";
            case "REJECTED" -> "Pago rechazado";
            case "PENDING" -> "Pago en proceso de validación";
            case "ERROR" -> "Error en el procesamiento del pago";
            default -> "Estado desconocido";
        };
    }

    /**
     * Obtiene un resumen corto para logging.
     */
    public String toSummary() {
        return "PaymentResponse{id=%s, ref=%s, status=%s, amount=%s}"
            .formatted(
                id != null ? id.toString().substring(0, 8) : "null",
                transactionReference != null ? transactionReference.substring(0, 
                    Math.min(8, transactionReference.length())) : "null",
                status,
                amount != null ? amount.toPlainString() : "null"
            );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/RestPaymentGateway.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/application/commands/CreatePaymentCommand.java ===
package com.pragma.payments.application.commands;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreatePaymentCommand(
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    BigDecimal amount,

    @NotBlank(message = "La moneda es obligatoria")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La moneda debe ser un código ISO 4217 de 3 letras")
    String currency,

    @NotBlank(message = "El número de cuenta del originador es obligatorio")
    @Size(min = 10, max = 34, message = "El número de cuenta debe tener entre 10 y 34 caracteres")
    String senderAccountNumber,

    @NotBlank(message = "El número de cuenta del beneficiario es obligatorio")
    @Size(min = 10, max = 34, message = "El número de cuenta debe tener entre 10 y 34 caracteres")
    String recipientAccountNumber,

    @NotBlank(message = "El canal es obligatorio")
    @Pattern(regexp = "^(API|MOVIL|WEB|CAJERO)$", message = "El canal debe ser API, MOVIL, WEB o CAJERO")
    String channel,

    @NotBlank(message = "El número de operación es obligatorio")
    @Size(min = 1, max = 50, message = "El número de operación debe tener entre 1 y 50 caracteres")
    String operationNumber,

    String description,

    @Pattern(regexp = "^[A-Z]{2}$", message = "El país debe ser un código ISO 3166-1 alpha-2")
    String country
) {
    public CreatePaymentCommand {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/commands/CreatePaymentCommandHandler.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/application/queries/GetPaymentQuery.java ===
package com.pragma.payments.application.queries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record GetPaymentQuery(
    @NotBlank(message = "El identificador del pago es obligatorio")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", 
             message = "El identificador debe ser un UUID válido")
    String paymentId
) {
    public UUID paymentUuid() {
        return UUID.fromString(paymentId);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/application/queries/GetPaymentQueryHandler.java ===
package com.pragma.payments.application.queries;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Component
public class GetPaymentQueryHandler {

    private static final Logger log = LoggerFactory.getLogger(GetPaymentQueryHandler.class);

    private final PaymentRepository paymentRepository;

    public GetPaymentQueryHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Mono<Payment> handle(GetPaymentQuery query) {
        log.info("Handling GetPaymentQuery for paymentId: {}", query.paymentId());

        return Mono.fromCallable(() -> paymentRepository.findById(query.paymentId()))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(paymentOpt -> {
                    if (paymentOpt.isEmpty()) {
                        log.warn("Payment not found with id: {}", query.paymentId());
                        return Mono.empty();
                    }
                    Payment payment = paymentOpt.get();
                    log.debug("Payment found: {} with status: {}", payment.id(), payment.status());
                    return Mono.just(payment);
                })
                .onErrorResume(e -> {
                    log.error("Error retrieving payment {}: {}", query.paymentId(), e.getMessage(), e);
                    return Mono.error(e);
                });
    }

    public Mono<Payment> handleByTransactionReference(String transactionReference) {
        log.info("Handling GetPaymentQuery for transactionReference: {}", transactionReference);

        return Mono.fromCallable(() -> paymentRepository.findByTransactionReference(transactionReference))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(paymentOpt -> {
                    if (paymentOpt.isEmpty()) {
                        log.warn("Payment not found with transactionReference: {}", transactionReference);
                        return Mono.empty();
                    }
                    Payment payment = paymentOpt.get();
                    log.debug("Payment found: {} with status: {}", payment.id(), payment.status());
                    return Mono.just(payment);
                })
                .onErrorResume(e -> {
                    log.error("Error retrieving payment by reference {}: {}", transactionReference, e.getMessage(), e);
                    return Mono.error(e);
                });
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/RestFraudDetectionService.java ===
package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.FraudDetectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class RestFraudDetectionService implements FraudDetectionService {

    private static final Logger log = LoggerFactory.getLogger(RestFraudDetectionService.class);
    private static final String DEFAULT_FRAUD_ASSESSMENT = "APPROVED";

    private final WebClient webClient;
    private final String fraudServiceBaseUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public RestFraudDetectionService(
            WebClient.Builder webClientBuilder,
            @Value("${app.services.fraud-detection.base-url:http://localhost:8081}") String fraudServiceBaseUrl,
            @Value("${app.services.fraud-detection.timeout.connection:5000}") int connectionTimeout,
            @Value("${app.services.fraud-detection.timeout.read:3000}") int readTimeout) {
        this.webClient = webClientBuilder
                .baseUrl(fraudServiceBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.fraudServiceBaseUrl = fraudServiceBaseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    @CircuitBreaker(name = "fraudDetectionCircuitBreaker", fallbackMethod = "assessFraudRiskFallback")
    @Retry(name = "fraudDetectionRetry")
    public Mono<String> assessFraudRisk(Payment payment) {
        log.info("Assessing fraud risk for payment: {}", payment.transactionReference());

        Map<String, Object> requestBody = buildFraudAssessmentRequest(payment);

        Supplier<Mono<String>> fraudAssessmentSupplier = () -> webClient.post()
                .uri("/api/v1/fraud-assessment")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Fraud detection service returned 4xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Fraud assessment rejected by service"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Fraud detection service returned 5xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Fraud detection service unavailable"));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(result -> log.info("Fraud assessment result for {}: {}", payment.transactionReference(), result))
                .doOnError(e -> log.error("Fraud assessment failed for {}: {}", payment.transactionReference(), e.getMessage()));

        return fraudAssessmentSupplier.get();
    }

    @Override
    public Mono<String> assessFraudRiskWithFallback(Payment payment, Throwable error) {
        log.warn("Using fallback for fraud assessment due to: {}", error.getMessage());
        return Mono.just(DEFAULT_FRAUD_ASSESSMENT);
    }

    private Mono<String> assessFraudRiskFallback(Payment payment, Throwable error) {
        log.error("Circuit breaker fallback triggered for fraud detection: {}", error.getMessage());
        return Mono.just(DEFAULT_FRAUD_ASSESSMENT);
    }

    private Map<String, Object> buildFraudAssessmentRequest(Payment payment) {
        return Map.of(
                "transactionReference", payment.transactionReference(),
                "amount", payment.amount().toString(),
                "currency", payment.currency(),
                "payerId", payment.payerId(),
                "payeeId", payment.payeeId(),
                "paymentMethod", payment.paymentMethod(),
                "channel", payment.channel()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/RestRiskAssessmentService.java ===
package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.RiskAssessmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Component
public class RestRiskAssessmentService implements RiskAssessmentService {

    private static final Logger log = LoggerFactory.getLogger(RestRiskAssessmentService.class);
    private static final String DEFAULT_RISK_ASSESSMENT = "MEDIUM_RISK";
    private static final String TIMEOUT_RISK_ASSESSMENT = "HIGH_RISK";

    private final WebClient webClient;
    private final String riskServiceBaseUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public RestRiskAssessmentService(
            WebClient.Builder webClientBuilder,
            @Value("${app.services.risk-assessment.base-url:http://localhost:8082}") String riskServiceBaseUrl,
            @Value("${app.services.risk-assessment.timeout.connection:5000}") int connectionTimeout,
            @Value("${app.services.risk-assessment.timeout.read:5000}") int readTimeout) {
        this.webClient = webClientBuilder
                .baseUrl(riskServiceBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.riskServiceBaseUrl = riskServiceBaseUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    @CircuitBreaker(name = "riskAssessmentCircuitBreaker", fallbackMethod = "assessRiskFallback")
    @TimeLimiter(name = "riskAssessmentTimeLimiter", fallbackMethod = "assessRiskTimeoutFallback")
    @Retry(name = "riskAssessmentRetry")
    public Mono<String> assessRisk(Payment payment) {
        log.info("Assessing risk for payment: {} with timeout: {}ms", payment.transactionReference(), readTimeout);

        Map<String, Object> requestBody = buildRiskAssessmentRequest(payment);

        Supplier<Mono<String>> riskAssessmentSupplier = () -> webClient.post()
                .uri("/api/v1/risk-assessment")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.warn("Risk assessment service returned 4xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Risk assessment rejected by service"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("Risk assessment service returned 5xx: {}", response.statusCode());
                    return Mono.error(new PaymentProcessingException("Risk assessment service unavailable"));
                })
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(result -> log.info("Risk assessment result for {}: {}", payment.transactionReference(), result))
                .doOnError(e -> log.error("Risk assessment failed for {}: {}", payment.transactionReference(), e.getMessage()));

        return riskAssessmentSupplier.get();
    }

    @Override
    public Mono<String> assessRiskWithFallback(Payment payment, Throwable error) {
        if (error instanceof TimeoutException || error.getCause() instanceof TimeoutException) {
            log.warn("Timeout detected in risk assessment for payment: {}", payment.transactionReference());
            return Mono.just(TIMEOUT_RISK_ASSESSMENT);
        }
        log.warn("Using fallback for risk assessment due to: {}", error.getMessage());
        return Mono.just(DEFAULT_RISK_ASSESSMENT);
    }

    private Mono<String> assessRiskFallback(Payment payment, Throwable error) {
        log.error("Circuit breaker fallback triggered for risk assessment: {}", error.getMessage());
        return Mono.just(DEFAULT_RISK_ASSESSMENT);
    }

    private Mono<String> assessRiskTimeoutFallback(Payment payment, Throwable error) {
        log.error("TimeLimiter fallback triggered for risk assessment - timeout exceeded for payment: {}", payment.transactionReference());
        return Mono.just(TIMEOUT_RISK_ASSESSMENT);
    }

    private Map<String, Object> buildRiskAssessmentRequest(Payment payment) {
        return Map.of(
                "transactionReference", payment.transactionReference(),
                "amount", payment.amount().toString(),
                "currency", payment.currency(),
                "payerId", payment.payerId(),
                "payeeId", payment.payeeId(),
                "paymentMethod", payment.paymentMethod(),
                "channel", payment.channel(),
                "accountType", payment.accountType(),
                "merchantCategory", payment.merchantCategory()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/adapters/RestBankingCoreService.java ===
package com.pragma.payments.infrastructure.adapters;

import com.pragma.payments.domain.model.Payment;
import com.pragma.payments.domain.ports.BankingCoreService;
import com.pragma.payments.domain.exception.PaymentProcessingException;
import com.pragma.payments.infrastructure.dto.PaymentRequest;
import com.pragma.payments.infrastructure.dto.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class RestBankingCoreService implements BankingCoreService {

    private static final Logger log = LoggerFactory.getLogger(RestBankingCoreService.class);

    private final WebClient webClient;
    private final String baseUrl;
    private final Duration timeout;

    public RestBankingCoreService(
            @Value("${app.banking-core.base-url:http://localhost:8081}") String baseUrl,
            @Value("${app.banking-core.timeout-ms:5000}") int timeoutMs) {
        this.baseUrl = baseUrl;
        this.timeout = Duration.ofMillis(timeoutMs);
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Mono<String> processPayment(Payment payment) {
        log.info("Enviando pago al core bancario: transactionReference={}, amount={}",
                payment.transactionReference(), payment.amount());

        PaymentRequest request = PaymentRequest.builder()
                .transactionReference(payment.transactionReference())
                .amount(payment.amount())
                .currency(payment.currency())
                .sourceAccount(payment.sourceAccount())
                .destinationAccount(payment.destinationAccount())
                .paymentType(payment.paymentType())
                .build();

        return webClient.post()
                .uri("/api/v1/payments")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(
                                        new PaymentProcessingException("Error del core bancario: " + error))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(
                                        new PaymentProcessingException("Fallo interno del core bancario: " + error))))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(response -> {
                    log.info("Respuesta del core bancario: reference={}, status={}",
                            response.reference(), response.status());
                    return response.status();
                })
                .doOnError(error -> log.error("Error al procesar pago en core bancario: {}", error.getMessage()));
    }

    @Override
    public Mono<String> confirmPayment(String transactionReference) {
        log.info("Confirmando pago en core bancario: {}", transactionReference);

        return webClient.post()
                .uri("/api/v1/payments/{reference}/confirm", transactionReference)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new PaymentProcessingException("Error al confirmar pago")))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(PaymentResponse::status)
                .doOnError(error -> log.error("Error al confirmar pago: {}", error.getMessage()));
    }

    @Override
    public Mono<String> reversePayment(String transactionReference, String reason) {
        log.info("Revirtiendo pago en core bancario: reference={}, reason={}",
                transactionReference, reason);

        return webClient.post()
                .uri("/api/v1/payments/{reference}/reverse", transactionReference)
                .bodyValue(java.util.Map.of("reason", reason))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new PaymentProcessingException("Error al revertir pago")))
                .bodyToMono(PaymentResponse.class)
                .timeout(timeout)
                .map(PaymentResponse::status)
                .doOnError(error -> log.error("Error al revertir pago: {}", error.getMessage()));
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/config/ResilienceConfig.java ===
package com.pragma.payments.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();
        return RetryRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(100))
                .build();
        return RateLimiterRegistry.of(config);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5))
                .build();
        return TimeLimiterRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/controllers/PaymentController.java ===
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

// === ARCHIVO: src/main/java/com/pragma/payments/infrastructure/messaging/SqsPaymentEventPublisher.java ===
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

// === ARCHIVO: src/main/resources/application-dev.yml ===
spring:
  application:
    name: payments
  config:
    import: optional:file:./secrets/application-secrets.yml
  datasource:
    url: jdbc:postgresql://localhost:5432/payments_dev
    username: payments_dev_user
    password: payments_dev_pass
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true

aws:
  region: us-east-1
  sqs:
    payment-events-queue-url: https://sqs.us-east-1.amazonaws.com/123456789012/payment-events-dev
    endpoint: http://localhost:4566
    access-key: local-dev-access-key
    secret-key: local-dev-secret-key
    max-number-of-messages: 10
    wait-time-seconds: 20
    visibility-timeout-seconds: 30

services:
  fraud-detection:
    url: http://localhost:8081/api/v1/fraud-detection
    connection-timeout: 5000
    read-timeout: 10000
    max-connections: 50
  risk-assessment:
    url: http://localhost:8082/api/v1/risk-assessment
    connection-timeout: 10000
    read-timeout: 25000
    max-connections: 30
  banking-core:
    url: http://localhost:8083/api/v1/banking-core
    connection-timeout: 8000
    read-timeout: 15000
    max-connections: 100
  payment-gateway:
    url: http://localhost:8084/api/v1/payment-gateway
    connection-timeout: 5000
    read-timeout: 10000
    max-connections: 50

resilience4j:
  instances:
    fraudDetectionService:
      retry:
        maxAttempts: 3
        waitDuration: 500ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
      circuitBreaker:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 30s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
      rateLimiter:
        registerHealthIndicator: true
        limitForPeriod: 100
        limitRefreshPeriod: 1s
        timeoutDuration: 0
    riskAssessmentService:
      retry:
        maxAttempts: 2
        waitDuration: 1000ms
        retryExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
      circuitBreaker:
        slidingWindowSize: 10
        minimumNumberOfCalls: 3
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 60s
        failureRateThreshold: 40
      timeLimiter:
        timeoutDuration: 5s
        cancelRunningFuture: true
    bankingCoreService:
      retry:
        maxAttempts: 3
        waitDuration: 1000ms
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 1.5
      circuitBreaker:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 30s
        failureRateThreshold: 50
    paymentGateway:
      retry:
        maxAttempts: 2
        waitDuration: 500ms
      circuitBreaker:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 30s
        failureRateThreshold: 50

logging:
  level:
    root: INFO
    com.pragma.payments: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    com.amazonaws: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

server:
  port: 8080
  tomcat:
    threads:
      max: 200
      min-spare: 10
    max-connections: 10000
    accept-count: 100
    connection-timeout: 20000

// === ARCHIVO: src/test/java/com/pragma/payments/application/commands/CreatePaymentCommandHandlerTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/payments/infrastructure/controllers/PaymentControllerTest.java ===
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
```
