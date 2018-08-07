package com.it.ymk.bubble.component.codetools.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.it.ymk.bubble.component.codetools.entity.DataSourceConfigEntity;

public class DBConnect {
    public static Connection getConnection(DataSourceConfigEntity config) throws ClassNotFoundException, SQLException {

        Class.forName(config.getDriverClass());
        return DriverManager.getConnection(config.getJdbcUrl(),
            config.getUsername(), config.getPassword());
    }

    /**
     * 测试连接,返回错误信息,无返回内容说明连接成功
     * 
     * @param dataSourceConfigEntity
     * @return 返回错误信息,无返回内容说明连接成功
     */
    public static String testConnection(DataSourceConfigEntity dataSourceConfigEntity) {
        Connection con = null;
        String ret = null;
        try {
            con = DBConnect.getConnection(dataSourceConfigEntity);
            // 不为空说明连接成功
            if (con == null) {
                ret = dataSourceConfigEntity.getDbName() + "连接失败";
            }
        } catch (ClassNotFoundException e) {
            ret = dataSourceConfigEntity.getDbName() + "连接失败" + "<br>错误信息:"
                  + "找不到驱动" + dataSourceConfigEntity.getDriverClass();
        } catch (SQLException e) {
            ret = dataSourceConfigEntity.getDbName() + "连接失败" + "<br>错误信息:"
                  + e.getMessage();
        } finally {
            if (con != null) {
                try {
                    con.close(); // 关闭连接,该连接无实际用处
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }
}
