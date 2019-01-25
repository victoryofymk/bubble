package com.it.ymk.bubble.web.domain;

import java.io.Serializable;

/**
 *
 *
 * @author Yanmingkun
 * @version $v:, $time:2017-05-02, $id:UserVO.java, Exp $
 */
public class UserVO implements Serializable {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}