package mtz.albb.dao;

import mtz.albb.domain.User;

public interface UserDao {
    //根据用户名查询用户是否存在
    User findByUsername(String username);
    //存储用户信息
    void saveUser(User user);
    //根据激活码查询用户信息
    User findByCode(String code);
    //修改指定用户激活状态
    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
