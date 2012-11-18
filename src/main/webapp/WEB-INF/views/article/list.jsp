<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {
		$("#add").click(
				function() {
					location.href = "${ctx}/article/create/?channelId="
							.concat("${page.params.channelId}");
				});

		$("#offline")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要放入回收站的文章");
								return false;
							}
							if (confirm("你确定要把选中的文章放入回收站吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/offline/",
											type : "POST",
											data : {
												"_method" : "PUT",
												"items" : itemsAsString
											},
											dataType : "json",
											success : function(data) {
												if (data
														&& data.state
														&& (data.state
																.indexOf("ERROR") != -1)) {
													alert(data.message);
													return;
												}

												location.reload();
											}
										});

								return false;
							}
						});

		$("#on-top")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择置顶的文章");
								return false;
							}
							if (confirm("你确定要把选中的内容置顶吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/on-top/",
											type : "POST",
											data : {
												"_method" : "PUT",
												"items" : itemsAsString
											},
											dataType : "json",
											success : function(data) {
												if (data
														&& data.state
														&& (data.state
																.indexOf("ERROR") != -1)) {
													alert(data.message);
													return;
												}

												location.reload();
											}
										});

								return false;
							}

						});

		$("#not-on-top")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择取消置顶的文章");
								return false;
							}
							if (confirm("你确定要把选中的内容取消置顶吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/not-on-top/",
											type : "POST",
											data : {
												"_method" : "PUT",
												"items" : itemsAsString
											},
											dataType : "json",
											success : function(data) {
												if (data
														&& data.state
														&& (data.state
																.indexOf("ERROR") != -1)) {
													alert(data.message);
													return;
												}

												location.reload();
											}
										});

								return false;
							}

						});

		$("#not-comments")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择禁止评论的内容");
								return false;
							}
							if (confirm("你确定要把选中的内容禁止评论吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/not-comments/",
											type : "POST",
											data : {
												"_method" : "PUT",
												"items" : itemsAsString
											},
											dataType : "json",
											success : function(data) {
												if (data
														&& data.state
														&& (data.state
																.indexOf("ERROR") != -1)) {
													alert(data.message);
													return;
												}

												location.reload();
											}
										});

								return false;
							}

						});

		$("#allow-comments")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择允许评论的内容");
								return false;
							}
							if (confirm("你确定要把选中的内容允许评论吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/allow-comments/",
											type : "POST",
											data : {
												"_method" : "PUT",
												"items" : itemsAsString
											},
											dataType : "json",
											success : function(data) {
												if (data
														&& data.state
														&& (data.state
																.indexOf("ERROR") != -1)) {
													alert(data.message);
													return;
												}

												location.reload();
											}
										});

								return false;
							}

						});

	});
</script>
</head>
<body>
	<div class="nrgt">

		<form id="myForm" action="${ctx }/article/list/online/" method="get">
			<div class="chsm">
				<span>内容管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasPermission name="article:create">
					<input class="tjbtn" id="add" type="button" value="新增" />
				</shiro:hasPermission>
				<shiro:hasPermission name="article:edit">
					<input class="tjbtn" id="offline" type="button" value="回收站" />
					<input class="tjbtn" id="on-top" type="button" value="置顶" />
					<input class="tjbtn" id="not-on-top" type="button" value="不置顶" />
					<input class="tjbtn" id="not-comments" type="button" value="禁止评论" />
					<input class="tjbtn" id="allow-comments" type="button" value="允许评论" />
				</shiro:hasPermission>
				<span>你可以：</span> <span class="dist">标题：</span><input
					name="params[channel]" value="${page.params.channel}" type="hidden" /><input
					name="params[title]" value="${page.params.title}" type="text" /><input
					class="tjbtn" type="submit" value="提交" />
			</div>
			<p>${message.text }</p>
			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th style="border-left: 0;" width="5%"><jsp:include
								page="/WEB-INF/commons/checkall.jsp"></jsp:include>&nbsp;</th>
						<th width="20%">文章标题</th>
						<th>文章来源</th>
						<th>最后更新时间</th>
						<th>文章属性</th>
						<th width="10%">操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result}" var="article" begin="0">
								<tr>
									<td><input type="checkbox" name="items" id="checkbox"
										value="${article.id}" /></td>
									<td>${fn:substring(article.title,0,15)}&nbsp;</td>
									<td>${fn:substring(article.source,0,15)}&nbsp;</td>
									<td class="date">${article.modifyTime}</td>
									<td>${article.onTop == 'true' ? '[置顶]&nbsp;' :
										'[非置顶]&nbsp;' } ${article.forbidComments == 'true' ?
										'[禁止评论]&nbsp;' : '[允许评论]&nbsp;' } [优先级:${article.priority
										}]&nbsp;</td>
									<td><a
										href="${ctx }/${article.channel.path }/${article.id}"
										target="_blank">浏览</a>&nbsp; <shiro:hasPermission
											name="article:edit">
											<a href="${ctx }/article/${article.id}/edit/">编辑</a>&nbsp;
						</shiro:hasPermission></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7" align="center"><b>暂无内容</b></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<!--page-->
				<div class="page"><jsp:include
						page="/WEB-INF/commons/page.jsp" /></div>
			</div>
		</form>
	</div>
</body>
</html>
