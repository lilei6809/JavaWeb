package com.atguigu.test;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BookServiceTest {

    BookService bookService = new BookServiceImpl();


    @Test
    public void getPage(){
        Page page = bookService.page(1, 3);
        System.out.println(page);
    }

    @Test
    public void addBook() {
        int i = bookService.addBook(new Book(null, "1", new BigDecimal(23), "1", 23, 23, null));

        if (i != -1) {
            System.out.println("图书已上架");
        } else {
            System.out.println("上架失败");
        }
    }

    @Test
    public void deleteBook() {
        int i = bookService.deleteBook(22);

        if (i != -1) {
            System.out.println("图书已删除");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void updateBook() {
        int i = bookService.updateBook(new Book(22, "22221", new BigDecimal(23), "1", 23, 23, null));

        if (i != -1) {
            System.out.println("图书信息已修改");
        } else {
            System.out.println("修改失败");
        }
    }

    @Test
    public void queryBookById() {
        Book book = bookService.queryBookById(1);
        System.out.println(book);
    }

    @Test
    public void queryBooks() {
        List<Book> books = bookService.queryBooks();
        books.forEach(System.out::println);
    }
}