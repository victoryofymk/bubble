package com.it.ymk.bubble.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.ymk.bubble.dao.domain.UserVO;

/**
 *  登录验证
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-05-02, $id:LoginController.java, Exp $
 */
@Controller
public class LoginController {
    @RequestMapping("/home")
    public String home(ModelAndView view) {
        return "index";
    }

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView view) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/login.ajax")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                request.setAttribute("error", "用户名或密码不能为空");
                return "2";
            }

            HttpSession session = request.getSession();
            if (session.getAttribute("userToken") != null) {
                //            ControllerUtil.redirectURL(response, "/home");
                return "1";
            }

            //如果没有这个字段，说明不是提交表单，而是申请显示表单。
            /* String isLoginForm =request.getParameter("isLoginForm");
             if(!StringUtils.isEmpty(isLoginForm)){
            return "login";
             }*/

            //验证
            UserVO u = new UserVO();
            u.setUsername(username);
            u.setPassword(password);
            if (u != null) {
                session.setAttribute("userToken", u);
                request.getRequestDispatcher("/home");
                CookieUtil.setCookie(request, response, "AUTH_TICKET", session.getId());
                CookieUtil.setCookie(request, response, "username", username, 259200);
                CookieUtil.setCookie(request, response, "token", password, 259200);
                //            ControllerUtil.redirectURL(response, "/home");

                ServletContext application = session.getServletContext();
                Object o = application.getAttribute("onlineUserList");
                List onlineUserList;
                if (o == null) {
                    onlineUserList = new ArrayList();
                }
                else {
                    onlineUserList = (List) o;
                }

                onlineUserList.add(username);
                application.setAttribute("onlineUserList", onlineUserList);
                return "1";
            }

            session.setAttribute("loginId", UUID.randomUUID().toString());
            request.setAttribute("error", "用户名或密码错误");

            return "0";
        } catch (ServletException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping("/loginOut.ajax")
    @ResponseBody
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            CookieUtil.delCookie(request, response, "AUTH_TICKET");
            CookieUtil.delCookie(request, response, "username");
            CookieUtil.delCookie(request, response, "token");
            HttpSession session = request.getSession();
            session.invalidate();
            return "1";
            //            ControllerUtil.redirectURL(response, "/login");
        } catch (ServletException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}