package com.it.ymk.bubble.web.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yanmingkun
 * @date 2018-09-12 15:05
 */
@Controller
@RequestMapping("/ftl")
public class FreemarkerController {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerController.class);

    @RequestMapping("/hello")
    public String hello(Map<String, Object> map) {
        map.put("message", "hello,world");

        return "ftl/hello";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        logger.info("这是一个controller");
        model.addAttribute("title", "我是一个例子");
        // 注意，不要再最前面加上/，linux下面会出错
        return "index";
    }
}
