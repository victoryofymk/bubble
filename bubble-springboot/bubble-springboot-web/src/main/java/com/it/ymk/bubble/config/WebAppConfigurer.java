package com.it.ymk.bubble.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 对静态资源处理，static通常存放js、css等，默认访问路径是/**，shiro过滤的static匿名访问是不生效的，因为资源文件访问不带static
 * 本类是将static下面文件的路径映射到/static/**，所以shiro生效了
 *
 * @author yanmingkun
 * @date 2018-10-25 18:07
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
