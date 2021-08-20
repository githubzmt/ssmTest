package mtz.albb.web.servlet;

import mtz.albb.domain.PageBean;
import mtz.albb.domain.Route;
import mtz.albb.domain.User;
import mtz.albb.service.FavorateService;
import mtz.albb.service.RouteService;
import mtz.albb.service.impl.FavorateServiceImpl;
import mtz.albb.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/favorate/*")
public class FavorateServlet extends BaseServlet {
    FavorateService favorateService = new FavorateServiceImpl();
    public void isFavorate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路id
        String rid = request.getParameter("rid");
        //2.获取用户信息
        User user =(User)request.getSession().getAttribute("users");
        //如果用户不存在
        int uid;
        if (user==null){
            return;
        }else {
            //如果用户存在
            uid= user.getUid();
        }
        //3.调用FavorateService
        boolean flag = favorateService.isFavorate(rid,uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取当前登录用户
        User users = (User) request.getSession().getAttribute("users");
        int uid;
        if (users==null) {
            return;
        }else {
            uid = users.getUid();
        }
        //3.调用Favoriteservice
        favorateService.add(rid,uid);
    }
}
