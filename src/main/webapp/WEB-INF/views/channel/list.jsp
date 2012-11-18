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
							if (items && items.length <= 0) {
								alert("请先选择要删除的内容");
								return false;
							}

							if (confirm("你确定要删除这些内容吗?")) {

								$('input[name="_method"]').remove();
								$("#myForm")
										.attr("action",
												"${ctx}/channel/delete/")
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

		<form id="myForm" action="${ctx }/channel/list/" method="get">
			<div class="chsm">
				<span>栏目管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasPermission name="channel:delete">
					<input class="tjbtn" id="del" type="button" value="删除" />
				</shiro:hasPermission>
				<span class="dist">名称：</span><input name="params[name]"
					value="${page.params.name}" type="text" /><input class="tjbtn"
					type="submit" value="提交" />
			</div>
			<p>${message.text }</p>
			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th style="border-left: 0;" width="5%"><jsp:include
								page="/WEB-INF/commons/checkall.jsp"></jsp:include>&nbsp;</th>
						<th>名称</th>
						<th>栏目模板</th>
						<th>内容模板</th>
						<th>访问路径</th>
						<th>优先级</th>
						<th width="10%">操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="channel">
								<tr>
									<td style="border-left: 0;"><input type="checkbox"
										name="items" id="checkbox" value="${channel.id}" /></td>
									<td>${channel.name}&nbsp;</td>
									<td>${channel.selfTemplate.name}&nbsp;</td>
									<td>${channel.articleTemplate.name}&nbsp;</td>
									<td>${channel.path }&nbsp;</td>
									<td>${channel.priority}&nbsp;</td>
									<td><a href="${ctx }/${channel.path}" target="_blank">浏览</a>&nbsp;
										<shiro:hasPermission name="channel:edit">
											<a href="${ctx }/channel/${channel.id}/edit/">编辑</a>&nbsp;
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
