package servlet;

import dao.MVDao;
import entity.MV;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/uploadMV")
public class UploadMVServlet extends HttpServlet {
    private static final String SAVEPATH = "F:\\Java\\javacode\\musicserver\\src\\main\\webapp\\mv\\";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            PrintWriter out = resp.getWriter();
            out.println("<script type='text/javascript'>alert('没有登录，不能上传音乐！');location='login.html';</script>");
            System.out.println("没有登录，不能上传音乐！");
        }
        else {
            // 获取文件名
            Part part = req.getPart("filename");

            // 可以直接获取到文件名字
            String fileName = part.getSubmittedFileName();
            System.out.println(fileName);
            part.write(SAVEPATH+fileName);

            // 获取歌手名字
            String singer = req.getParameter("singer");
            // 获取歌曲名
            String[] titles = fileName.split("\\.");
            String title = titles[0];
            // 获取url
            String url = "mv\\" + title;
            // 获取时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String postTime = simpleDateFormat.format(new Date());

            int userId = user.getId();

            MVDao mvDao = new MVDao();
            MV mv = new MV();
            mv.setTitle(title);
            mv.setSinger(singer);
            mv.setUrl(url);
            mv.setUserId(userId);
            mv.setPostTime(postTime);
            int ret = mvDao.insert(mv);
            if (ret == 1) {
                resp.sendRedirect("MV.html");
            }
            else {
                System.out.println("上传失败！");
                part.delete();
            }
        }
    }
}
