<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="nlft">
		<ul class="wcc">
			<h1>
				<strong>资源管理</strong>
			</h1>
			<shiro:hasPermission name="resource:list">
				<li><a href="${ctx}/resource/list/" target="right"><span
						class="allIco ico15"></span>网页文件管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="photo-resource:list">
				<li><a href="${ctx}/photo-resource/list/" target="right"><span
						class="allIco ico15"></span>图片文件管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="security-resource:list">
				<li><a href="${ctx}/security-resource/list/" target="right"><span
						class="allIco ico15"></span>后台源文件管理</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	</div>
</body>
</html>
