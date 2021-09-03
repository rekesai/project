package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        UserDao userDao = new UserDao();
        User user = new User();
        Map<String, Object> returnMap = new HashMap<>();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String strAge = req.getParameter("age");
        int age = Integer.parseInt(strAge);
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");

        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        user.setGender(gender);
        user.setEmail(email);

        int ret = userDao.insertUser(user);
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
