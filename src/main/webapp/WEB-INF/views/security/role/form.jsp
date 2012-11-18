<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form:form method="post" modelAttribute="role" id="form">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>用户角色管理</h1>
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
									class="red" style="color: red;">*</span>显示名称：</td>
								<td colspan="2"><form:input path="showName"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>名称：</td>
								<td colspan="2"><form:input path="name"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">权限：</td>
								<td colspan="2"><c:forEach var="auth"
										items="${authorities }">
										<c:choose>
											<c:when test="${role.authoritiesTrans[auth.id] ne null }">
												<input type="checkbox" id="auth${auth.id }"
													name="authoritiesTrans[${auth.id }]" value="${auth.id}"
													checked="checked" />
												<label for="auth${auth.id }">${auth.name }</label>
											</c:when>
											<c:otherwise>
												<input type="checkbox" id="auth${auth.id }"
													name="authoritiesTrans[${auth.id }]" value="${auth.id}" />
												<label for="auth${auth.id }">${auth.name }</label>
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
