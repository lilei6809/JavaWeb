package com.atguigu.service.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;

public class UserServiceImpl implements UserService {

    /**
     * 因为3个业务都需要跟数据库交互, 所以创建一个userDao对象
     */
    private UserDao userDao = new UserDaoImpl();

    @Override
    public int registerUser(User user) {
        //if (user == null) return;
//
//        if (existUsername(user.getUsername())) return;

        //这个地方user是web层封装传入的, 不会为null
        //但是不需要判断用户名是否存在吗?

        return userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        //if (user == null) return null;

        return userDao.queryUserByUsernameAndPsw(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existUsername(String username) {

        return userDao.queryUserByUsername(username) != null;

        /**
         * == null, 说明没查到 -> 不存在 -> false
         */
    }
}
