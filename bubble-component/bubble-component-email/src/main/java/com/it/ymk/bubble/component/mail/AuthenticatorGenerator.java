package com.it.ymk.bubble.component.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @author yanmingkun
 * @version 1.0
 * @since  创建时间：2016年11月1日 下午8:30:33
 */
public final class AuthenticatorGenerator {

    /**
     * 根据用户名和密码，生成Authenticator
     *
     * @param userName 用户名
     * @param password 密码
     * @return Authenticator 认证
     */
    public static Authenticator getAuthenticator(final String userName, final String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
    }

}