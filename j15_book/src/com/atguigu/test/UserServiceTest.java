package com.atguigu.test;

import com.atguigu.pojo.User;
import com.atguigu.service.impl.UserServiceImpl;
import org.junit.Test;

public class UserServiceTest {

    @Test
    public void registerUser() {
        UserServiceImpl userService = new UserServiceImpl();

        userService.registerUser(new User(null,"qiao","123123","123@123.cc"));

    }

    @Test
    public void login() {
        UserServiceImpl userService = new UserServiceImpl();
        User qiao = userService.login(new User(null, "qiao", "123123", null));
        System.out.println(qiao);
    }

    @Test
    public void existUsername() {
        UserServiceImpl userService = new UserServiceImpl();
        boolean b = userService.existUsername("123");
        System.out.println("用户名是否存在? " + b);
    }
}