package com.it.ymk.bubble.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，非常适合在应用程序启动之初进行一些数据初始化的工作
 * 使用方法：
 * 1.实现CommandLineRunner
 * 2.添加 @Order 注解的实现类最先执行，并且@Order()里面的值越小启动越早
 *
 * 使用ApplicationRunner也可以达到相同的目的
 * 
 * @author yanmingkun
 * @date 2018-09-27 14:43
 */
@Component
@Order(0)
public class OrderRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("The OrderRunner start to initialize ...");
    }
}
