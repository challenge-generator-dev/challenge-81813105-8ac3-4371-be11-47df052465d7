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