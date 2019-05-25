package com.it.ymk.bubble.common.support.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author yanmingkun
 * @date 2019-05-23 12:31
 */
public class ServletUtl {
    //private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtl.class);
    public static String fetchPostByTextPlain(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            char[] buf = new char[512];
            int len = 0;
            StringBuffer contentBuffer = new StringBuffer();
            while ((len = reader.read(buf)) != -1) {
                contentBuffer.append(buf, 0, len);
            }
            return contentBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            //log.error("[获取request中用POST方式“Content-type”是“text/plain”发送的json数据]异常:{}", e.getCause());
        }
        return "";
    }

    public static <T> T fetchPostByTextPlain(HttpServletRequest request, Class<T> clazz) {
        try {
            BufferedReader reader = request.getReader();
            char[] buf = new char[512];
            int len = 0;
            StringBuffer contentBuffer = new StringBuffer();
            while ((len = reader.read(buf)) != -1) {
                contentBuffer.append(buf, 0, len);
            }
            return JSON.parseObject(contentBuffer.toString(), clazz);

        } catch (IOException e) {
            e.printStackTrace();
            //log.error("[获取request中用POST方式“Content-type”是“text/plain”发送的json数据]异常:{}", e.getCause());
        }
        return null;
    }

    public static void redirectURL(HttpServletResponse response, String s) {
        try {
            response.sendRedirect("redirect:" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
