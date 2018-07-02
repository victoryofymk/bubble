package com.it.ymk.bubble.dao.pojo;

import java.io.Serializable;

/**
 * @Title TaobaoUser.java
 * @description TODO
 * @time 2016年9月22日 下午2:31:16
 * @author yanmingkun
 * @version 1.0
 **/
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6929361864606904544L;
    /**
     * 用户名
     */
    private String            name;
    /**
     * 密码
     */
    private String            password;
    /**
     * cookie
     */
    private String            cookie;

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * @param cookie
     *            the cookie to set
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

}
