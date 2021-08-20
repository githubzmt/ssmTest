package mtz.albb.service.impl;

import mtz.albb.dao.FavorateDao;
import mtz.albb.dao.RouteDao;
import mtz.albb.dao.impl.FavorateDaoImpl;
import mtz.albb.dao.impl.RouteDaoImpl;
import mtz.albb.domain.*;
import mtz.albb.service.FavorateService;
import mtz.albb.service.RouteService;

import java.util.List;

public class FavorateServiceImpl implements FavorateService {
    FavorateDao favorateDao = new FavorateDaoImpl();
    @Override
    public boolean isFavorate(String rid, int uid) {
        Favorite favorite = favorateDao.isFavorate(Integer.parseInt(rid),uid);
        return favorite!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favorateDao.add(Integer.parseInt(rid),uid);
    }
}
