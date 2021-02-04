package com.atguigu.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {


    /**
     * 这是 copyParamToBean version 1.0
     * @param req
     * @param bean
     * 但是这个写法并不好
     */
    public static void copyParamToBean(HttpServletRequest req, Object bean){
        try {
            System.out.println("注入前:  " + bean);
            BeanUtils.populate(bean,req.getParameterMap());
            System.out.println("注入后:  " + bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这是 version 2.0
     * 将 Map中的值, 一次性注入到 bean对象中
     * @param value
     * @param bean
     * @param <T>
     * @return
     *
     * 为什么要将 req 参数 改成 Map value?
     *      因为将Map注入Bean对象是一个很常见的操作.
     *      一个java程序中包括:
     *      dao层
     *      service层
     *      web层
     *      如果说我们的参数是 HttpServletRequest, 那这个方法就只能在 web 层中调用了(耦合度高),
     *      但是如果参数是 Map, 则这个方法在dao, service, web层中都可以使用
     *      这样代码的适用范围更广, 耦合度更低
     *
     */
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            System.out.println("注入前:  " + bean);
            BeanUtils.populate(bean,value);
            System.out.println("注入后:  " + bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 这是一个将String 转为 int 的方法, 如果转换失败则返回默认值
     * @param stringInt  数字字符串
     * @param defaultValue  默认值
     * @return
     */
    public static int parseInt(String stringInt, int defaultValue){

        try {
            int i = Integer.parseInt(stringInt);
            return i;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }
}
