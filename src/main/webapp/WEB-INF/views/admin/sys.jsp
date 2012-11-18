<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<%@include file="/WEB-INF/commons/no-cache.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="nlft">
		<ul class="wcc">
			<h1>
				<strong>系统管理</strong>
			</h1>
			<shiro:hasRole name="admin">
				<li><a href="${ctx}/security/user/list/" target="right"><span
						class="allIco ico15"></span>用户管理</a></li>
				<li><a href="${ctx}/security/user/create/" target="right"><span
						class="allIco ico15"></span>创建用户</a></li>
				<li><a href="${ctx}/security/role/list/" target="right"><span
						class="allIco ico15"></span>角色管理</a></li>
				<li><a href="${ctx}/security/role/create/" target="right"><span
						class="allIco ico15"></span>创建角色</a></li>
				<li><a href="${ctx}/security/authority/list/" target="right"><span
						class="allIco ico15"></span>权限管理</a></li>
				<li><a href="${ctx}/security/authority/create/" target="right"><span
						class="allIco ico15"></span>创建权限</a></li>
				<li><a href="${ctx}/monitoring-record/list/" target="right"><span
						class="allIco ico15"></span>日志管理</a></li>
			</shiro:hasRole>
			<shiro:hasPermission name="conf:edit">
				<li><a href="${ctx}/conf/edit/" target="right"><span
						class="allIco ico15"></span>站点配置</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="email-conf:edit">
				<li><a href="${ctx}/email-conf/edit/" target="right"><span
						class="allIco ico15"></span>邮件通知配置</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
</body>
</html>
