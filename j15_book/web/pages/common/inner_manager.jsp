<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/2/2
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>



    <%--		分页功能的实现:
                    之前我们点击 图书管理 链接, 请求的 action=list
                    但是现在我们需要实现分页, 就需要请求 BookServlet的page方法--%>


    <a href="manager/BookServlet?action=page">图书管理</a>
    <a href="pages/manager/order_manager.jsp">订单管理</a>
    <a href="index.jsp">返回商城</a>
</div>
