package com.atguigu.test;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BookDaoTest {

    private BookDao dao = new BookDaoImpl();

    @Test
    public void addBook() {

        int i = dao.addBook(new Book(null, "少年阿宾", new BigDecimal(34.4), "阿宾", 54, 231, null));

        if (i != -1) {
            System.out.println("图书已上架");
        } else {
            System.out.println("上架失败");
        }
    }

    @Test
    public void deleteBookById() {
        int i = dao.deleteBookById(21);

        if (i != -1) {
            System.out.println("图书已删除");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void updateBook() {
        int i = dao.updateBook(new Book(20, "人月神话", new BigDecimal(29), "华哥", 290, 892, null));

        if (i != -1) {
            System.out.println("图书信息已修改");
        } else {
            System.out.println("修改失败");
        }
    }

    @Test
    public void queryBookById() {
        Book book = dao.queryBookById(1);

        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = dao.queryBooks();
        books.forEach(System.out::println);
    }
}