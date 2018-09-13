package com.it.ymk.bubble;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yanmingkun
 * @date 2018-09-11 16:17
 */
@Controller
@SpringBootApplication
@MapperScan("com.it.ymk.bubble.**.dao")
@ImportResource(locations = { "classpath:cxf.xml" })
public class Application {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello Spring Boot World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
