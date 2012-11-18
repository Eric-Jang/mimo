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
	<form:form method="post" modelAttribute="channel" id="form">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>栏目管理</h1>
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
								<td colspan="2"><form:input path="name"
										cssClass="input5 fontMar" id="channel" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>访问路径：</td>
								<td colspan="2"><form:input path="path"
										cssClass="input6 fontMar" id="path" />&nbsp;（如：aboutus）</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>排序：</td>
								<td colspan="2"><form:input path="priority"
										cssClass="input6 fontMar" id="sort" />&nbsp;（数值越<span
									class="red" style="color: red;">高</span>，越靠<span class="red"
									style="color: red;">前</span>显示）</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">父栏目：</td>
								<td colspan="2"><form:select path="father.id">
										<form:option value="">请选择</form:option>
										<form:options items="${channelViews}" itemValue="id"
											itemLabel="levelName" />
									</form:select></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>栏目显示模板：</td>
								<td colspan="2"><form:select path="selfTemplate.id">
										<form:option value="">请选择</form:option>
										<form:options items="${templates}" itemValue="id"
											itemLabel="name" />
									</form:select></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">文章显示模板：</td>
								<td colspan="2"><form:select path="articleTemplate.id">
										<form:option value="">请选择</form:option>
										<form:options items="${templates}" itemValue="id"
											itemLabel="name" />
									</form:select></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">网页标题：</td>
								<td colspan="2"><form:textarea path="title"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">meta标题：</td>
								<td colspan="2"><form:textarea path="metaTitle"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">meta描述：</td>
								<td colspan="2"><form:textarea path="metaDescr"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">meta关键字：</td>
								<td colspan="2"><form:textarea path="metaKeyword"
										cssClass="input5 fontMar"
										style="margin-top: 2px; margin-bottom: 2px; height: 80px; margin-left: 2px; margin-right: 2px; width: 318px;" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">关于：</td>
								<td colspan="2"><form:textarea path="about" rows="50"
										cols="120" /></td>
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
	<script type="text/javascript"
		src="${ctx }/resources/js/ueditor/editor_config.js"></script>
	<script type="text/javascript"
		src="${ctx }/resources/js/ueditor/editor_all.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		
		// 覆盖全局的URL
		var URL = "${ctx}/resources/js/ueditor/".replace(/\/+/,"/");
		UEDITOR_CONFIG.UEDITOR_HOME_URL = URL;
		var editor = new baidu.editor.ui.Editor({autoHeightEnabled:false,imagePath:"${ctx}"});
	    editor.render("about");
	});
</script>
</body>
</html>
