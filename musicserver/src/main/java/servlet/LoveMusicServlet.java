package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoveMusicDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/loveMusicServlet")
public class LoveMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        // 添加喜欢音乐需要userId 和 musicId
        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();
        String strId = req.getParameter("id");
        int musicId = Integer.parseInt(strId);

        Map<String, Object> returnMap = new HashMap<>();
        LoveMusicDao loveMusicDao = new LoveMusicDao();
        // 添加之前先查看一下是否添加过了，预防重复
        if (loveMusicDao.findLoveMusicByMusicIdAndUserId(userId, musicId)) {
            System.out.println("添加过了！");
            returnMap.put("msg", false);
        }
        else {
            if (loveMusicDao.insertLoveMusic(userId, musicId)) {
                returnMap.put("msg", true);
            }
            else {
                returnMap.put("msg", false);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}
