package com.it.ymk.bubble.core.utils;

/**
 *  字符串处理工具
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-08-01, $id:StringUtil.java, Exp $
 */
public class StringUtil {
    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return String 返回的字符串
     */
    public static String UpStr(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }
}