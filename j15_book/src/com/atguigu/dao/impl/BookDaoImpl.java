package com.atguigu.dao.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.pojo.Book;

import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {

    @Override
    public Integer queryForTotalCount() {
        String sql = "SELECT COUNT(id) FROM t_book;";

        /**
         *  queryForSingleValue 返回的是 Object对象, 因为本例中是数值型对象
         *  先将其转为 Number 类型, Number类型是所有数值类的超类,
         *  Number类对象可以被转为任何数值型类
         */
        Number count = (Number) queryForSingleValue(sql);

        return count.intValue() ;
    }

    @Override
    public List<Book> queryForPageItems(int pageNo, int pageSize) {
        String sql = "SELECT id, name, price, author, sales, stock, img_path FROM t_book LIMIT ?, ?;";
        /**
         * 注意:  sql的 LIMIT中不能做运算, 我们先计算出一个结果填进去好了
         */
        int offset = (pageNo - 1) * pageSize;
        return queryForList(Book.class,sql,offset, pageSize);
    }

    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO t_book (name, price, author, sales, stock, img_path)" +
                        "VALUES (?,?,?,?,?,?);";

        return update(sql,book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public int deleteBookById(int id) {

        String sql = "DELETE FROM t_book WHERE id = ? ;";

        return update(sql,id);
    }

    @Override
    public int updateBook(Book book) {
        /**
         * 我修改图书信息可能只需要修改某一个字段(比如price), 这个地方怎么做判断呢?
         * 因为在web层封装Bean对象时, 已经封装了一个数据修改过的Bean对象, 其他信息依然保存着的
         */
        String sql = "UPDATE t_book SET name = ?, price = ?, author = ?, sales = ?, stock = ?, img_path = ?" +
                        "WHERE id = ? ;";

        return update(sql,book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getImgPath(), book.getId());
    }

    @Override
    public Book queryBookById(int id) {
        String sql = "SELECT id, name, price, author, sales, stock, img_path FROM t_book " +
                    "WHERE id = ?;";
        return queryForOne(Book.class,sql,id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql = "SELECT id, name, price, author, sales, stock, img_path FROM t_book;";
        return queryForList(Book.class,sql);
    }
}
