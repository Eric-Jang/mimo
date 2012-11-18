<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="${ctx }/resources/js/ueditor/themes/default/ueditor.css" />
</head>
<body>
	<form:form method="post" modelAttribute="article">
		<input type="hidden" name="_method" value="${_method }" />
		<c:choose>
			<c:when test="${not empty article.channel.id }">
				<input type="hidden" name="channel.id"
					value="${article.channel.id }" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="channel.id" value="${param.channelId }" />
			</c:otherwise>
		</c:choose>
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>文章管理</h1>
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
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>标题：</td>
								<td colspan="2"><form:input path="title"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap">来源：</td>
								<td colspan="2"><form:input path="source"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap">标签：</td>
								<td colspan="2"><form:input path="tags"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>优先级：</td>
								<td colspan="2"><form:input path="priority"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>内容：</td>
								<td colspan="2"><form:textarea path="content" rows="50"
										cols="120" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<input id="ok" type="submit" value="提交" class="button1" />&nbsp; <input
						id="createAndNew" type="submit" name="action" value="提交并新增"
						class="button1" />&nbsp; <input id=“back” type="button"
						value="返回" class="button1" onclick="javascript:history.go(-1);" />
				</div>
			</div>
		</div>
	</form:form>
	<script type="text/javascript"
		src="${ctx }/resources/js/ueditor/editor_config.js"></script>
	<script type="text/javascript"
		src="${ctx }/resources/js/ueditor/editor_all.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			// 覆盖全局的URL
			var URL = "${ctx}/resources/js/ueditor/".replace(/\/+/, "/");
			UEDITOR_CONFIG.UEDITOR_HOME_URL = URL;
			var editor = new baidu.editor.ui.Editor({
				autoHeightEnabled : false,
				imagePath : "${ctx}"
			});
			editor.render("content");

			var currentUrl = document.URL;
			if (currentUrl && currentUrl.indexOf("edit") !== -1) {
				$("#createAndNew").remove();
			}
		});
	</script>
</body>
</html>
