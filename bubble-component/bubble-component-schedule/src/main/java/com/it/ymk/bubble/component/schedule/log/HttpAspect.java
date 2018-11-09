package com.it.ymk.bubble.component.schedule.log;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理httpl请求、返回日志
 *
 * @author yanmingkun
 * @date 2018-11-06 09:21
 */
@Aspect
@Configuration
public class HttpAspect {
    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    /**
     * 只关注方法名为find前缀的
     */
    @Pointcut("execution(public * com.it.ymk.bubble.component.schedule.controller.JobController.*(..))")
    public void log() {
    }

    /**
     * 执行点之前调用
     * @param point
     */
    @Before("log()")
    public void invokeBefore(JoinPoint point) {
        String realClassName = getRealClassName(point);
        logger.info("调用-----{}执行{}方法之前", realClassName, getMethodName(point));
        //记录http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从request中获取http请求的url/请求的方法类型／响应该http请求的类方法／IP地址／请求中的参数

        //url
        logger.info("url={}", request.getRequestURI());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //类方法
        logger.info("class_method={}.{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
        //参数
        logger.info("args={}", JSONObject.toJSONString(point.getArgs()));
    }

    /**
     * 执行点之后调用
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void invokeAfter(Object object) {
        logger.info("结果={}", object);
    }

    /**
     * 执行点之后调用
     * @param point
     */
    @After("log()")
    public void invokeAfter(JoinPoint point) {
        String realClassName = getRealClassName(point);
        logger.info("调用-----{}执行{}方法之后", realClassName, getMethodName(point));
    }

    /**
     * 获取被代理对象的真实类全名
     * @param point 连接点对象
     * @return 类全名
     */
    private String getRealClassName(JoinPoint point) {
        return point.getTarget().getClass().getName();
    }

    /**
     * 获取代理执行的方法名
     * @param point 连接点对象
     * @return 调用方法名
     */
    private String getMethodName(JoinPoint point) {
        return point.getSignature().getName();
    }
}
