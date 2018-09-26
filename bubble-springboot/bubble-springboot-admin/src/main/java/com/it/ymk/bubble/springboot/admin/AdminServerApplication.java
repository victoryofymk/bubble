package com.it.ymk.bubble.springboot.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * admin应用启动类
 *
 * @author yanmingkun
 * @date 2018-09-11 16:17
 */
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
