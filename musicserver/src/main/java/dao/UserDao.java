package dao;

import entity.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 登录：依据用户名查询，如果没有则返回null
     * 否则返回一个User对象
     */
    public User login(User loginUser) {
        // 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 预编译sql
            statement = connection.prepareStatement(sql);
            statement.setString(1, loginUser.getUsername());
            statement.setString(2, loginUser.getPassword());
            // 执行sql
            resultSet = statement.executeQuery();
            // 如果查到结果
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
            else {
                System.out.println("用户名或密码不正确！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 注册
    public int insertUser(User user) {
        // 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "insert into user values(null, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        // 预编译sql
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getGender());
            statement.setString(5, user.getEmail());
            int ret = statement.executeUpdate();
            if (ret == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(connection, statement, null);
        }
        return 0;
    }

    // 查看是否有已注册的用户
    public boolean isExist(String username) {
        // 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "select * from user where username = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 预编译sql
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            // 执行sql
            resultSet = statement.executeQuery();
            // 如果查到结果
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(connection, statement, resultSet);
        }
        return false;
    }

    // 根据用户名和邮箱来确定用户
    public boolean findUserByUsernameAndEmail(String username, String email) {
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "select * from user where username = ? and email = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 预编译sql
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, email);
            // 执行sql
            resultSet = statement.executeQuery();
            // 如果查到结果
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(connection, statement, resultSet);
        }
        return false;
    }

    // 修改密码
    public int resetPassword(String username, String newPassword) {
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "update user set password = ? where username = ?";
        PreparedStatement statement = null;
        try {
            // 预编译sql
            statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            // 执行sql
            int ret = statement.executeUpdate();
            if (ret == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            DBUtil.close(connection, statement, null);
        }
        return 0;
    }




    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();

        // 测试登录功能
//        user.setUsername("zxc1");
//        user.setPassword("zxc");
//        System.out.println(userDao.login(user));

        // 注册功能
        user.setUsername("zxc1");
        user.setPassword("zxc1");
        user.setAge(21);
        user.setGender("女");
        user.setEmail("zxc123@qq.com");
        userDao.insertUser(user);
    }
}
