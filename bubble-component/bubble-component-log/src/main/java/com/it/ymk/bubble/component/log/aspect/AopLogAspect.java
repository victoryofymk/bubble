package com.it.ymk.bubble.component.log.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 根据注解提供日志切面，处理事物、权限、日志等
 *
 * 使用方法：在切点处使用AopLog注解
 *
 * @author yanmingkun
 * @date 2018-12-17 16:14
 */
@Aspect
@Component
public class AopLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopLogAspect.class);

    @Pointcut("@annotation(com.it.ymk.bubble.component.log.annotation.AopLog)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void boBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method Name : [" + methodName + "] ---> AOP before ");
    }

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method Name : [" + methodName + "] ---> AOP after ");
    }

    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method Name : [" + methodName + "] ---> AOP after return ,and result is : " + result.toString());
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method Name : [" + methodName + "] ---> AOP after throwing ,and throwable message is : "
                    + throwable.getMessage());
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        try {
            LOGGER.info("Method Name : [" + methodName + "] ---> AOP around start");
            long startTimeMillis = System.currentTimeMillis();
            //调用 proceed() 方法才会真正的执行实际被代理的方法
            Object result = joinPoint.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            LOGGER.info("Method Name : [" + methodName + "] ---> AOP method exec time millis : " + execTimeMillis);
            LOGGER
                .info("Method Name : [" + methodName + "] ---> AOP around end , and result is : " + result.toString());
            return result;
        } catch (Throwable te) {
            LOGGER.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }
}
