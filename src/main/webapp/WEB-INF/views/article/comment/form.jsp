<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form:form method="post" modelAttribute="articleComment">
		<input type="hidden" name="_method" value="${_method }" />
		<input type="hidden" name="article.id"
			value="${articleComment.article.id }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>文章留言管理</h1>
				</div>
				<div class="tips">
					<img src="${ctx}/resources/img/tips.gif" align="left" />&nbsp;所有带有<span
						class="red">*</span>为必填项
				</div>
				<div class="info border">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<tr>
								<td width="150" align="right" nowrap="nowrap">所属文章：</td>
								<td colspan="2"><form:input path="article.title"
										readonly="true" cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap">留言者：</td>
								<td colspan="2"><form:input path="author" readonly="true"
										cssClass="input5 fontMar" /></td>
							</tr>
							<!-- <tr> -->
							<!-- <td width="150" align="right" nowrap="nowrap">状态：</td> -->
							<!-- <td colspan="2"> -->
							<%-- <form:select path="status"> --%>
							<%-- 	<form:option value="1" label="正常" /> --%>
							<%-- 	<form:option value="0" label="审核中" /> --%>
							<%-- </form:select> --%>
							<!-- </td> -->
							<!-- </tr> -->
							<tr>
								<td width="150" align="right" nowrap="nowrap">内容：</td>
								<td colspan="2"><form:textarea path="content"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
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
