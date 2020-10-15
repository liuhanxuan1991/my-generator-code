package com.lhx.study.mygeneratorcode.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class DBConnectionUtil {

    private static String mysqlUrl = "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding={3}&useSSL=true&allowMultiQueries=true";


    public static Connection getConnection(String ip, String port, String dbName, String encoding, String userName, String password) {
        try {
            return DriverManager.getConnection(CommonUtils.format(mysqlUrl, new String[]{ip, port, dbName, encoding}), userName, password);
        } catch (SQLException e) {
            log.error("连接数据库异常",e);
            return null;
        }
    }

    public void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var7) {
                var7.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }

    }
}
