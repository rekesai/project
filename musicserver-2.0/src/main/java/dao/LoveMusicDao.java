package dao;

import entity.Music;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoveMusicDao {

    // 加入喜欢列表
    public boolean insertLoveMusic(int userId, int musicId) {
        Connection connection = DBUtil.getConnection();
        String sql = "insert into lovemusic values(null, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, musicId);
            int ret = statement.executeUpdate();
            if (ret == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return false;
    }

    // 展示当前用户的喜欢列表
    public List<Music> findLoveMusic(int userId) {
        List<Music> loveMusicList = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        String sql = "select m.id, title, singer, url, m.userId, postTime " +
                "from music m, lovemusic lm where m.id = lm.musicId and lm.userId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                music.setPostTime(resultSet.getString("postTime"));
                loveMusicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return loveMusicList;
    }


    // 预防重复喜欢 - 添加喜欢时先查询是否已经添加过
    public boolean findLoveMusicByMusicIdAndUserId(int userId, int musicId) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from lovemusic where userId = ? and musicId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, musicId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return false;
    }

    // 通过用户id和关键词查找喜欢列表中的音乐
    public List<Music> findLoveMusicByKeyAndUserId(String str, int userId) {
        List<Music> musicList = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        String sql = "select m.id, title, singer, url, m.userId, postTime " +
                "from music m, lovemusic lm where m.id = lm.musicId and title like ? and lm.userId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            str = "%"+str+"%";
            statement.setString(1, str);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                music.setPostTime(resultSet.getString("postTime"));
                musicList.add(music);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return musicList;
    }

    // 移除喜欢列表的歌曲
    public int removeLoveMusic(int userId, int musicId) {
        Connection connection = DBUtil.getConnection();
        String sql = "delete from lovemusic where userId = ? and musicId = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, musicId);
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

    // 通过音乐id来查找是否有人喜欢这首歌
    public boolean findLoveMusicById(int musicId) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from lovemusic where musicId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, musicId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return false;
    }

    // 删除喜欢表里的这首歌
    public int deleteLoveMusicById(int musicId) {
        Connection connection = DBUtil.getConnection();
        String sql = "delete from lovemusic where musicId = ?";
        PreparedStatement statement = null;
        int ret = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, musicId);
            ret = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return ret;
    }


    public static void main(String[] args) {
        LoveMusicDao loveMusicDao = new LoveMusicDao();
//        loveMusicDao.insertLoveMusic(1, 1);
//        MusicDao musicDao = new MusicDao();
//        int id = musicDao.findMusicById(2).getId();
//        loveMusicDao.insertLoveMusic(1, id);
//        List<Music> list = new ArrayList<>();
//        list = loveMusicDao.findLoveMusicByKeyAndUserId("北", 1);
//        for (Music music : list) {
//            System.out.println(music);
//        }
        int ret = loveMusicDao.removeLoveMusic(1,3);
        System.out.println(ret);
    }
}
