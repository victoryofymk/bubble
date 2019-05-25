package com.it.ymk.bubble.common.support.util;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 *
 * @author Yanmingkun
 * @version $v:, $time:2017-05-03, $id:CookieUtil.java, Exp $
 */
public class CookieUtil {
    private CookieUtil() {
    }

    public static String getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return null;
        }
        else {
            Cookie[] var6 = cookies;
            int var5 = cookies.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                Cookie c = var6[var4];
                if (name.equalsIgnoreCase(c.getName())) {
                    try {
                        return URLDecoder.decode(c.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException var8) {
                        return c.getValue();
                    }
                }
            }

            return null;
        }
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
        String cookieValue) throws ServletException {
        try {
            Cookie privacy = new Cookie(cookieName, URLEncoder.encode(cookieValue, "UTF-8"));
            privacy.setMaxAge(-1);
            privacy.setPath("/");
            response.addCookie(privacy);
        } catch (UnsupportedEncodingException var6) {
            //            LOG.error(var6.getMessage(), var6);
            throw new ServletException(var6);
        }
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
        String cookieValue, int maxAge) throws ServletException {
        try {
            Cookie privacy = new Cookie(cookieName, URLEncoder.encode(cookieValue, "UTF-8"));
            privacy.setMaxAge(maxAge);
            privacy.setPath("/");
            response.addCookie(privacy);
        } catch (UnsupportedEncodingException var7) {
            //            LOG.error(var7.getMessage(), var7);
            throw new ServletException(var7);
        }
    }

    public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName)
        throws ServletException {
        Cookie privacy = new Cookie(cookieName, (String) null);
        privacy.setMaxAge(0);
        privacy.setPath("/");
        response.addCookie(privacy);
    }
}