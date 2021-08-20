package mtz.albb.service.impl;

import mtz.albb.dao.FavorateDao;
import mtz.albb.dao.RouteDao;
import mtz.albb.dao.impl.FavorateDaoImpl;
import mtz.albb.dao.impl.RouteDaoImpl;
import mtz.albb.domain.PageBean;
import mtz.albb.domain.Route;
import mtz.albb.domain.RouteImg;
import mtz.albb.domain.Seller;
import mtz.albb.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    FavorateDao favorateDao = new FavorateDaoImpl();
    @Override
    public PageBean<Route> queryRoute(int cid, int currentPage, int pageSize, String rname) {
        //1.调用dao查询总记录数
        int totalCount = routeDao.totalCount(cid,rname);
        //2.计算总页数
        int totalPage = totalCount/pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        //3.查询每页的数据
        int current = (currentPage-1)*pageSize;
        List<Route> list = routeDao.queryRoute(cid,current,pageSize,rname);
        //4.封装数据
        PageBean<Route> routePageBean = new PageBean<>();
        routePageBean.setTotalCount(totalCount);
        routePageBean.setTotalPage(totalPage);
        routePageBean.setPageSize(pageSize);
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setList(list);
        return routePageBean;
    }

    @Override
    public Route findOne(String rid) {
        //1.查询route
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2.查询routeImg
        List<RouteImg> imgList = routeDao.findByrid(route.getRid());
        //3.查询routeSeller
        Seller seller = routeDao.findBySeller(route.getSid());
        route.setRouteImgList(imgList);
        route.setSeller(seller);
        //4.设置查询次数
        int count = favorateDao.findFavorateCount(route.getRid());
        route.setCount(count);
        return route;
    }
}
