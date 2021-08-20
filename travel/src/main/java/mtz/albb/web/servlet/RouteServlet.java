package mtz.albb.web.servlet;

import mtz.albb.domain.PageBean;
import mtz.albb.domain.Route;
import mtz.albb.service.FavorateService;
import mtz.albb.service.RouteService;
import mtz.albb.service.impl.FavorateServiceImpl;
import mtz.albb.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService routeService = new RouteServiceImpl();
    //FavorateService favorateService =new FavorateServiceImpl();
    public void pageRouteQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取页面传递的参数
        String cid_string = request.getParameter("cid");
        String currentPage_string = request.getParameter("currentPage");
        String pageSize_string = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        if (rname!=null&&rname.length()>0){
             rname = new String(rname.getBytes("ISO-8859-1"), "utf-8");
        }
        //2.规格初始化参数
        int cid = 0;
        if (cid_string != null && !"".equals(cid_string) && cid_string.length() > 0) {
            cid = Integer.parseInt(cid_string);
        } else {
            cid = 0;
        }
        int currentPage = 0;
        if (currentPage_string != null && !"".equals(currentPage_string)&& currentPage_string.length() > 0){
            currentPage = Integer.parseInt(currentPage_string);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSize_string != null && !"".equals(pageSize_string)) {
            pageSize = Integer.parseInt(pageSize_string);
        } else {
            pageSize = 5;
        }
        //3.调用service层
        PageBean pb = routeService.queryRoute(cid, currentPage, pageSize, rname);
        //4.将数据转换成json数据格式
        writeValue(pb, response);
    }
    //根据线路rid查询详细信息
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.调用service层
        Route route = routeService.findOne(rid);
        //System.out.println(route.getCount());
        //3.返回数据
        writeValue(route, response);
    }
}