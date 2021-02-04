<%@ page import="com.atguigu.pojo.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">编辑图书</span>
			<%@include file="/pages/common/inner_manager.jsp"%>
		</div>
		
		<div id="main">
			<form action="manager/BookServlet" method="post">
<%--
            这个地方出现一个问题: 因为 book_edit.jsp 页面实际上需要实现 2 个功能: 添加, 和修改
            这个两个功能通过 action 的值(add/update)传递给 web层, 但是我如何实现为action动态赋值呢?

            解决方案:  在请求发起时(book_manager.jsp页面), 传入一个method参数, method参数有着add or update两个值
            在这边通过获取method的值, 填入action的value中
                book_manager.jsp页面传过来的method=add, 这边就是add
                传过来的method=update, 这边就是update

                !!!!!! 获取的时候使用 ${param.method}  是 param!!!!!获取请求参数!!!!
                不是 requestScope!!! 这个是自己保存的域数据!!!!!!
--%>
				<input type="hidden" name="action" value="${param.method}" />

                <input type="hidden" name="id" value="${requestScope.book.id}">
				<table>
					<tr>
						<td>名称</td>
						<td>价格</td>
						<td>作者</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>

						<td><input name="name" type="text" value="${requestScope.book.name}"/></td>
						<td><input name="price" type="text" value="${requestScope.book.price}"/></td>
						<td><input name="author" type="text" value="${requestScope.book.author}"/></td>
						<td><input name="sales" type="text" value="${requestScope.book.sales}"/></td>
						<td><input name="stock" type="text" value="${requestScope.book.stock}"/></td>
						<td><input type="submit" value="提交"/></td>
					</tr>	
				</table>
			</form>
			
	
		</div>

		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>