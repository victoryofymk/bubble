package com.it.ymk.bubble.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.it.ymk.bubble.Application;

/**
 * springboot controller测试
 *
 * @author yanmingkun
 * @date 2018-09-13 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc //开启MockMvc
public class SpringbootHelloworldApplicationTests {

    //注入MockMvc
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloController() throws Exception {
        mockMvc.perform(get("/"))//请求方式+地址
            .andDo(print()) //打印效果
            .andExpect(status().isOk()) //预期状态
            .andExpect(content().string(containsString("Hello Spring Boot World!")));
    }
}
