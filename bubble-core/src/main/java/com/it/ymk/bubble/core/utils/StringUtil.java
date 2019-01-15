package com.it.ymk.bubble.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 判断字符串是否为null、“ ”、“null”
     * @param obj
     * @return
     */
    public static boolean isNull(String obj) {
        if (obj == null) {
            return true;
        }
        else if (obj.toString().trim().equals("")) {
            return true;
        }
        else if (obj.toString().trim().toLowerCase().equals("null")) {
            return true;
        }

        return false;
    }

    /**
     * 正则验证是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");
        Matcher match = pattern.matcher(str);

        return match.matches();
    }

    /**
     * 将一个长整数转换位字节数组(8个字节)，b[0]存储高位字符，大端
     *
     * @param l
     *            长整数
     * @return 代表长整数的字节数组
     */
    public static byte[] longToBytes(long l) {
        byte[] b = new byte[8];
        b[0] = (byte) (l >>> 56);
        b[1] = (byte) (l >>> 48);
        b[2] = (byte) (l >>> 40);
        b[3] = (byte) (l >>> 32);
        b[4] = (byte) (l >>> 24);
        b[5] = (byte) (l >>> 16);
        b[6] = (byte) (l >>> 8);
        b[7] = (byte) (l);
        return b;
    }
}