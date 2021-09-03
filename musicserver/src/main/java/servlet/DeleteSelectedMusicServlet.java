package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoveMusicDao;
import dao.MusicDao;
import entity.Music;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deleteSelectedServlet")
public class DeleteSelectedMusicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String[] strId = req.getParameterValues("id[]");
        int sum = 0;
        Map<String, Object> returnMap = new HashMap<>();
        MusicDao musicDao = new MusicDao();
        for (int i = 0; i < strId.length; i++) {
            int id = Integer.parseInt(strId[i]);

            Music music = musicDao.findMusicById(id);
            // 如果没有这首歌就跳过
            if (music == null) continue;

            // 发现数据库中有这个音乐，开始删除
            int ret = musicDao.deleteMusicById(id);
            if (ret == 1) {

                // 删除喜欢表中的这个音乐
                LoveMusicDao loveMusicDao = new LoveMusicDao();
                // 如果有人喜欢
                if (loveMusicDao.findLoveMusicById(id)) {
                    loveMusicDao.deleteLoveMusicById(id);
                }

                // 删除服务器中的这个音乐文件
//                File file = new File("F:\\Java\\javacode\\musicserver\\src\\main\\webapp\\"
//                        +music.getUrl()+".mp3");

                File file = new File("/root/install/apache-tomcat-8.5.70/webapps/musicserver/"
                        +music.getUrl()+".mp3");
                if (file.delete()) {
                    sum += 1;
                }
                else {
                    returnMap.put("msg", false);
                }
            }
            else {
                returnMap.put("msg", false);
            }
        }
        if (sum == strId.length) {
            returnMap.put("msg", true);
        }
        else {
            returnMap.put("msg", false);
        }

        // 利用Jackson将map转换成json对象
        // write 将转换后的json字符串保存到字符输出流中，最后返还给客户端
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getWriter(), returnMap);
    }
}
