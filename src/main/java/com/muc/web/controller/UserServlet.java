package com.muc.web.controller;

import com.muc.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    public Result register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getData(request, User.class);
        boolean flag = userService.register(user);
        return new Result("注册成功！", null);
    }

    public Result login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getData(request,User.class);
        System.out.println("前端得到的user书局:"+user.toString());
        user = userService.login(user.getUserName(),user.getPassword());
        System.out.println("UserServlet");
        if(user != null){
            return new Result("登录成功！", user);
        }else{
            return new Result("用户名密码错误，请重试！", false, null, Code.LOGIN_FAIL);
        }
    }

    public Result checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getData(request,User.class);
        //根据获取到的id去redis中查找，是否存在
        String nickName = userService.getLoginInfo(user.getId());
        return new Result("", nickName);
    }

    public Result logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = getData(request,User.class);
        boolean flag = userService.logout(user.getId());
        if(flag){
            return new Result("退出成功!", flag);
        }else{
            return new Result("", false, flag, Code.LOGOUT_FAIL);
        }
    }

}



/*@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //1.收集数据（request数据）
    String json = JSON.parseObject(request.getInputStream(), String.class);
    //2.组织成一个实体类（User)
    User user = JSON.parseObject(json, User.class);
    //3.调用逻辑层API
    boolean flag = userService.register(user);
    //4.返回的数据封装成一个对象
    Result result = new Result("注册成功！", null);
    //5.返回结果(json数据解析）
    response.setContentType("application/json;charset=utf-8");
    JSON.writeJSONString(response.getOutputStream(),result);
}*/