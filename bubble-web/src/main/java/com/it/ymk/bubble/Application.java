package com.it.ymk.bubble;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ymk.bubble.core.utils.ErrorUtil;

/**
 * springboot应用启动类，引用 cxf，使用mapper 扫描
 *
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

    /**
     * 异常访问
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/errorIndex")
    @ResponseBody
    public String testError() throws Exception {
        ErrorUtil.randomException();
        return "Hello Spring Boot World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
