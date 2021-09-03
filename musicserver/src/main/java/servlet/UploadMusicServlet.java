package servlet;

import dao.MusicDao;
import entity.Music;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/upload")
@MultipartConfig
public class UploadMusicServlet extends HttpServlet {
    // private static final String SAVEPATH = "F:\\Java\\javacode\\musicserver\\src\\main\\webapp\\music\\";
    private static final String SAVEPATH = "/root/install/apache-tomcat-8.5.70/webapps/musicserver/music/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            PrintWriter out = resp.getWriter();
            out.println("<script type='text/javascript'>alert('没有登录，不能上传音乐！');location='login.jsp';</script>");
            System.out.println("没有登录，不能上传音乐！");
        }
        else {
            // 获取文件名

            Part part = req.getPart("filename");
//            String header = part.getHeader("Content-Disposition");
//            int start = header.lastIndexOf("=");
//            // 这里获取到的文件名是加密过的
//            String fileName = header.substring(start+1).replace("\"", "");
//            System.out.println(fileName);
//            // 解密文件名
//            int index = fileName.indexOf("%");
//            fileName = fileName.substring(index);
//            fileName = URLDecoder.decode(fileName, "utf-8");
//            System.out.println(fileName);
//            // 写入文件
//            part.write(SAVEPATH+fileName);

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
            String url = "music\\" + title;
            // 获取时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String postTime = simpleDateFormat.format(new Date());

            int userId = user.getId();

            MusicDao musicDao = new MusicDao();
            Music music = new Music();
            music.setTitle(title);
            music.setSinger(singer);
            music.setUrl(url);
            music.setUserId(userId);
            music.setPostTime(postTime);
            int ret = musicDao.insert(music);
            if (ret == 1) {
                resp.sendRedirect("list.html");
            }
            else {
                System.out.println("上传失败！");
                part.delete();
            }
        }
    }
}