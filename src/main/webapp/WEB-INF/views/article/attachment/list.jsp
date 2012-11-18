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
												"${ctx}/article/attachment/delete")
										.attr("method", "post")
										.append(
												'<input type="hidden" name="_method" value="DELETE" />')
										.submit();

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

		<form id="myForm" action="${ctx }/article/attachment/list"
			method="get">
			<div class="chsm">
				<span>文章附件管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasPermission name="article-attachment:delete">
					<input class="tjbtn" id="del" type="button" value="删除" />
				</shiro:hasPermission>
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
						<th>所属文章</th>
						<th>名称</th>
						<th>路径</th>
						<th>类型</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="articleAttachment">
								<tr>
									<td style="border-left: 0;"><input type="checkbox"
										name="items" id="checkbox" value="${articleAttachment.id}" /></td>
									<td>${articleAttachment.article.title}&nbsp;</td>
									<td>${articleAttachment.name }&nbsp;</td>
									<td>${articleAttachment.path }&nbsp;</td>
									<td>${articleAttachment.contentType}&nbsp;</td>
									<td class="date">${articleAttachment.createTime}&nbsp;</td>
									<td><shiro:hasPermission
											name="article-attachment:download">
											<a
												href="${ctx }/article/attachment/${articleAttachment.id}/download">下载</a>
										</shiro:hasPermission> &nbsp;</td>
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
