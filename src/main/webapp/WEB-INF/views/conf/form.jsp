<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form:form action="${ctx }/conf/edit/" method="post"
		modelAttribute="conf" id="form">
		<input type="hidden" name="_method" value="put" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>站点配置</h1>
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
									class="red" style="color: red;">*</span>模板存储路径：</td>
								<td colspan="2"><form:input path="templatePath"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>网站资源路径：</td>
								<td colspan="2"><form:input path="resourcePath"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>允许上传或浏览的资源文件类型：</td>
								<td colspan="2"><form:input path="allowedResourceTypes"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">网站安全资源路径：</td>
								<td colspan="2"><form:input path="securityResourcePath"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">允许上传或浏览的安全资源文件类型：</td>
								<td colspan="2"><form:input
										path="allowedSecurityResourceTypes" cssClass="input5 fontMar" />
								</td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>图片存储路径：</td>
								<td colspan="2"><form:input path="photoPath"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>允许上传图片类型：</td>
								<td colspan="2"><form:input path="allowedPhotoTypes"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap">附件存储路径：</td>
								<td colspan="2"><form:input path="attachmentPath"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="240" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>上传文件最大字节数（通用）：</td>
								<td colspan="2"><form:input path="maxResourceSize"
										cssClass="input5 fontMar" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<shiro:hasPermission name="conf:edit">
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
