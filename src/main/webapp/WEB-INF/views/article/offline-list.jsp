<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {
		$("#online")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要还原的内容");
								return false;
							}

							if (confirm("你确定要把选中的内容还原吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/article/online/",
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

		$("#del")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要删除的内容");
								return false;
							}

							if (confirm("你确定要删除这些内容吗?")) {
								$('input[name="_method"]').remove();
								$("#myForm")
										.attr("action",
												"${ctx}/article/delete/")
										.attr("method", "post")
										.append(
												'<input type="hidden" name="_method" value="DELETE" />')
										.submit();
								return false;
							}
						});

	});
</script>
</head>
<body>
	<div class="nrgt">

		<form id="myForm" action="${ctx }/article/list/offline/" method="get">
			<input type="hidden" name="pathname" value="${param.pathname}" />
			<div class="chsm">
				<span>文章回收站</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasPermission name="article:create">
					<input class="tjbtn" id="del" type="button" value="删除" />
					<input class="tjbtn" id="online" type="button" value="还原" />
				</shiro:hasPermission>
				<span>你可以：</span> <span class="dist">名称：</span><input
					name="params[pathname]" value="${page.params.actor}" type="text" /><input
					class="tjbtn" type="submit" value="提交" />
			</div>

			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th style="border-left: 0;" width="5%"><jsp:include
								page="/WEB-INF/commons/checkall.jsp"></jsp:include>&nbsp;</th>
						<th width="10%">所属栏目</th>
						<th width="20%">文章标题</th>
						<th>文章来源</th>
						<th>最后更新时间</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result}" var="article" begin="0">
								<tr>
									<td><input type="checkbox" name="items" id="checkbox"
										value="${article.id}" /></td>
									<td>${article.channel.name}&nbsp;</td>
									<td>${fn:substring(article.title,0,15)}&nbsp;</td>
									<td>${fn:substring(article.source,0,15)}&nbsp;</td>
									<td class="date">${article.modifyTime}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" align="center"><b>暂无内容</b></td>
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
