<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form method="post" enctype="multipart/form-data">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>上传模版</h1>
				</div>
				<div class="tips">
					<img src="${ctx}/resources/img/tips.gif" align="left" />&nbsp;所有带有<span
						class="red" style="color: red;">*</span>为必填项
				</div>
				<div class="info border">

					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>压缩文件（只支持zip）：</td>
								<td colspan="2"><input type="file" name="file"
									class="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>解压编码：</td>
								<td colspan="2"><select name="encoding">
										<option label="GBK" value="GBK" />
										<option label="UTF-8" value="UTF-8" />
								</select></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>模版文件后缀：</td>
								<td colspan="2"><input id="ftl" type="checkbox"
									name="suffixs" value="ftl" checked="checked" /> <label
									for="ftl">&nbsp;ftl&nbsp;</label> <input id="html"
									type="checkbox" name="suffixs" value="html" checked="checked" />
									<label for="html">&nbsp;html&nbsp;</label> <input id="htm"
									type="checkbox" name="suffixs" value="htm" /> <label for="htm">&nbsp;htm&nbsp;</label>
									<input id="txt" type="checkbox" name="suffixs" value="txt" /> <label
									for="txt">&nbsp;txt&nbsp;</label></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">注意事项：</span></td>
								<td colspan="2"><textarea rows="5" cols="80"
										readonly="readonly">1：windows平台下，zip压缩文件默认为GBK编码。2：请确保压缩包下所有可读文件编码为UTF-8编码，否则会出现乱码。3：linux平台下区分大小写，请规范路径，以免因大小写问题而不能访问。
	</textarea></td>
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
	</form>
</body>
</html>
