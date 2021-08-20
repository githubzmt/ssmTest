package mtz.albb.dao;

import mtz.albb.domain.Favorite;
import mtz.albb.domain.User;

public interface FavorateDao {

    Favorite isFavorate(int parseInt, int uid);
    //查询收藏次数
    int findFavorateCount(int rid);
    //添加收藏
    void add(int parseInt, int uid);
}
