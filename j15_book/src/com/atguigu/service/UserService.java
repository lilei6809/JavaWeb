package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * service表示业务层
 * 一个业务一个方法:  比如说登录是一个业务, 注册是一个业务, 检查用户名是否存在也是一个业务
 */
public interface UserService {

    /**
     * 注册用户
     * @param user  User对象
     * @return int  如果返回-1, 表示注册失败
     */
    public int registerUser(User user);

    /**
     * 用户登录
     * @param user
     * @return User  如果用户名密码不正确, 则返回null, 否则返回对应的User对象
     */
    public User login(User user);

    /**
     * 检查用户名是否已存在
     * @param username
     * @return  返回true表示用户名已存在, 返回false表示用户名可用
     */
    public boolean existUsername(String username);
}
