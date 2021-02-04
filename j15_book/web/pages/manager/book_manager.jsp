<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			// 给删除的a标签绑定单击事件, 用于删除的确认提示
			$("a.deleteClass").click(function () {
				return confirm("你确定要删除["+ $(this).parent().parent().find("td:first").text() +"]吗?");
			});
		})
	</script>
</head>
		<%-- 这个版本关注:   图书列表的分页实现:
		  		添加页码模块  --%>

<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
		<%@include file="/pages/common/inner_manager.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>
			<c:forEach items="${requestScope.page.items}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.price}</td>
					<td>${book.author}</td>
					<td>${book.sales}</td>
					<td>${book.stock}</td>
					<td><a href="manager/BookServlet?action=getBook&id=${book.id}&method=update">修改</a></td>
					<td><a class="deleteClass" href="manager/BookServlet?action=delete&id=${book.id}">删除</a></td>
				</tr>
			</c:forEach>

			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_edit.jsp?method=add">添加图书</a></td>
			</tr>	
		</table>
	</div>

	<div id="page_nav">
<%--		上一页就是当前页码 - 1
			如何实现 当前页为 1 时, 不显示 上一页 标签
			使用 JSTL语句进行判断 !!!!
			当前页数 > 1 时, 才有 上一页的这个 <a> 标签--%>
		<c:if test="${requestScope.page.pageNo > 1}">
			<a href="manager/BookServlet?action=page&pageNo=1">首页</a>
			<a href="manager/BookServlet?action=page&pageNo=${requestScope.page.pageNo-1}" >上一页</a>
		</c:if>
<%--		动态生成 1到 pageTotal 的页数:
 			特殊需求:
 				要求生成5个连续的页码, 当前页在最中间
 				除了当前页码之外, 每个页码都可以点击跳到指定页
 				具体算法看讲义
 				--%>
		<c:forEach begin="1" end="${requestScope.page.pageTotal}" var="pageCode">
			<a href="manager/BookServlet?action=page&pageNo=${pageCode}">
				<c:if test="${pageCode == requestScope.page.pageNo}">
					[
				</c:if>
					${pageCode}
				<c:if test="${pageCode == requestScope.page.pageNo}">
					]
				</c:if>
			</a>
		</c:forEach>
		<c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
			<a href="manager/BookServlet?action=page&pageNo=${requestScope.page.pageNo+1}">下一页</a>
			<%--		末页就是总页数的那一页--%>
			<a href="manager/BookServlet?action=page&pageNo=${requestScope.page.pageTotal}">末页</a>
		</c:if>
		共${requestScope.page.pageTotal}页, ${requestScope.page.totalCount}条记录

<%--	通过输入 页数, 跳转到对应的页
		需要给 button绑定单击事件
		--%>
		到第<input type="text" name="pn" id="pn_input" value="${param.pageNo}" />页
		<input id="searchPageBtn" type="button" value="确定">

			<script type="text/javascript">
				$(function () {
					// 点击跳到指定的页码
					$("#searchPageBtn").click(function () {

						// 页码有效性判断 输入的页码 不能小于 1, 也不能大于 pageTotal
						var pageNumber = $("#pn_input").val();

						if (pageNumber < 1 || pageNumber > ${requestScope.page.pageTotal}){
							return false;
						}

						/**
						 * 注意:  上述的有效性校验都是用户点击button后, 才会做校验,
						 * 		  但是还可以在地址上显性地添加 pageNo 参数, 这样 js的校验就无法生效了
						 * 		  所以我们在服务器上还需要做一次校验 (BookService page方法)
						 */

						// 页码有效性判断 输入的页码:
						// 1 类型是 number
						// 2 number必须是整数 (任何整数对 1 取模结果都是 0, 小数对1取模, 结果是小数点后面的数)
						if (typeof pageNumber != 'number' && pageNumber % 1 != 0){
							return false;
						}

						// JavaScript语言中提供了一个 location 地址栏对象
						// 它有一个属性叫 href, 它可以获取浏览器地址栏中的地址
						// href属性可读可写
						//alert(location.href);

						//location.href = "http://www.baidu.com"; //页面跳转到了百度

						//给href赋值, 就能实现页面的跳转

						// location.href = "http://localhost:8080/j15_book/manager/BookServlet?action=page&pageNo=" + pageNumber;

						// 所以可以利用href的跳转功能:  将指定页面的参数传入请求 BookServlet的page方法的 URL中, 实现跳转
						// 注意!!!!!!!! ip:port/工程路径 这个地方就不可以写作  localhost:8080/j15_book 了, 否则对方就是访问自己的localhost了
						// 这个地方的值必须跟我们base标签中的值一样, 也同样是动态的
						// 怎么解决???
						// 在head.jsp中, 将bathPath 放入 pageContext的域数据中(pageContext只在同一jsp页面内有效)
						// 这边获取bathPath的值即可
						// 这边获取pageContext域数据使用的是 pageScope !!!!!!! 注意!!!!!!!!!!!!!!
						<%-- 并且 ${pageScope.basePath} 要写在冒号内!!!!!--%>
						location.href = "${pageScope.basePath}/manager/BookServlet?action=page&pageNo=" + pageNumber;

					})
				})
			</script>
	</div>

	<%@include file="/pages/common/footer.jsp"%>
</body>
</html>