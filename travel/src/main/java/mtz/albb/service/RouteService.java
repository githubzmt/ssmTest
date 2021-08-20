package mtz.albb.service;

import mtz.albb.domain.PageBean;
import mtz.albb.domain.Route;

public interface RouteService {
    //分页查询route数据
    PageBean<Route> queryRoute(int cid, int currentPage, int pageSize, String rname);
    //旅游路线详情查询
    Route findOne(String rid);
}
