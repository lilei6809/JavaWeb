package com.atguigu.service;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;

import java.util.List;

public interface BookService {

    /**
     * 与Dao交互, 返回page对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<Book> page(int pageNo, int pageSize);

    public int addBook(Book book);

    public int deleteBook(Integer id);

    public int updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();
}
