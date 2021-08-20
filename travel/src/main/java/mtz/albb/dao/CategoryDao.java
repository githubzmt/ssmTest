package mtz.albb.dao;

import mtz.albb.domain.Category;

import java.util.List;

public interface CategoryDao {
    //查询所有的分类
    List<Category> findAllCategory();
}
