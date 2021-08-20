package mtz.albb.service;

import mtz.albb.domain.User;

public interface UserService {
    /*
    注册用户
     */
    boolean register(User user);
    //激活用户的状态
    Boolean active(String code);
    //通过用户名和密码查询用户是否存在
    User findByUsernameAndPassword(String username, String password);
}
