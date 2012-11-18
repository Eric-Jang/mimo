<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form:form action="${ctx }/home-conf/edit/" method="post"
		modelAttribute="homeConf" id="form">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>首页配置</h1>
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
									class="red" style="color: red;">*</span>网页标题：</td>
								<td colspan="2"><form:input path="title"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>模板：</td>
								<td colspan="2"><form:select path="template.id">
										<form:option value="">请选择</form:option>
										<form:options items="${templates}" itemValue="id"
											itemLabel="name" />
									</form:select></td>
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
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<shiro:hasPermission name="home-conf:edit">
						<input id="ok" type="submit" value="提交" class="button1" />&nbsp;
</shiro:hasPermission>
					<input id=“back” type="button" value="返回" class="button1"
						onclick="javascript:history.go(-1);" />
				</div>

			</div>
		</div>
	</form:form>
</body>
</html>
