package com.it.ymk.bubble.common.template.util;

import com.it.ymk.bubble.common.template.Params;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *  jdbc工具类
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-07-21, $id:JdbcUtil.java, Exp $
 */
public class JdbcUtil {
    public static Connection connect(String driver, String url, String username,
                                     String password) {
        Connection connection = null;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ResultSet execStatement(String driver, String url, String username,
                                          String password, String sql) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = connect(driver, url, username, password);
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * template 模版解析
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static Map<String, Map<String, Params>> buildFreemark(String driver, String url, String username,
                                                                 String password) {
        String sql = "select table_name,column_name,column_comment, column_type,column_key,column_default from INFORMATION_SCHEMA.Columns where table_name='USER' and table_schema='skyrim'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = connect(driver, url, username, password);
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Map<String, Map<String, Params>> map = new HashMap<>();
            Map<String, Params> mapCommon = new HashMap<>();
            int i = 0;
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                String className = tableName.replaceAll("m_", "");
                String columnName = rs.getString("column_name");
                String type = rs.getString("column_type");
                if ("(varchar(255)".equals(type)) {
                    type = "String";
                } else if ("int(11)".equals(type)) {
                    type = "int";
                }
                // 使首字母大写
                String classNameUpCase = FreemarkUtil.toUpString(className);
                String columnNameUpCase = FreemarkUtil.toUpString(columnName);
                //这里只是使用一个实体类接收ResultSet的结果
                //这里只是使用一个实体类接收ResultSet的结果
                Params p = new Params.Build()
                        .column(columnName)
                        .comment(rs.getString("column_comment"))
                        .defaultName(rs.getString("column_default"))
                        .type(type)
                        .key(rs.getString("column_key")).tableName(tableName)
                        .className(className).classNameUpCase(classNameUpCase)
                        .columnNameUpCase(columnNameUpCase)
                        .buildParams();
                //将每一个组装好的实体类存入MAP，指定key
                mapCommon.put("Params" + i, p);
                i++;
            }
            //全部的MAP
            map.put("Paramss", mapCommon);
            System.out.println(map);

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}