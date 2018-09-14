package com.it.ymk.bubble.core.utils;

import java.sql.SQLException;

/**
 * @author yanmingkun
 * @date 2018-09-14 10:08
 */
public class ErrorUtil {
    /**
     * 随机抛出异常.
     */
    public static void randomException() throws Exception {
        Exception[] exceptions = { //异常集合
                                   new NullPointerException(),
                                   new ArrayIndexOutOfBoundsException(),
                                   new NumberFormatException(),
                                   new SQLException() };
        //发生概率
        double probability = 0.75;
        if (Math.random() < probability) {
            //情况1：要么抛出异常
            throw exceptions[(int) (Math.random() * exceptions.length)];
        }
        else {
            //情况2：要么继续运行
        }

    }
}
