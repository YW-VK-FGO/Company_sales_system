package com.muc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muc.dao.UserDao;
import com.muc.domain.User;
import com.muc.factory.MapperFactory;
import com.muc.service.UserService;
import com.muc.utils.MD5Util;
import com.muc.utils.TransactionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public User login(String name, String password) {

        SqlSession sqlSession = null;
        try{
            //1.获取SqlSession
            sqlSession = MapperFactory.getSqlSession();
            //2.获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);
            //暂时先不加密数据
//            password = MD5Util.md5(password);
            User user = userDao.findByNameAndPwd(name,password);
            System.out.println("name:"+name+"\tpassword:"+password);
            System.out.println("登录用户:"+user.toString());

//            //3.将登录人的信息保存到redis中
//            Jedis jedis = JedisUtils.getResource();
//            //使用登录人的id作为key，设定3600秒的过期时间，value值待定
//            jedis.setex(user.getId(),3600,user.getNickName());
//            jedis.close();

            return user;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
            //记录日志
        }finally {
            try {
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getLoginInfo(String id) {
        return null;
    }

    @Override
    public boolean logout(String id) {
        return false;
    }

    @Override
    public void save(User user) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession,UserDao.class);

            //后台id使用String 无法做到自动生成
            //故 使用UUID的生成策略来获取
            String id = UUID.randomUUID().toString();
            //注意可以对UUID自动生成的id进行处理，以符合需求(好看点)
            user.setId(id);

            //密码必须经过加密处理MD5加密
            user.setPassword(MD5Util.md5(user.getPassword()));

            //3. 调用Dao层操作
            userDao.save(user);
            //4. 提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行save操作出错！");
            //记录日志

        }finally {
            try{
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(User user) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession,UserDao.class);


            //3. 调用Dao层操作
            userDao.delete(user);
            //4. 提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行delete操作出错！");
            //记录日志

        }finally {
            try{
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(User user) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession,UserDao.class);

            //对于用户的更新，某些信息是不能更新的 比如邮箱号

            //方案一：读取现有库中的信息 ，覆盖现有的数据


            //方案二：修改update语句  对于某些字段设为不可更改


            //3. 调用Dao层操作
            userDao.update(user);
            //4. 提交事务
            TransactionUtil.commit(sqlSession);

        }catch (Exception e){
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行update操作出错！");
            //记录日志

        }finally {
            try{
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public User findById(String id) {
        SqlSession sqlSession = null;
        try {
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession, UserDao.class);

            //3. 调用Dao层操作
            return userDao.findById(id);

            //查询操作是没有事务的
/*
            //4. 提交事务
            TransactionUtil.commit(sqlSession);
*/

        } catch (Exception e) {
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行findById操作出错！");
            //记录日志

        } finally {
            try {
                TransactionUtil.close(sqlSession);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> findAll() {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession,UserDao.class);

            //3. 调用Dao层操作
            return userDao.findAll();


        }catch (Exception e){
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行findAll操作出错！");
            //记录日志

        }finally {
            try{
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageInfo findAll(int page, int size) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            UserDao userDao = MapperFactory.getMapper(sqlSession,UserDao.class);

            PageHelper.startPage(page,size);
            //3. 调用Dao层操作
            List<User> all = userDao.findAll();

            //分页操作
            PageInfo pageInfo = new PageInfo(all);

            return pageInfo;

        }catch (Exception e){
            //如果出问题了，就得回滚事务操作
            TransactionUtil.rollback(sqlSession);
            throw new RuntimeException("执行findAll操作出错！");
            //记录日志

        }finally {
            try{
                TransactionUtil.close(sqlSession);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
