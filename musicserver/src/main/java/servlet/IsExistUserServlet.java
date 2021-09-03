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

@WebServlet("/isExistServlet")
public class IsExistUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        UserDao userDao = new UserDao();
        Map<String, Object> returnMap = new HashMap<>();

        String username = req.getParameter("username");
        if (userDao.isExist(username)) {
            returnMap.put("msg", false);
        }
        else {
            returnMap.put("msg", true);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}