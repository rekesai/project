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

@WebServlet("/removeLoveMusicServlet")
public class RemoveLoveMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        Map<String, Object> returnMap = new HashMap<>();

        // 获取userId 和 musicId
        User user = (User) req.getSession().getAttribute("user");
        int userId = user.getId();
        String strId = req.getParameter("id");
        int musicId = Integer.parseInt(strId);

        LoveMusicDao loveMusicDao = new LoveMusicDao();
        int ret = loveMusicDao.removeLoveMusic(userId, musicId);
        if (ret == 1) {
            returnMap.put("msg", true);
        }
        else {
            returnMap.put("msg", false);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}
