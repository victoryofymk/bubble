package com.it.ymk.bubble.component.schedule.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 为定时任务请求提供切面，在执行点前后处理
 *
 * @author yanmingkun
 * @date 2018-11-06 09:21
 */
@Aspect
@Configuration
public class HttpAspect {
    private static final Logger logger    = LoggerFactory.getLogger(HttpAspect.class);

    private static ThreadLocal<HashMap> threadmap = ThreadLocal.withInitial(HashMap::new);

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
        Object[] args = point.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
                || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        logger.info("args={}", JSONObject.toJSONString(arguments));
        Date startTime = new Date();
        threadmap.get().put("startTime", startTime);
    }

    /**
     * 执行点之后调用
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void invokeAfter(Object object) {
        Date endTime = new Date();
        Date startTime = (Date) threadmap.get().get("startTime");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long threadId = Thread.currentThread().getId();
        logger.info("结果={},执行时长={}ms,开始时间={},结束时间={},线程Id={}", object, (endTime.getTime() - startTime.getTime()),
            sd.format(startTime), sd.format(endTime), threadId);
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
