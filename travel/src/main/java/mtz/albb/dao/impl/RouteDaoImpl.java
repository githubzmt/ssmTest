package mtz.albb.dao.impl;

import mtz.albb.dao.RouteDao;
import mtz.albb.domain.Route;
import mtz.albb.domain.RouteImg;
import mtz.albb.domain.Seller;
import mtz.albb.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int totalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList<>();
        if (cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sql = sb.toString();

        int totalCount = jdbcTemplate.queryForObject(sql, Integer.class,params.toArray());
        return totalCount;
    }

    @Override
    public List<Route> queryRoute(int cid, int current, int pageSize, String rname) {
        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList<>();
        if (cid!=0){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        sql = sb.toString();
        //System.out.println(sql);
        params.add(current);
        params.add(pageSize);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<RouteImg> findByrid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }

    @Override
    public Seller findBySeller(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);

    }
}
