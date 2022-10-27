package com.muc.web.controller;

import com.muc.domain.Customer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/customer/*")
public class CustomerServlet extends BaseServlet{


    public Result getCustomer(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行加载客户信息");
        List<Customer> customerList = customerService.findAll();
        return new Result("加载客户数据成功!",customerList);

    }


}
