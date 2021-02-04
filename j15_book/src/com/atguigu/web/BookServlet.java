package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BookServlet extends BaseServlet {

    BookService bookService = new BookServiceImpl();

    /**
     * 负责图书信息分页处理的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 获取请求的参数:  pageNo 当前页码,  pageSize 每页显示多少行数据
        // 注意:  当用户首次进入图书管理页面, 肯定是先定位到首页, pageNo参数肯定没有传入, 所以默认值改为1
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);

        // 注意:  如果用户没有设置 pageSize, 我们就使用 Page中默认的PAGE_SIZE常量(默认值)
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        // 2 调用 bookService.page(pageNo, pageSize) 根据当前页码 和 每页行数量
        // 返回一个page对象(page就包含了当前页应该显示的数据)
        Page<Book> page = bookService.page(pageNo, pageSize);

        // 3 保存page对象到request域中
        req.setAttribute("page",page);

        // 4 请求转发到 pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1 使用WebUtils中的方法实现将req中的参数一次注入javaBean对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());

        // 2 调用 bookService中的 add方法, 将book保存到数据库
        int i = bookService.addBook(book);

        // 4 跳转到图书管理页面(需要重新从数据库读取数据, 所以先请求转发给 list方法, list方法再转发到manager.jsp)
        //req.getRequestDispatcher("/manager/BookServlet?action=list").forward(req,resp);

        resp.sendRedirect(req.getContextPath()+"/manager/BookServlet?action=list");
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = WebUtils.parseInt(req.getParameter("id"), 0);

        bookService.deleteBook(id);

        //重定向到 list
        resp.sendRedirect(req.getContextPath()+"/manager/BookServlet?action=list");

    }



    /**
     * list 用于展示图书管理系统中的图书信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1 通过bookService查询全部图书
        List<Book> books = bookService.queryBooks();

        // 2 将books保存到req的域数据中
        req.setAttribute("books",books);

        // 3 请求转发到 book_manager.jsp(因为客户端只发出一次请求, 所以 request域数据可以共享)
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }

    /**
     * 这个方法是针对 点击修改图书信息, 跳转到 book_edit.jsp 前, 获取所点击的图书的 id,
     * 在数据库中找到这本图书的信息, 保存到 request 域中, 在 book_edit.jsp中填写对应的 name, author....
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtils.parseInt(req.getParameter("id"),0);

        Book book = bookService.queryBookById(id);

        req.setAttribute("book",book);

        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = WebUtils.copyParamToBean(req.getParameterMap(), new Book());

//        Book oldBook = (Book) req.getAttribute("book");
//        book.setId(oldBook.getId());

        String stringId = req.getParameter("id");
        int id = WebUtils.parseInt(stringId, 0);
        book.setId(id);

        System.out.println(book);

        // 2 调用bookService中的update方法, 将修改后的图书信息保存到数据库
        bookService.updateBook(book);

        // 3 重定向到图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/BookServlet?action=list");
    }


}
