package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UserServlet extends BaseServlet {

    UserService userService = new UserServiceImpl();

    /**
     * 处理登录的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("执行login");
        // 1 获取提交的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 2 调用service对象
        // 验证用户名和密码是否正确
        // 如果正确: login()返回相应的 User对象, 如果错误 user == null
        User user = userService.login(new User(null, username, password, null));

        // 如果不正确, 返回登录页面
        if (user == null){
            /**
             * 账号密码错误, 跳转回登录页面:
             * 1 显示错误信息
             * 2 保留表单项的值在输入框中
             *
             * 把错误信息和表单项信息保存到request域中, 这样跳转回login.jsp, jsp就可以获取requset域中的信息了
             * jsp中的username 设置 value="${requestScope.username}" 注意使用EL表达式, 如果为null, 则不显示
             *
             * 密码不用回显
             */
            req.setAttribute("username",username);
            req.setAttribute("errorInfo","用户名或密码错误, 请重新输入");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }

        // 正确, 则跳转到登录成功页面
        req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
    }

    /**
     * 处理注册的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        System.out.println("执行regist");
//
//        // 1 获取请求的参数
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String email = req.getParameter("email");
//        String code = req.getParameter("code");
//
//        User user = new User();
//        try {
//            System.out.println("注入之前:  " + user);
//            BeanUtils.populate(user, req.getParameterMap());
//            System.out.println("注入之后:  " + user);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }


        /**
         * 使用WebUtils的copyParamToBean()实现javaBean对象的注入
         */
        Map value = req.getParameterMap();

        User user = WebUtils.copyParamToBean(value, new User());

        //因为code不属于user的属性, 所以需要单独获取
        String code = req.getParameter("code");


        // 2 检查验证码是否正确   ==== 写死了, 要求验证码为 abcde
        // 忽略大小写
        if (!"abcde".equalsIgnoreCase(code)){
            // 不正确:  跳转回注册页面
            // 使用请求转发 (注意是 / 开头:  /表示web目录)
            System.out.println("验证码["+code+"]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }

        //验证码正确:
        //3 检查用户名是否可用
        //注意: web层只能调用service层的方法, 但是不能直接与数据库交互
        //所以需要一个 UserService对象
        //如果该用户名已存在, 请求转发到注册页面

        if (userService.existUsername(user.getUsername())){

            /**
             * 回显失败原因, 并且保留表单数据
             */
            req.setAttribute("errorInfo","用户名已被占用");
            req.setAttribute("username",user.getUsername());
            req.setAttribute("email",user.getEmail());

            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
        // 否则用户名不存在 === 用户名可用
        // 调用service中的方法将用户信息保存到数据库

        userService.registerUser(user);

        // 然后跳转至 注册成功页面
        req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
    }

}
