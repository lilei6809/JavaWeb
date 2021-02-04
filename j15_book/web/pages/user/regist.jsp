<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
	<%@include file="/pages/common/head.jsp"%>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
	<script type="text/javascript">
		/**
		 * 需要通过jQuery实现表单验证
		 *
		 *  验证用户名： 必须由字母， 数字下划线组成， 并且长度为 5 到 12 位
		 	验证密码： 必须由字母， 数字下划线组成， 并且长度为 5 到 12 位
		 	验证确认密码： 和密码相同
		 	邮箱验证： xxxxx@xxx.com
		 	验证码： 现在只需要验证用户已输入。 因为还没讲到服务器。 验证码生成

		 	生成相应的错误信息, 放入 .errorMsg span标签中
		 */
		//页面加载完成后
		$(function () {

			//为注册按钮绑定单击事件
			let $sub = $("#sub_btn");
			
			$sub.bind("click", function () {

				let $errorMsg = $("span.errorMsg");

				//验证用户名： 必须由字母， 数字下划线组成， 并且长度为 5 到 12 位
				//1 获取用户名输入框的内容
				let usernameText = $("#username").val();
				// 2 创建正则表达式
				let usernamePatt = /^\w{5,12}$/;
				//3 使用test方法验证
				let b = usernamePatt.test(usernameText);
				//4 提示用户结果
				if (!b){
					$("span.errorMsg").text("用户名不合法");
					return false;  //阻止input默认行为
				}

				//验证密码： 必须由字母， 数字下划线组成， 并且长度为 5 到 12 位
				let pswText = $("#password").val();
				let pswPatt = /^\w{5,12}$/;
				b = pswPatt.test(pswText);
				if (!b){
					$errorMsg.text("密码不合法");
					return false;
				}

				//验证确认密码： 和密码相同
				let repwdText = $("#repwd").val();
				if (repwdText != pswText){
					$errorMsg.text("两次输入的密码不一样");
					return false;
				}

				//邮箱验证： xxxxx@xxx.com
				let emailText = $("#email").val();
				let emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
				b = emailPatt.test(emailText);
				if (!b){
					$errorMsg.text("邮箱输入有误");
					return false;
				}
				// //验证码： 现在只需要验证用户已输入。 因为还没讲到服务器。 验证码生成
				// //去掉字符串前后的空格  $.trim("字符串")
				// let codeText = $("#code").val();
				// codeText = $.trim(codeText);
				// if (codeText != "bmbmp"){
				// 	$errorMsg.text("验证码输入有误");
				// 	return false;
				// }


				//程序走到这儿, 说明上述验证都没有问题, 则错误提示信息要清除
				$("span.errorMsg").text("");
			})

		})
	</script>

</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">
<%--									${requestScope.errorInfo == null ? "":requestScope.errorInfo}--%>
									${requestScope.errorInfo}
<%--									因为初始注册时这个地方没有任何的提示信息--%>
								</span>
							</div>
							<div class="form">
								<form action="UserServlet" method="post">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名" autocomplete="off"
										   tabindex="1" name="username" id="username" value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off"
										   tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码" autocomplete="off"
										   tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off"
										   tabindex="1" name="email" id="email" value="${requestScope.email}"/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" style="width: 150px;" name="code" id="code"/>
									<img alt="" src="static/img/code.bmp" style="float: right; margin-right: 40px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />

									<input type="hidden" name="action" value="regist">
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>