package com.it.ymk.bubble.web.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
@Deprecated
public class HelloWorld {
    @RequestMapping("/hello")
    public String hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter();
        response.sendRedirect("https://www.tmall.com/");
        Cookie[] cookies = request.getCookies();
        return "hello";
    }
}
