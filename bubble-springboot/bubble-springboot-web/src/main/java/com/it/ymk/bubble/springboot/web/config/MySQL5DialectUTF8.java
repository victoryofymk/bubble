package com.it.ymk.bubble.springboot.web.config;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * @author yanmingkun
 * @date 2018-09-21 13:23
 */
public class MySQL5DialectUTF8 extends MySQL57Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
