package com.jwt.starter.aspects;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ExecutionTimeTrackerForServiceAspect {

    @SneakyThrows
    @Around(value = "execution(* com.jwt.starter.service.*.*(..))")
    public Object trackExecutionTime(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Execution time for {} of service class is {} ms",
                proceedingJoinPoint.getSignature().getName(), endTime - startTime);
        return proceed;
    }
}
