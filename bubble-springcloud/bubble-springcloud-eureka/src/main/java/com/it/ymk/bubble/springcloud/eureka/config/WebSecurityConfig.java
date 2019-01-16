package com.it.ymk.bubble.springcloud.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yanmingkun
 * @date 2019-01-16 17:11
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 关闭csrf,或者http.csrf().ignoringAntMatchers("/eureka/**");super.configure(http); */
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic(); //开启认证
    }
}
