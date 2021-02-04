package com.atguigu.dao.impl;

import com.atguigu.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * BaseDao存在的目的:  让其他具体的Dao操作类继承, 复用其代码, 但是它自身不需要实例化, 所以使用抽象类
 * 跟HttpServlet是一个道理:  HttpServlet也是抽象类, 目的也是代码复用
 * 我写一个servlet程序继承HttpServlet, 根据自己的需要去重写 doGet()方法即可, 而其他方法就就需使用HttpServlet的方法
 *
 * BaseDao类提供数据库的查询和修改的方法
 */
public abstract class BaseDao {
    //使用 DBUtils的QueryRunner操作数据库
    private QueryRunner runner = new QueryRunner();

    /**
     * update()方法用来执行 INSERT, UPDATE, DELETE语句
     * @return  如果返回 -1, 则表示执行失败. 其他int表示被影响的行数
     * 通过QueryRunner的update()实现
     */
    public int update(String sql, Object ...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return runner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /**
             * 一定要记得关闭连接
             */
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }

    /**
     * queryForOne()实现查询语句返回一个Bean对象的情况
     * @param tClass   返回的对象类型
     * @param sql
     * @param args
     * @param <T>  返回类型的泛型
     * @return
     * 通过 BeanHandler将结果集转为一个Bean对象.
     * 如何确定需要转为哪一个具体的Bean对象? 参数传入对应的Class
     */
    public <T> T queryForOne(Class<T> tClass, String sql, Object...args){
        Connection connection = JdbcUtils.getConnection();

        try {
            return runner.query(connection,sql, new BeanHandler<>(tClass), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }


    /**
     * queryForList() 实现一条SQL查询返回的多个Bean对象, 封装为List
     * @param tClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(Class<T> tClass, String sql, Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return runner.query(connection,sql, new BeanListHandler<>(tClass), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    /**
     * queryForSingleValue()实现查询返回单个值的情况, 比如COUNT(*), MAX(birth)..., 而不是返回Bean对象
     * @param sql
     * @param args
     * @return
     * 使用 ScalarHandler实现将结果集转为单个值
     */
    public Object queryForSingleValue(String sql, Object...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return runner.query(connection,sql, new ScalarHandler(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }
}
