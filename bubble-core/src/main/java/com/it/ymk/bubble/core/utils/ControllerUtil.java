package com.it.ymk.bubble.core.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 * @author Yanmingkun
 * @version $v:, $time:2017-05-02, $id:ControllerUtil.java, Exp $
 */
public class ControllerUtil {
    public static void redirectURL(HttpServletResponse response, String s) {
        try {
            response.sendRedirect("redirect:" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}