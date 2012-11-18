<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<%@include file="/WEB-INF/commons/no-cache.jsp"%>
<link href="${ctx }/resources/dtree/dtree.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx }/resources/dtree/dtree.js" type="text/javascript"></script>
<script type="text/javascript">
	channelViews = new dTree('channelViews');
	channelViews.config.target = "right";
	channelViews.config.inOrder = true;

	$(function() {
		$.ajax({
			url : "${ctx}/channel/views/",
			type : "POST",
			dataType : "json",
			success : function(data) {
				channelViews.add("0", "-1", "内容管理", "", "内容管理");

				$.each(data, function(index, channel) {
					channelViews.add(channel.id, channel.fatherId,
							channel.name,
							"${ctx}/article/list/online/?params[channelId]="
									+ channel.id, channel.name);
				});

				var i = channelViews + "";
				$("#tree").append(i);
			}
		});
	});
</script>
</head>
<body>
	<div class="nlft">
		<div id="tree" class="total"></div>
	</div>
</body>
</html>
