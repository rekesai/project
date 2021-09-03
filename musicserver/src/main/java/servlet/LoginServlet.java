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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        // 作为请求头告诉服务端消息主体是序列化的JSON字符串
        resp.setContentType("application/json;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username:"+username);
        System.out.println("password:"+password);

        Map<String, Object> returnMap = new HashMap<>();
        UserDao userDao = new UserDao();
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        User user = userDao.login(loginUser);
        if (user == null) {
            // 登录失败
            returnMap.put("msg", false);
        }
        else {
            // 登录成功
            req.getSession().setAttribute("user", user);    // 绑定数据
            returnMap.put("msg", true);
        }

        ObjectMapper mapper = new ObjectMapper();
        // 利用 Jackson 将map转化成 json 对象
        // write 将转换后的 json字符串 保存到字符输出流中，最后给客户端
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}
