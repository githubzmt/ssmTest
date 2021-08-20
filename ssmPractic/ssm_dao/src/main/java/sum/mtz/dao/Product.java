package sum.mtz.dao;

import java.util.List;

/**
 * @User Administrator
 * @Author ZMT
 * @title: Product
 * @create 2021-08-20 21:06
 */
public interface Product {
    //查询所有的产品
    List<Product> findAll();
    //通过Id查询产品
    Product findById(Integer id);
}
