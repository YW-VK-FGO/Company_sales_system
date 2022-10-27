package com.muc.dao;

import com.muc.domain.Customer;

import java.util.List;

public interface CustomerDao {

    int save(Customer customer);

    int delete(Customer customer);

    int update(Customer customer);

    Customer findById(String id);

    List<Customer> findAll();
}
