package mtz.albb.dao;

import mtz.albb.domain.Route;
import mtz.albb.domain.RouteImg;
import mtz.albb.domain.Seller;

import java.util.List;

public interface RouteDao {
    //查询线路的总记录数
    int totalCount(int cid, String rname);
    //查询线路的每页的数据
    List<Route> queryRoute(int cid, int current, int pageSize, String rname);

    //通过rid查询线路
    Route findOne(int rid);
    //查询图片信息
    List<RouteImg> findByrid(int rid);
    //查询商家信息
    Seller findBySeller(int sid);
}
