<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
	<%@include file="/pages/common/head.jsp"%>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>尚硅谷会员</h1>
								<a href="pages/user/regist.jsp">立即注册</a>
							</div>
							<div class="msg_cont">
								<b></b>
								<%--
									此处修改:  当 requestScope.errorInfo 为null时, 说明用户还没有登录失败, 所以提示 "请输入用户名和密码"
									否则, 输出错误提示信息
									--%>
								<span class="errorInfo">
<%--									${requestScope.errorInfo == null ? "请输入用户名和密码" : requestScope.errorInfo}--%>
									${empty requestScope.errorInfo ? "请输入用户名和密码" : requestScope.errorInfo}
								</span>
							</div>
							<div class="form">
								<form action="UserServlet" method="post">

									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username"
										   value="${requestScope.username}" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password"/>
									<br />
									<br />
									<input type="submit" value="登录" id="sub_btn" />

<%--									设置隐藏域--%>
									<input type="hidden" name="action" value="login">

								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>