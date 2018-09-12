package com.it.ymk.bubble;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author yanmingkun
 * @date 2018-09-11 16:17
 */
@SpringBootApplication
@MapperScan("com.it.ymk.bubble.**.dao")
@ImportResource(locations = { "classpath:cxf.xml" })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
