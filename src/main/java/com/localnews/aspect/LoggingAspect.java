package com.localnews.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(com.localnews.controller.*)")
    public void anyControllerOperation() {};

    @Before("anyControllerOperation()")
    public void beforeAnyControllerOperationAdvice(JoinPoint joinPoint){
        log.info("Before -> Method: {}",
                joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "anyControllerOperation()", returning = "result")
    public void afterReturningAnyControllerOperationAdvice(JoinPoint joinPoint, Object result){
        log.info("After Returning -> Method: {}, Result: {}",
                joinPoint.getSignature(), result.toString());
    }

    @AfterThrowing(value = "anyControllerOperation()", throwing = "exception")
    public void afterThrowingAnyControllerExceptionAdvice(JoinPoint joinPoint, Exception exception){
        log.info("After Throwing -> Method: {}, Exception: {}",
                joinPoint.getSignature(), exception.getMessage());
    }

}
