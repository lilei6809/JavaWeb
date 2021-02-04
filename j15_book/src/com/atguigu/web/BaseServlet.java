package com.atguigu.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BaseServlet是各个servlet功能模块的父类, 目的是继承实现代码复用, 所以使用abstract
 * 其他的功能servlet不再继承 HttpServlet, 继承BaseServlet就可以了
 */
public abstract class BaseServlet extends HttpServlet {

    /**
     * 因为后台管理模块中某功能模块需要在浏览器上直接点击实现, 而不是通过form提交参数
     * 直接点击链接是 get请求, 所以提供doGet()方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 因为doGet()中的代码与 doPost()都是一样的, 所以直接调用doPost()实现就行了
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 我从页面上获取的value乱码了, 所以在 req将编码改为UTF-8
         */
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            Method actionMethod = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            actionMethod.invoke(this,req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
