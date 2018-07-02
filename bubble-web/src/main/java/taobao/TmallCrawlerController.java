package taobao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ymk.bubble.dao.pojo.Shop;
import com.it.ymk.bubble.dao.pojo.User;

import net.sf.json.JSONArray;

/**
 * @Title TmallCrawler.java
 * @description TODO
 * @time 2016年9月22日 下午3:14:31
 * @author yanmingkun
 * @version 1.0
 **/
@Controller
@RequestMapping("/tmallCrawler")
public class TmallCrawlerController {
    @RequestMapping(value = "/crawler", method = RequestMethod.POST)
    @ResponseBody
    public List<Shop> hello(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user)
        throws Exception {
        String reslult = null;
        List<Shop> list = new ArrayList<Shop>();
        try {
            list = TmallCrawler.getAllFavourite(user.getName(), user.getPassword());
            // List<Shop> list =
            // TmallCrawler.getAllFavouriteByCookie(user.getName(),
            // user.getPassword(),
            // user.getCookie());
            reslult = JSONArray.fromObject(list).toString();
            Cookie[] cookies = request.getCookies();
            // for (Cookie cookie : cookies) {
            // System.out.println(cookie);
            // }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
        response.setContentType("text/html");
        // PrintWriter out = response.getWriter();
        // out.print("<html><body>");
        // out.print(reslult);
        // out.print("</body></html>");
        // out.close();
        System.out.println(reslult);
        return list;
    }
}
