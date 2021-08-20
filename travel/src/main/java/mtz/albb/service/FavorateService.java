package mtz.albb.service;

import mtz.albb.domain.User;

public interface FavorateService {

    boolean isFavorate(String rid, int uid);
    //添加收藏
    void add(String rid, int uid);
}
