package com.atguigu.pojo;

import java.util.List;

/**
 * 新知识点:
 *      这个Page并不能仅仅针对图书, 我们可能还需要对用户列表进行分页, 如果是淘宝, 还需要对商户列表进行分页
 *      所以 items属性不能写死为<Book>, 而是 private List<T> items; 因此, Page类也要改为泛型类
 *      Page是分页的模型对象, Page<T> 的 T 是具体的javaBean类
 */
public class Page<T> {

    //每页显示的数量:  设置为常量(默认值)
    //为什么设置为 static????
    //为了其他的功能可以直接调用:  比如 BookServlet中 为 pageSize设置默认值
    // int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
    public static final Integer PAGE_SIZE = 4;

    private Integer pageNo; //当前页码

    private Integer pageTotal;  //总页数

    //总记录数
    private Integer totalCount;

    private Integer pageSize = PAGE_SIZE;  //每页显示数量

    private List<T> items;  //当前页数据


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
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
        if (pageNo < 1) pageNo = 1;
        if (pageNo > pageTotal)  pageNo = pageTotal;
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", items=" + items +
                '}';
    }
}
