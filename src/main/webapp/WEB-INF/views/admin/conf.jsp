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
				<strong>站点管理</strong>
			</h1>
			<shiro:hasPermission name="home-conf:edit">
				<li><a href="${ctx}/home-conf/edit/" target="right"><span
						class="allIco ico15"></span>首页配置</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="channel:list">
				<li><a href="${ctx}/channel/list/" target="right"><span
						class="allIco ico15"></span>栏目管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="channel:create">
				<li><a href="${ctx}/channel/create/" target="right"><span
						class="allIco ico15"></span>创建栏目</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:list">
				<li><a href="${ctx}/template/list/" target="right"><span
						class="allIco ico15"></span>模板管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="template:create">
				<li><a href="${ctx}/template/create/" target="right"><span
						class="allIco ico15"></span>创建模版</a></li>
				<li><a href="${ctx}/template/upload/" target="right"><span
						class="allIco ico15"></span>上传模版</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="article:list">
				<li><a href="${ctx}/article/list/offline/" target="right"><span
						class="allIco ico15"></span>文章回收站</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="article-comment:list">
				<li><a href="${ctx}/article-comment/list/" target="right"><span
						class="allIco ico15"></span>文章评论管理</a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="guestbook:list">
				<li><a href="${ctx}/guestbook/list/" target="right"><span
						class="allIco ico15"></span>留言管理</a></li>
			</shiro:hasPermission>
		</ul>
	</div>
</body>
</html>
