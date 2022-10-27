package com.muc.dao;

import com.muc.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    int save(User user);

    int delete(User user);

    int update(User user);

    User findById(String id);

    List<User> findAll();


    User findByNameAndPwd(@Param("name") String name, @Param("password") String password);
}
