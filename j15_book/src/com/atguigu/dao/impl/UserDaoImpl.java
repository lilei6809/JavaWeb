package com.atguigu.dao.impl;

import com.atguigu.dao.UserDao;
import com.atguigu.pojo.User;


public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public User queryUserByUsername(String username) {

        String sql = "SELECT id,username,password,email FROM t_user WHERE username = ?";
        return queryForOne(User.class,sql,username);
    }

    @Override
    public int saveUser(User user) {

        String sql = "INSERT INTO t_user (username, password, email) VALUES (?, ?, ?)";
        return update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }

    @Override
    public User queryUserByUsernameAndPsw(String username, String password) {
        String sql = "SELECT id,username,password,email FROM t_user " +
                "WHERE username = ? AND password = ?";
        return queryForOne(User.class,sql,username, password);
    }
}
