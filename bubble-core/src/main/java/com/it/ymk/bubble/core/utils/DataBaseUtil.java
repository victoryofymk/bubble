package com.it.ymk.bubble.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yanmingkun.
 * @version 1.0
 * @Title
 * @date 2016/12/27 0:14
 */
public class DataBaseUtil {

    private static final Logger               LOGGER = LoggerFactory.getLogger(DataBaseUtil.class);

    private static AbstractApplicationContext context;
    static {
        try {
            context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
            BeanLocator.setApplicationContext(context);

            LOGGER.info("task服务器启动成功 √");
            synchronized (context) {
                context.wait();
            }
        } catch (Exception e) {
            LOGGER.error("task服务器启动失败 X", e);
            if (context != null) {
                context.destroy();
            }
            System.exit(1);
        }
    }

    public void getDataBaseConnection() {

    }

}
