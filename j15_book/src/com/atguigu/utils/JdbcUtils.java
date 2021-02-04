package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 自己写的, 跟老师的思路一致
 * 这是实现数据库连接池连接和关闭的工具类
 */
public class JdbcUtils {

    /**
     * 初始化时实现静态代码块
     * 数据库连接池通过静态代码块实现, 确保只有一个连接池
     */
    private static DruidDataSource databasePoll;

    static {
        InputStream resourceAsStream = null;
        try {
            Properties prop = new Properties();
            //TODO: 一定要注意!!!!这个地方通过反射获取流对象一定不能使用
            // ClassLoader.getSystemClassLoader...不然会出现空指针异常
            // 使用本class来获取ClassLoader.
            resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream("book.properties");
            prop.load(resourceAsStream);

            databasePoll = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resourceAsStream != null){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnection(){
        DruidPooledConnection connection = null;
        try {
            connection = databasePoll.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        // 注意!!! 一定要先判断 connection是否为null, 否则空指针异常
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
