package com.atguigu.dao;

import com.atguigu.pojo.Book;

import java.util.List;

public interface BookDao {

    /**
     * 获取数据库总记录数
     * @return
     */
    public Integer queryForTotalCount();

    /**
     * 使用分页查询获取当前页上的数据
     * limit (pageNo - 1) * pageSize, pageSize
     * @return
     */
    public List<Book> queryForPageItems(int pageNo, int pageSize);

    public int addBook(Book book);

    public int deleteBookById(int id);

    public int updateBook(Book book);

    public Book queryBookById(int id);

    public List<Book> queryBooks();
}
