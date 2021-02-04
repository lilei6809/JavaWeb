package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * 接口 UserDao 规范需要对 User表(用户登录) 实现的操作
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @return  如果返回null, 说明没有这个用户. 反之亦然
     */
    public User queryUserByUsername(String username);

    /**
     * 保存用户信息
     * @param user
     * @return  返回-1表示操作失败, 其他则表示SQL语句影响的行数
     */
    public int saveUser(User user);

    /**
     * 用户登录验证. 根据username, password查询
     * @return  如果返回null, 说明用户名或密码错误. 反之亦然
     */
    public User queryUserByUsernameAndPsw(String username, String password);

}
