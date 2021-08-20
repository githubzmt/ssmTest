package mtz.albb.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import mtz.albb.domain.ResultInfo;
import mtz.albb.domain.User;
import mtz.albb.service.UserService;
import mtz.albb.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private ResultInfo resultInfo = new ResultInfo();
    //注册功能
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.验证码
        //0.1获取验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了验证码只能一次使用
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误!");
            String msg = writeValueAsString(resultInfo,response);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(msg);
            return;
        }
        //1.获取数据
        Map<String, String[]> user_Map = request.getParameterMap();
        //2.封装数据
        User user = new User();
        try {
            BeanUtils.populate(user,user_Map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = userService.register(user);
        //4.根据service提示信息
        if (flag){
            //注册成功
            resultInfo.setFlag(true);
        }
        else {
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败!用户名重复");

        }
        //(1)将提示信息转为json
        String msg = writeValueAsString(resultInfo,response);
        //(2)设置响应头
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(msg);
    }
    //登录功能
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取验证码
        String check = request.getParameter("check");
        //2.获取session域中的随机验证码
        HttpSession session = request.getSession();
        String check_code = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //3.将错误信息存储到ResultInfo对象中,最后json序列化

        //4.封装数据
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.判断验证码是否正确
        if (check.equalsIgnoreCase(check_code)&&check!=null){
            //5.判断用户名和密码是否正确
            User u = userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
            // System.out.println(u);
            if (u==null){
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户名或密码错误");

            }
            if (u!=null&&"Y".equals(u.getStatus())){
                String name = u.getName();
                request.getSession().setAttribute("users",u);
                request.getSession().setAttribute("user",name);
                resultInfo.setFlag(true);
            }
            if(u!=null&&!"Y".equals(u.getStatus())){
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("请前往邮箱进行激活");
            }
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
        }
        response.setContentType("application/json;charset=utf-8");
        writeValue(resultInfo,response);
    }
    //查询用户信息
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        response.setContentType("application/json;charset=utf-8");
        writeValue(user,response);
    }
    //激活邮件
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //2.判断激活码是否为空
        if(code!=null){
            //3.调用service完成激活
            Boolean flag = userService.active(code);
            String msg = null;
            if (flag){
                //跳转到登陆页面
                msg = "激活成功!请<a href='login.html'>登录</a>";
            }else{
                //提示请联系管理员
                msg = "激活失败请联系管理员!!!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
    //退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁登录的用户对象
        request.getSession().invalidate();
        //2.跳转页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
