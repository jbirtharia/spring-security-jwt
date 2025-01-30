package com.jwt.starter.aspects;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("execution(* com.jwt.starter..*(..)) && " +
            "!execution(* com.jwt.starter.filter..*(..)) && " +
            "!execution(* com.jwt.starter.config..*(..)) && " +
            "!execution(* com.jwt.starter.security..*(..))")
    public void loggerPointcut() {}

    @SneakyThrows
    @Around("loggerPointcut()")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint){

        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        log.info("Class Name: {}  Method Name: {}  Args: {}", className, methodName, objectMapper.writeValueAsString(args));
        Object returnObject = proceedingJoinPoint.proceed();
        log.info("Class Name: {}  Method Name: {}  Return Object: {}", className, methodName, objectMapper.writeValueAsString(returnObject));
        return returnObject;
    }
}
