package com.it.ymk.bubble.core.session;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.*;

/**
 * @author yanmingkun.
 * @version 1.0
 * @since 2017/5/3 下午4:56
 */
public class SessionListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public SessionListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
        */
    }

    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
        */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("SessionListener:保存session【" + se.getSession().getId() + "】到context中。");
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();

        // 取得登录的用户名
        if (session.getAttribute("userToken") == null) {
            System.out.println("找不到session。");
        }
        String username = (String) session.getAttribute("userToken");
        // 从在线列表中删除用户名
        List onlineUserList = (List) application.getAttribute("onlineUserList");
        onlineUserList.remove(username);
        System.out.println(username + "超时退出。");
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute 
         is added to a session.
        */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute
         is removed from a session.
        */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is invoked when an attibute
         is replaced in a session.
        */
    }
}
