<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx }/resources/css/base.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx }/resources/js/jquery.js" type="text/javascript"></script>
<title>登录我的Mimo</title>
<script type="text/javascript">
	$(function() {
		$("#username").focus();

		var showError = function(msg) {
			$("#error").empty().append(msg);
		};

		var code = "${param.code}";
		if (code) {
			try {
				var codeAsInt = parseInt(code, 10);
				switch (codeAsInt) {
				case 1:
					showError("无效的用户名密码！");
					break;
				case 2:
					showError("该用户已被锁定！");
					break;
				case 4:
					showError("无效的用户名密码！");
					break;
				case 8:
					showError("验证码错误！");
					break;
				default:
					showError("未知错误！");
					break;
				}
			} catch (e) {
				showError("未知错误！");
			}
		}

		$("#captcha").click(function() {
			var captchaUrl = "${ctx}/captcha/?" + Math.random().toString();
			$(this).attr("src", captchaUrl);
			$("#captchaText").focus();
		});
	});
</script>
</head>
<body>
	<div class="div960">
		<div class="logo">
			<a href="javascript:void(0)"><img
				src="${ctx }/resources/img/logo.gif" /></a>
		</div>
		<div class="fb">
			<a href="javascript:void(0)"><img
				src="${ctx }/resources/img/gs.gif" /></a>
		</div>
		<div class="clear line"></div>
	</div>
	<div class="center">
		<div class="left"></div>
		<div class="right">
			<form action="${ctx }/login" method="post">
				<div class="loginbg">
					<p id="error"></p>
					<div class="login_zliao">
						<div class="user">
							<div class="font1">用户名：</div>
							<div class="input1">
								<input id="username" type="text" name="username" maxlength="20"
									size="3"
									style="border:0px; background-repeat:no-repeat; background-image:url(${ctx}/resources/img/kk.jpg); width:153px; height:20px; padding-top:6px; padding-left:2px;" />
							</div>
						</div>
						<div class="password">
							<div class="font1">密&nbsp;&nbsp;码：</div>
							<div class="input1">
								<input type="password" name="password" maxlength="20" size="3"
									style="border:0px; background-repeat:no-repeat;background-image:url(${ctx}/resources/img/kk.jpg); width:153px; height:20px; padding-top:6px; padding-left:2px;" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="yzm">
							<div class="font1">验证码：</div>
							<div class="input2">
								<input type="text" id="captchaText" name="captcha"
									maxlength="20" size="3"
									style="border:0px; background-repeat:no-repeat; background-image:url(${ctx}/resources/img/kk2.jpg); width:80px; height:20px; padding-top:6px; padding-left:2px;" />
							</div>
							<img id="captcha" src="${ctx }/captcha/" style="cursor: pointer;" />
						</div>


						<div class="logo_wk_tijiao">
							<input id="sub" type="submit" value="登  录" name=""
								style="width: 90px; height: 25px;">
						</div>

					</div>
				</div>
			</form>
		</div>



		<div class="clear"></div>
	</div>

</body>
</html>
