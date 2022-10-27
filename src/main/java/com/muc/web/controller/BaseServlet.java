package com.muc.web.controller;


import com.alibaba.fastjson.JSON;
import com.muc.service.CustomerService;
import com.muc.service.UserService;
import com.muc.service.impl.CustomerServiceImpl;
import com.muc.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {


    protected UserService userService;
    protected CustomerService customerService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        customerService = new CustomerServiceImpl();
    }


    protected <T> T getData(HttpServletRequest request, Class<T> clazz) throws IOException {
        //1.收集数据（request数据），先转换成String 类型
        String json = JSON.parseObject(request.getInputStream(), String.class);
        System.out.println("前端的json:"+json);
        //2.组织成一个实体类（User)
        return JSON.parseObject(json, clazz);
    }


    protected void returnData(HttpServletResponse response,Result result) throws IOException {
        //5.返回结果(json数据解析）
        response.setContentType("application/json;charset=utf-8");
        JSON.writeJSONString(response.getOutputStream(),result);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        int lastIndex = url.lastIndexOf('/');
        String methodName = url.substring(lastIndex + 1,url.length());
        //获取到了方法名叫做url的方法，然后执行，传递参数即可
        Class clazz = this.getClass();
        try {
            Method method = clazz.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            Result ret = (Result) method.invoke(this, request, response);
            returnData(response,ret);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
