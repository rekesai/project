package util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/musicserver?characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    volatile private static DataSource dataSource = null;

    public static DataSource getDataSource() {
        // 看一下DataSource是否已经存在一个实例
        // 如果不存在就创建一个新的，如果已经存在就直接返回存在的
        if (dataSource == null) {
            synchronized(DBUtil.class) {
                if (dataSource == null) {
                    MysqlDataSource mysqlDataSource = new MysqlDataSource();
                    mysqlDataSource.setUrl(URL);
                    mysqlDataSource.setUser(USERNAME);
                    mysqlDataSource.setPassword(PASSWORD);
                    dataSource = mysqlDataSource;
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            Connection connection = getDataSource().getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接失败");
        }
    }

    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}