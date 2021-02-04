package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.utils.WebUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public Page<Book> page(int pageNo, int pageSize) {



        //计算总记录数
        //TODO:  queryForTotalCount()返回的是long类型, 但是老师设置的totalcount是int类型, 老师是怎么处理的
        // 我的这种处理办法可能会损失精度
        // 具体见queryForTotalCount()方法源码
        int totalCount = bookDao.queryForTotalCount();



        //获取当前页的Book数据
        List<Book> books = bookDao.queryForPageItems(pageNo, pageSize);

        // 计算总页码
        int pageTotal = 0;

        //int pageTotal = Math.ceil(toalCount/pageSize);
        //这个地方不能使用 ceil计算, 因为a, b都是int 如果 a= 1, b=10, a/b=0了
//        if (totalCount % pageSize == 0){
//            pageTotal =  (totalCount/pageSize);
//        } else {
//            pageTotal = (totalCount/pageSize + 1);
//        }

        pageTotal = (totalCount % pageSize == 0) ? totalCount/pageSize : (totalCount/pageSize + 1);


        /**
         * 数据边界的有效检查:(js中已经做过一次检查, 现在是为了处理用户在地址栏显性输入非法的pageNo以及pageSize的情况)
         *
         * 服务器端对pageNo, 和pageSize进行校验 (现在已经明确这两个数现在一定是整数)
         * 现在需要针对 pageNo < 1 以及 pageNo > pageTotal 的情况做处理
         * 如果 pageNo < 1, 就让 pageNo = 1
         * 如果 pageNo > pageTotal, 就让 pageNo = pageTotal
         *
         * 因为数据边界检查每个分页模块都需要做, 所以直接写到 Page类的  setPageNo()中去
         */
//        if (pageNo < 1) pageNo = 1;
//        if (pageNo > pageTotal)  pageNo = pageTotal;



        Page page = new Page();
        page.setItems(books);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setPageTotal(pageTotal);
        page.setTotalCount(totalCount);

        return page;
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public int deleteBook(Integer id) {
        return bookDao.deleteBookById(id);
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }
}
