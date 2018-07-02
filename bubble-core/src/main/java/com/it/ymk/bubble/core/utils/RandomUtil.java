package com.it.ymk.bubble.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  随机工具类
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-08-01, $id:RandomUtil.java, Exp $
 */
public class RandomUtil {
    private static int sequence = 0;
    private static int length   = 4;

    /**
     * 产生n位随机数
     *
     * @param n 产生n位随机数
     * @return long 数值
     */
    public static long generateRandomNumber(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long) (Math.random() * 9 * Math.pow(10, n - 1)) + (long) Math.pow(10, n - 1);
    }

    /**
     * YYYYMMDDHHMMSS+6位自增长码(20位)
     *
     * @return String 序号
     */
    public static synchronized String getLocalTrmSeqNum() {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss")
            .format(new Date());
        String s = Integer.toString(sequence);
        return datetime + addLeftZero(s, length);
    }

    /**
     * 左填0
     * @param s 字符串
     * @param length 长度
     * @return  String 生成的字符串
     */
    public static String addLeftZero(String s, int length) {
        // StringBuilder sb=new StringBuilder();
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                    "Numeric value is larger than intended length: " + s
                                                   + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);
    }
}