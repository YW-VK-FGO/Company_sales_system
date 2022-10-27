package com.muc.service;

import com.github.pagehelper.PageInfo;
import com.muc.domain.Customer;

import java.util.List;

public interface CustomerService {
    /**
     * 添加
     * @param customer
     * @return
     */
    void save(Customer customer);

    /**
     * 删除
     * @param customer
     * @return
     */
    void delete(Customer customer);

    /**
     * 修改
     * @param customer
     * @return
     */
    void update(Customer customer);

    /**
     * 查询多个
     * @param  id 查询的条件（id)
     * @return 查询的结果 ，单个对象
     */
    Customer findById(String id);

    /**
     * 查询全部的数据
     * @return  全部数据的列表对象
     */
    List<Customer> findAll();

    /**
     * 分页查询数据
     * @param page 页码
     * @param size 每页显示的数据总量
     * @return
     */
    PageInfo findAll(int page, int size);


}
