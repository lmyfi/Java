package com.imooc.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.query.ProductListQuery;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import com.imooc.mall.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;

/**
 * 用户接口实现类，具体服务实现类
 * UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired //持有Mybatis映射类，可以对数据库进行操作
    UserMapper userMapper;

    @Autowired
    CategoryService categoryService;

    /**
     * 根据给定的用户id查询，用户信息
     * @return
     */
    @Override
    public User getUser() {
        User user = userMapper.selectByPrimaryKey(1);
        return user;
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param password 用户明码
     * @throws ImoocMallException
     */
    @Override
    public void register(String userName, String password) throws ImoocMallException, NoSuchAlgorithmException {
        //查询用户是否存在,不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null ){
            //查询到用户，抛出异常，自定义异常类ImoocMallException
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        //开始执行，用户数据写入
        User user = new User();//新建用户示例
        user.setUsername(userName);
        //密码使用md5工具类进行加密
        user.setPassword(MD5Utils.getMD5str(password));
        //该方法返回，修改条数
        int count = userMapper.insertSelective(user);//只写入部分注册用户名和密码信息，就调用该方法
        if (count == 0){
            //没有修改，表明修改失败，抛出异常
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 一般用户登陆登陆服务
     * @param userName
     * @param password
     * @return
     * @throws ImoocMallException
     */
    @Override
    public User login( String userName,  String password) throws ImoocMallException {
        String md5Password = null;//用于获取md5工具加密后的密码
        try {
            md5Password = MD5Utils.getMD5str(password);//获取加密后密码
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null){
            //如果登录用户为空，抛出错误信息
            throw  new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新用户个性签名服务
     * @param user
     * @throws ImoocMallException
     */
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        //更新用户个性签名
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1){
            //正常情况下应该只更新一个用户的个性签名，因为一般用户只能在自己的账号内作修改
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 检查用户角色
     * 1-普通用户
     * 2-管理员
     * @param user
     * @return
     */
    @Override
    public boolean checkAdminRole(User user){
        //角色，1-普通用户，2-管理员
        return user.getRole().equals(2);
    }

}
