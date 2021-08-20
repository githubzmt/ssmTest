package mtz.albb.dao.impl;

import mtz.albb.dao.CategoryDao;
import mtz.albb.domain.Category;
import mtz.albb.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAllCategory() {
        String sql = "select * from tab_category";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
