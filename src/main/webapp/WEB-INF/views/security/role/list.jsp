<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	$(function() {

		$("#del")
				.click(
						function() {
							var items = mimo.select();
							if (items && items.length > 0
									&& confirm("你确定要删除这些内容吗?")) {

								$('input[name="_method"]').remove();
								$("#myForm")
										.attr("action",
												"${ctx}/security/role/delete/")
										.attr("method", "post")
										.append(
												'<input type="hidden" name="_method" value="DELETE" />')
										.submit();
								//加了这句之后 下面那就不在执行
								return false;
							}

							alert("请先选择要删除的内容");
							return false;
						});
	});
</script>
</head>
<body>
	<div class="nrgt">
		<form id="myForm" action="${ctx }/security/role/list/" method="get">
			<div class="chsm">
				<span>用户角色管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasRole name="admin">
					<input class="tjbtn" id="del" type="button" value="删除" />
				</shiro:hasRole>
				<span class="dist">名称：</span><input name="params[showName]"
					value="${page.params.showName }" type="text" /><input
					class="tjbtn" type="submit" value="提交" />
			</div>
			<p>${message.text }</p>
			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th style="border-left: 0;" width="5%"><jsp:include
								page="/WEB-INF/commons/checkall.jsp"></jsp:include>&nbsp;</th>
						<th>显示名称</th>
						<th>名称</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="role">
								<tr>
									<td style="border-left: 0;"><input type="checkbox"
										name="items" id="checkbox" value="${role.id}" /></td>
									<td>${role.showName}&nbsp;</td>
									<td>${role.name }&nbsp;</td>
									<td><shiro:hasRole name="admin">
											<a href="${ctx }/security/role/${role.id}/edit/">编辑</a>
										</shiro:hasRole> &nbsp;</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4" align="center"><b>暂无内容</b></td>
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
