package mtz.albb.dao.impl;

import mtz.albb.dao.UserDao;
import mtz.albb.domain.User;
import mtz.albb.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public User findByUsername(String username) {
        User user = null;
        try{
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(),
                user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),
                user.getTelephone(),user.getEmail(),
                user.getStatus(),user.getCode());
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try{
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql  = "update tab_user set status='Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try{
            String sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
}
