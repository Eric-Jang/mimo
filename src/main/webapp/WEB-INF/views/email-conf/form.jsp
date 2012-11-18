<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form:form action="${ctx }/email-conf/edit/" method="post"
		modelAttribute="emailConf" id="form">
		<input type="hidden" name="_method" value="put" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>邮件通知配置</h1>
				</div>
				<div class="tips">
					<img src="${ctx}/resources/img/tips.gif" align="left" />&nbsp;所有带有<span
						class="red" style="color: red;">*</span>为必填项
				</div>
				<p>${message.text }</p>
				<div class="info border">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>smtp服务器地址：</td>
								<td colspan="2"><form:input path="smtpServer"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>发送者的邮箱地址：</td>
								<td colspan="2"><form:input path="sender"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>用户名：</td>
								<td colspan="2"><form:input path="username"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>密码：</td>
								<td colspan="2"><form:password path="password"
										showPassword="true" cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>邮件接收用户（多个用户请用；分开）：</td>
								<td colspan="2"><form:textarea path="receivers"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>监听以下事件：</td>
								<td colspan="2"><form:checkbox path="events" value="创建用户"
										label="创建用户" /> <form:checkbox path="events" value="修改用户"
										label="修改用户" /> <form:checkbox path="events" value="删除用户"
										label="删除用户" /> <form:checkbox path="events" value="创建角色"
										label="创建角色" /> <form:checkbox path="events" value="修改角色"
										label="修改角色" /> <form:checkbox path="events" value="删除角色"
										label="删除角色" /> <form:checkbox path="events" value="创建权限"
										label="创建权限" /> <form:checkbox path="events" value="修改权限"
										label="修改权限" /> <form:checkbox path="events" value="删除权限"
										label="删除权限" /> <form:checkbox path="events" value="创建栏目"
										label="创建栏目" /> <form:checkbox path="events" value="修改栏目"
										label="修改栏目" /> <form:checkbox path="events" value="删除栏目"
										label="删除栏目" /> <form:checkbox path="events" value="创建模版"
										label="创建模版" /> <form:checkbox path="events" value="修改模版"
										label="修改模版" /> <form:checkbox path="events" value="删除模版"
										label="删除模版" /> <form:checkbox path="events" value="创建文章"
										label="创建文章" /> <form:checkbox path="events" value="修改文章"
										label="修改文章" /> <form:checkbox path="events" value="删除文章"
										label="删除文章" /> <form:checkbox path="events" value="创建文章评论"
										label="创建文章评论" /> <form:checkbox path="events" value="修改文章评论"
										label="修改文章评论" /> <form:checkbox path="events" value="删除文章评论"
										label="删除文章评论" /> <form:checkbox path="events" value="创建留言"
										label="创建留言" /> <form:checkbox path="events" value="修改留言"
										label="修改留言" /> <form:checkbox path="events" value="删除留言"
										label="删除留言" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<shiro:hasPermission name="email-conf:edit">
						<input id="ok" type="submit" value="提交" class="button1" />&nbsp;
					</shiro:hasPermission>
					<input id="back" type="button" value="返回" class="button1"
						onclick="javascript:history.go(-1);" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
