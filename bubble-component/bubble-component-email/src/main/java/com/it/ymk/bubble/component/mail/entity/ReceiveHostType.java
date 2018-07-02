package com.it.ymk.bubble.component.mail.entity;

import java.util.Properties;

/**
 * 
 * @description 邮箱Host和协议
 * @author yanmingkun
 * @date 创建时间：2016年11月2日 上午10:10:15
 * @version 1.0
 */
public enum ReceiveHostType {
                             /**
                              * 139邮箱收件配置
                              */
                             MOBILE {
                                 @Override
                                 public Properties getProperties() {
                                     Properties defaults = new Properties();
                                     defaults.put("mail.pop3.host", "pop.139.com");
                                     defaults.put("mail.imap.host", "imap.139.com");
                                     // 默认使用pop3收取邮件
                                     defaults.put("mail.store.protocol", "pop3");
                                     // 需要邮件服务器认证
                                     defaults.put("mail.pop3.auth", "true");
                                     return defaults;
                                 }

                             },
                             /**
                              * 网易邮箱配置
                              */
                             NETEASE {
                                 @Override
                                 public Properties getProperties() {
                                     Properties defaults = new Properties();
                                     defaults.put("mail.pop3.host", "pop.163.com");
                                     defaults.put("mail.imap.host", "imap.163.com");
                                     // 默认使用pop3收取邮件
                                     defaults.put("mail.store.protocol", "pop3");
                                     // 需要邮件服务器认证
                                     defaults.put("mail.pop3.auth", "true");
                                     return defaults;
                                 }

                             },
                             /**
                              * QQ邮箱配置
                              */
                             TENCENT {
                                 @Override
                                 public Properties getProperties() {
                                     Properties defaults = new Properties();
                                     // 默认使用pop3收取邮件
                                     defaults.put("mail.store.protocol", "pop3");
                                     defaults.put("mail.pop3.host", "pop.qq.com");
                                     defaults.put("mail.imap.host", "imap.qq.com");
                                     defaults.put("mail.pop3.port", "995");
                                     // 需要邮件服务器认证
                                     defaults.put("mail.pop3.auth", "true");
                                     return defaults;
                                 }
                             },
                             /**
                              * Gmail邮箱配置
                              */
                             GMAIL {
                                 @Override
                                 public Properties getProperties() {
                                     Properties defaults = new Properties();
                                     defaults.put("mail.pop3.host", "pop.gmail.com");
                                     defaults.put("mail.pop3.port", "995");
                                     defaults.put("mail.imap.host", "imap.gmail.com");
                                     defaults.put("mail.imap.port", "465");
                                     // 默认使用pop3收取邮件
                                     defaults.put("mail.store.protocol", "pop3");
                                     return defaults;
                                 }

                             };
    /**
     * 获取配置
     * @return Properties
     */
    public abstract Properties getProperties();
}
