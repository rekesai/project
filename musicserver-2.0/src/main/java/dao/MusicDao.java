package dao;

import entity.Music;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {

    // 上传音乐
    public int insert(Music music) {
        // 建立数据库连接
        Connection connection = DBUtil.getConnection();
        // 拼装sql
        String sql = "insert into music values(null, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        // 预编译sql
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, music.getTitle());
            statement.setString(2, music.getSinger());
            statement.setString(3, music.getUrl());
            statement.setInt(4, music.getUserId());
            statement.setString(5, music.getPostTime());
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

    // 查询所有歌单
    public List<Music> findMusic() {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from music";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Music> musicList = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
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
            DBUtil.close(connection, statement, null);
        }
        return musicList;
    }

    // 根据id查询音乐
    public Music findMusicById(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from music where id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                music.setPostTime(resultSet.getString("postTime"));
                return music;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return null;
    }

    // 根据歌手查询音乐
    public Music findMusicBySinger(String singer) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from music where singer = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, singer);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Music music = new Music();
                music.setId(resultSet.getInt("id"));
                music.setTitle(resultSet.getString("title"));
                music.setSinger(resultSet.getString("singer"));
                music.setUrl(resultSet.getString("url"));
                music.setUserId(resultSet.getInt("userId"));
                music.setPostTime(resultSet.getString("postTime"));
                return music;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
        return null;
    }

    // 根据关键字查询歌曲
    public List<Music> findMusicByKey(String str) {
        List<Music> musicList = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
//        String sql = "select * from music where title like '%" + str + "%'";  // 容易发生sql注入攻击
        String sql = "select * from music where title like ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            str = "%" + str + "%";
            statement.setString(1, str);
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

    // 删除歌曲 - 除了要删除全部列表里的歌，还要检查喜欢列表中有没有，如果有也得删除
    public int deleteMusicById(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "delete from music where id = ?";
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





    public static void main(String[] args) {
        MusicDao musicDao = new MusicDao();
        Music music = new Music();
        // 上传
//        music.setTitle("八成攻");
//        music.setSinger("星宫汐");
//        music.setUrl("music/八成攻");
//        music.setTime("2021-8-29");
//        music.setUserId(1);
//        musicDao.insert(music);
//        music.setTitle("一路向北");
//        music.setSinger("周杰伦");
//        music.setUrl("music/一路向北");
//        music.setTime("2021-8-29");
//        music.setUserId(1);
//        musicDao.insert(music);
        // 查询所有
//        List<Music> musicList = musicDao.findMusic();
//        for (Music m : musicList) {
//            System.out.println(m);
//        }
        // 通过id查询
//        music = musicDao.findMusicById(3);
//        System.out.println(music);

        // 通过关键字查询
        List<Music> musicList = musicDao.findMusicByKey("一");
        for (Music m : musicList) {
            System.out.println(m);
        }
    }
}
