package mtz.albb.service.impl;

import mtz.albb.dao.UserDao;
import mtz.albb.dao.impl.UserDaoImpl;
import mtz.albb.domain.User;
import mtz.albb.service.UserService;
import mtz.albb.utils.MailUtils;
import mtz.albb.utils.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public boolean register(User user) {


        //1.根据用户名查询用户是否存在
        User u = userDao.findByUsername(user.getUsername());
        //2.保存用户信息到数据库
        //2.1设置激活码唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        if (u!=null){
            return false;
        }
        userDao.saveUser(user);
        //System.out.println(user.getCode());
        //3.激活邮件
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活<黑马旅游网></a>";
        //System.out.println(content);
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    @Override
    public Boolean active(String code) {
        //1.根据激活码查询用户的信息
        User user = userDao.findByCode(code);
        //System.out.println(user);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            System.out.println(user.getStatus());
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username,password);
        return user;
    }
}
