package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/findMusicServlet")
public class FindMusicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String musicName = req.getParameter("musicName");
        MusicDao musicDao = new MusicDao();
        List<Music> musicList = null;
        if (musicName != null) {
            // 如果有输入字段来进行查询就按照关键字来查
            musicList = musicDao.findMusicByKey(musicName);
        }
        else {
            // 如果没有输入字段查询就展示所有
            musicList = musicDao.findMusic();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), musicList);
    }
}
