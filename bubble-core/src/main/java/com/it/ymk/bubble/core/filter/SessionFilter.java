package com.it.ymk.bubble.core.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  session失效自动跳转登录
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-05-03, $id:SessionFilter.java, Exp $
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String url = ((HttpServletRequest) request).getRequestURI();
        if (url.endsWith("login.ajax") || url.endsWith("login.html")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (session.getAttribute("userToken") == null) {
            String errors = "您还没有登录，或者session已过期。请先登陆!";
            request.setAttribute("Message", errors);
            //跳转至登录页面
            request.getRequestDispatcher("/login.html").forward(request, response);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}