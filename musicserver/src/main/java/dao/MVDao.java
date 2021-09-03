package dao;

import entity.MV;
import entity.Music;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MVDao {

    // 上传MV
    public int insert(MV mv) {
        // 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "insert into mv values(null, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        // 预编译sql
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, mv.getTitle());
            statement.setString(2, mv.getSinger());
            statement.setString(3, mv.getUrl());
            statement.setInt(4, mv.getUserId());
            statement.setString(5, mv.getPostTime());
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

    // 查询所有MV
    public List<MV> findMV() {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from mv";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<MV> mvList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setTitle(resultSet.getString("title"));
                mv.setSinger(resultSet.getString("singer"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                mv.setPostTime(resultSet.getString("postTime"));
                mvList.add(mv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return mvList;
    }

    // 根据id查询MV
    public MV findMVById(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from mv where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setTitle(resultSet.getString("title"));
                mv.setSinger(resultSet.getString("singer"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                mv.setPostTime(resultSet.getString("postTime"));
                return mv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return null;
    }

    // 根据关键字查询MV
    public List<MV> findMVByKey(String str) {
        List<MV> mvList = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
//        String sql = "select * from music where title like '%" + str + "%'";  // 容易发生sql注入攻击
        String sql = "select * from mv where title like ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            str = "%" + str + "%";
            statement.setString(1, str);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MV mv = new MV();
                mv.setId(resultSet.getInt("id"));
                mv.setTitle(resultSet.getString("title"));
                mv.setSinger(resultSet.getString("singer"));
                mv.setUrl(resultSet.getString("url"));
                mv.setUserId(resultSet.getInt("userId"));
                mv.setPostTime(resultSet.getString("postTime"));
                mvList.add(mv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return mvList;
    }

    public int deleteMVById(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "delete from mv where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int ret = statement.executeUpdate();
            if (ret == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return 0;
    }
}
