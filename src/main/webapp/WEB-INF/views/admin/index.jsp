<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mini & More -- 轻量级内容管理系统</title>
</head>
<frameset rows="130,*,46" frameborder="NO" border="0" framespacing="0">
<frame src="${ctx}/admin/head/" name="top" scrolling="NO" noresize /> <frameset
	cols="17%,83%" frameborder="NO" border="0" framespacing="0">
<frame src="${ctx}/admin/conf/" name="left" scrolling="no" noresize />
<frame src="${ctx}/admin/right/" name="right" scrolling="yes" noresize />
</frameset> <frame src="${ctx }/admin/foot/" name="foot" scrolling="NO" noresize />
</frameset>
<noframes>
	<body>很抱歉，你所使用的浏览器不支援框架功能，请转用新的浏览器
	</body>
</noframes>
</html>
