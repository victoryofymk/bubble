package com.it.ymk.bubble.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.it.ymk.bubble.web.service.ws.WebserviceImpl;

/**
 * @author yanmingkun
 * @date 2018-09-12 11:01
 */
@Configuration
public class CxfConfig {
    /**
     * webservice
     *
     * @return
     */
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 注入webService
     *
     * @return
     */
    @Bean
    @Qualifier("WebserviceImpl")
    public Endpoint endpoint(WebserviceImpl WebserviceImpl) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), WebserviceImpl);
        // 暴露webService api
        endpoint.publish("/ws");
        return endpoint;
    }

    /**
     *添加普通的controller处理,实现restful风格
     *
     * @return
     */
    /*@Bean
    public ServletRegistrationBean dispatcherRestServlet() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        //替换成自己想买的controller包路径
        context.scan("com.it.ymk.bubble.web.service.rest");
        DispatcherServlet disp = new DispatcherServlet(context);
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(disp);
        registrationBean.setLoadOnStartup(1);
        //映射路径自定义
        registrationBean.addUrlMappings("/rest/*");
        registrationBean.setName("rest");
        return registrationBean;
    }*/

    /**
     *cxf实现restful风格
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherCxfRestServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    }
}
