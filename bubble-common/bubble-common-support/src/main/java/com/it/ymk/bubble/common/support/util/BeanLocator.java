package com.it.ymk.bubble.common.support.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author yanmingkun.
 * @version 1.0
 * @Title
 * @date 2016/12/27 0:21
 */
public class BeanLocator {
    private static final Logger       logger             = LoggerFactory.getLogger(BeanLocator.class);
    private static ApplicationContext applicationContext = null;
    private static BeanLocator        instance           = null;

    private BeanLocator() {
    }

    public static BeanLocator getInstance() {
        if (instance == null) {
            Logger var0 = logger;
            synchronized (logger) {
                if (instance == null) {
                    instance = new BeanLocator();
                }
            }
        }

        return instance;
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBeanInstance(String beanName) {
        getInstance();
        return applicationContext.getBean(beanName);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext ac) {
        applicationContext = ac;
    }
}