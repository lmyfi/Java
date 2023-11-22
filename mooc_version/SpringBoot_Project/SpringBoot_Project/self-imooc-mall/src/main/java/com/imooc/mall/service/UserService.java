package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

import java.security.NoSuchAlgorithmException;

/**
 * 用户服务接口类
 */
public interface UserService {

    User getUser();

    void register(String userName, String password) throws ImoocMallException, NoSuchAlgorithmException;

    User login(String userName, String password) throws NoSuchAlgorithmException, ImoocMallException;

    void updateInformation(User user) throws ImoocMallException;

    boolean checkAdminRole(User user);
}
