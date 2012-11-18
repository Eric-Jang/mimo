<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {

		$("#evict-cache")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要刷新缓存的用户");
								return false;
							}
							if (items && items.length > 0
									&& confirm("你确定要刷新选中用户的缓存吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/security/user/evict-cache/",
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

		$("#lock")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要锁定的用户");
								return false;
							}

							if (confirm("你确定要锁定选中的用户吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/security/user/lock/",
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

							return false;
						});

		$("#not-lock")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length <= 0) {
								alert("请先选择要解锁的用户");
								return false;
							}
							if (confirm("你确定要锁定选中的用户吗?")) {
								var itemsAsString = items.join(",");
								$
										.ajax({
											url : "${ctx}/security/user/not-lock/",
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

							if (confirm("你确定要删除选中的用户吗?")) {

								$('input[name="_method"]').remove();
								$("#myForm")
										.attr("action",
												"${ctx}/security/user/delete/")
										.attr("method", "post")
										.append(
												'<input type="hidden" name="_method" value="DELETE" />')
										.submit();
								//加了这句之后 下面那就不在执行
								return false;
							}
						});
	});
</script>
</head>
<body>
	<div class="nrgt">

		<form id="myForm" action="${ctx }/security/user/list/" method="get">
			<div class="chsm">
				<span>用户管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasRole name="admin">
					<input class="tjbtn" id="del" type="button" value="删除" />
					<input class="tjbtn" id="lock" type="button" value="锁定" />
					<input class="tjbtn" id="not-lock" type="button" value="解锁" />
					<input class="tjbtn" id="evict-cache" type="button" value="刷新缓存" />
				</shiro:hasRole>
				<span class="dist">名称：</span><input name="params[name]"
					value="${page.params.name }" type="text" /><input class="tjbtn"
					type="submit" value="提交" />
			</div>
			<p>${message.text }</p>
			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th style="border-left: 0;" width="5%"><jsp:include
								page="/WEB-INF/commons/checkall.jsp"></jsp:include>&nbsp;</th>
						<th>用户名</th>
						<th>角色</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="user">
								<tr>
									<td style="border-left: 0;"><input type="checkbox"
										name="items" id="checkbox" value="${user.id}" /></td>
									<td>${user.username}&nbsp;</td>
									<td>${user.roleNamesAsString }&nbsp;</td>
									<td>${user.accountNonLocked == 'true' ? "正常" : "锁定"
										}&nbsp;</td>
									<td><shiro:hasRole name="admin">
											<a href="${ctx }/security/user/${user.id}/edit/">编辑</a>
										</shiro:hasRole> &nbsp;</td>
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
