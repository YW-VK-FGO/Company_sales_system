package com.muc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muc.dao.CustomerDao;
import com.muc.domain.Customer;
import com.muc.factory.MapperFactory;
import com.muc.service.CustomerService;
import com.muc.utils.MD5Util;
import com.muc.utils.TransactionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    @Override
    public void save(Customer customer) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession,CustomerDao.class);

            //后台id使用String 无法做到自动生成
            //故 使用UUID的生成策略来获取
            String id = UUID.randomUUID().toString();
            //注意可以对UUID自动生成的id进行处理，以符合需求(好看点)
            customer.setId(id);



            //3. 调用Dao层操作
            customerDao.save(customer);
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
    public void delete(Customer customer) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession,CustomerDao.class);


            //3. 调用Dao层操作
            customerDao.delete(customer);
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
    public void update(Customer customer) {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession,CustomerDao.class);

            //对于用户的更新，某些信息是不能更新的 比如邮箱号

            //方案一：读取现有库中的信息 ，覆盖现有的数据


            //方案二：修改update语句  对于某些字段设为不可更改


            //3. 调用Dao层操作
            customerDao.update(customer);
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
    public Customer findById(String id) {
        SqlSession sqlSession = null;
        try {
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
            //2. 获取Dao
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession, CustomerDao.class);

            //3. 调用Dao层操作
            return customerDao.findById(id);

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
    public List<Customer> findAll() {
        SqlSession sqlSession = null;
        try{
            //1. 获取SqlSession对象
            sqlSession = MapperFactory.getSqlSession();
//            System.out.println("findAll操作");
            //2. 获取Dao
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession,CustomerDao.class);
            //3. 调用Dao层操作
            return customerDao.findAll();


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
            CustomerDao customerDao = MapperFactory.getMapper(sqlSession,CustomerDao.class);

            PageHelper.startPage(page,size);
            //3. 调用Dao层操作
            List<Customer> all = customerDao.findAll();

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
