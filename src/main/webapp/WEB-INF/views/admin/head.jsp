<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {
		var navs = $(".n_lft > li > a");
		if (navs && navs[0]) {
			$(navs[0]).addClass("nav_cur");
		}

		var reset = function() {
			navs.each(function() {
				$(this).removeClass("nav_cur");
			});
		};

		navs.each(function() {
			var $this = $(this);
			$this.click(function() {
				reset();
				$this.addClass("nav_cur");
			});
		});
	});
</script>
</head>
<body>
	<div class="head">
		<div class="div960">
			<div class="wm"></div>
		</div>
	</div>

	<div class="nav">
		<div class="div960">
			<ul class="ldst">
				<shiro:authenticated>
					<li><a target="right" href="${ctx }/admin/right/">系统信息</a></li>
				</shiro:authenticated>
				<shiro:authenticated>
					<li><a target="left" href="${ctx }/admin/conf/">站点管理</a></li>
				</shiro:authenticated>
				<shiro:hasPermission name="article:list">
					<li><a target="left" href="${ctx }/admin/content/">内容管理</a></li>
				</shiro:hasPermission>
				<shiro:authenticated>
					<li><a target="left" href="${ctx }/admin/resource/">资源管理</a></li>
				</shiro:authenticated>
				<shiro:authenticated>
					<li><a target="left" href="${ctx }/admin/sys/">系统管理</a></li>
				</shiro:authenticated>
				<li class="wel"><p>
						欢迎您：<span><shiro:principal /></span><a href="${ctx}"
							target="_blank">访问网站</a><a href="${ctx}/admin/logout/"
							target="_parent">安全退出</a>
					</p></li>
				<li class="clear"></li>
			</ul>
		</div>
	</div>


	<div class="clear"></div>
</body>
</html>
