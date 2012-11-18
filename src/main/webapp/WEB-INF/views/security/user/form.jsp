<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {
		var currentUrl = document.URL;
		if (currentUrl && currentUrl.indexOf("edit") !== -1) {
			$("#username").attr("readonly", "readonly");
			$("#password-tip")
					.empty()
					.append(
							'<span class="red" style="color: red;" >（如不修改，请留空）</span>密码：');
		}
	});
</script>
</head>
<body>
	<form:form method="post" modelAttribute="user" id="form">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>用户管理</h1>
				</div>
				<div class="tips">
					<img src="${ctx}/resources/img/tips.gif" align="left" />&nbsp;所有带有<span
						class="red" style="color: red;">*</span>为必填项
				</div>
				<div class="info border">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>用户名：</td>
								<td colspan="2"><form:input path="username" id="username"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap" id="password-tip"><span
									class="red" style="color: red;">*</span>密码：</td>
								<td colspan="2"><input type="password" id="password"
									name="password" class="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">角色：</td>
								<td colspan="2"><c:forEach var="role" items="${roles }">
										<c:choose>
											<c:when test="${user.rolesTrans[role.id] ne null }">
												<input type="checkbox" id="role${role.id }"
													name="rolesTrans[${role.id }]" value="${role.id}"
													checked="checked" />
												<label for="role${role.id }">${role.name }</label>
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="role${role.id }"
													name="rolesTrans[${role.id }]" value="${role.id}" />
												<label for="role${role.id }">${role.name }</label>
											</c:otherwise>
										</c:choose>
									</c:forEach></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<input id="ok" type="submit" value="提交" class="button1" />&nbsp; <input
						id=“back” type="button" value="返回" class="button1"
						onclick="javascript:history.go(-1);" />
				</div>

			</div>
		</div>
	</form:form>
</body>
</html>
