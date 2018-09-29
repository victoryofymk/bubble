package com.it.ymk.bubble.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yanmingkun
 * @date 2018-09-14 16:59
 */
@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
@Profile({ "dev", "test" })
public class Swagger2Config {
    /**
     * 接口版本号
     */
    private final String  VERSION              = "版本：1.0";
    /**
     * 接口大标题
     */
    private final String  TITLE                = "标题：XX_接口文档";
    /**
     * 具体的描述
     */
    private final String  DESCRIPTION          = "描述：接口说明文档..";
    /**
     * 服务说明url
     */
    private final String  TERMS_OF_SERVICE_URL = "http://www.xxx.com";
    /**
     * licence
     */
    private final String  LICENSE              = "MIT";
    /**
     * licnce url
     */
    private final String  LICENSE_URL          = "https://mit-license.org/";
    /**
     * 接口作者联系方式
     */
    private final Contact CONTACT              = new Contact("starlord", "https://github.com/victoryofymk",
        "starlord.yan@gmail.com");
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .license(LICENSE)
                .licenseUrl(LICENSE_URL)
                .contact(CONTACT)
                .version(VERSION)
                .build())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.it.ymk"))
            .paths(PathSelectors.any())
            .build();
    }

}
