package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void queryUserByUsername() {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.queryUserByUsername("admin");
        System.out.println(user);

        User user1 = userDao.queryUserByUsername("none");
        System.out.println(user1);

    }

    @Test
    public void saveUser() {
        UserDao userDao = new UserDaoImpl();
        User user = new User(null, "QIU", "123", "lilei@gmail.com");

        int i = userDao.saveUser(user);
        System.out.println(i);
    }

    @Test
    public void queryUserByUsernameAndPsw() {
    }
}