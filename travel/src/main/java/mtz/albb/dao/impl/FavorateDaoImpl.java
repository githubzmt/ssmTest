package mtz.albb.dao.impl;

import mtz.albb.dao.FavorateDao;
import mtz.albb.dao.UserDao;
import mtz.albb.domain.Favorite;
import mtz.albb.domain.User;
import mtz.albb.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavorateDaoImpl implements FavorateDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Favorite isFavorate(int rid, int uid) {
        Favorite favorite = null;
        try{
            String sql = "select * from tab_favorite where rid= ? and uid = ?";
            favorite = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){

        }

        return favorite;
    }

    @Override
    public int findFavorateCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void add(int rid, int uid) {
        jdbcTemplate.update("insert into tab_favorite values(?,?,?)",rid,new Date(),uid);
    }
}
