package com.muc.service;

import com.github.pagehelper.PageInfo;
import com.muc.domain.User;

import java.util.List;

public interface UserService {

    /**
     * 注册新用户
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 根据email和密码登录
     * @param email
     * @param password
     * @return
     */
    User login(String email, String password);

    /**
     * 根据登录人id获取对应的昵称，从redis中获取
     * @param id
     * @return
     */
    String getLoginInfo(String id);

    boolean logout(String id);
    
    /**
     * 添加
     * @param user
     * @return
     */
    void save(User user);

    /**
     * 删除
     * @param user
     * @return
     */
    void delete(User user);

    /**
     * 修改
     * @param user
     * @return
     */
    void update(User user);

    /**
     * 查询多个
     * @param  id 查询的条件（id)
     * @return 查询的结果 ，单个对象
     */
    User findById(String id);

    /**
     * 查询全部的数据
     * @return  全部数据的列表对象
     */
    List<User> findAll();

    /**
     * 分页查询数据
     * @param page 页码
     * @param size 每页显示的数据总量
     * @return
     */
    PageInfo findAll(int page, int size);


}
