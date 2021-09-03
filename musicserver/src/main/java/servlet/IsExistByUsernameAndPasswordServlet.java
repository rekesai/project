package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/isExistByUsernameAndPasswordServlet")
public class IsExistByUsernameAndPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        UserDao userDao = new UserDao();
        Map<String, Object> returnMap = new HashMap<>();

        String username = req.getParameter("username");
        String email = req.getParameter("email");

        // 确认身份
        if (userDao.findUserByUsernameAndEmail(username, email)) {
            returnMap.put("msg", true);
        }
        else {
            returnMap.put("msg", false);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}
