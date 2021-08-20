package mtz.albb.service.impl;

import mtz.albb.dao.CategoryDao;
import mtz.albb.dao.impl.CategoryDaoImpl;
import mtz.albb.domain.Category;
import mtz.albb.service.CategoryService;
import mtz.albb.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAllCategory() {
        Jedis jedis = JedisUtil.getJedis();
        //Set<String> category = jedis.zrange("categorys", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("categorys", 0, -1);

        List<Category> allCategory = null;
        try{
            if (category==null||category.size()==0){
                //查询数据库,将数据取出来
                allCategory = categoryDao.findAllCategory();
                //将数据库中的数据存到jedis中
                for (int i = 0; i < allCategory.size(); i++) {
                    jedis.zadd("categorys",allCategory.get(i).getCid(),allCategory.get(i).getCname());
                }
            }else {
                allCategory = new ArrayList<>();
                for (Tuple name : category) {
                    Category cg = new Category();
                    cg.setCname(name.getElement());
                    cg.setCid((int) name.getScore());
                    allCategory.add(cg);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return allCategory;
    }
}
