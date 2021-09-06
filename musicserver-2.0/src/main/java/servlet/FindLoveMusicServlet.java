package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoveMusicDao;
import entity.Music;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/findLoveMusicServlet")
public class FindLoveMusicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        // 获取userId 和关键词字符串
        String musicName = req.getParameter("loveMusicName");
        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();

        // 定义一个表来接收一会查出来的音乐
        List<Music> musicList = new ArrayList<>();
        LoveMusicDao loveMusicDao = new LoveMusicDao();

        if (musicName != null) {
            // 如果输入了关键词，就按照关键词查询
            musicList = loveMusicDao.findLoveMusicByKeyAndUserId(musicName, userId);
        }
        else {
            // 如果没有输入字符就展示喜欢列表所有音乐
            musicList = loveMusicDao.findLoveMusic(userId);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), musicList);

    }
}
