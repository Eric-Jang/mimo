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
												"${ctx}/template/delete/")
										.attr("method", "post")
										.append(
												'<input type="hidden" name="_method" value="DELETE" />')
										.submit();

								return false;
							}
						});

		$("#generate").click(function() {

			$.ajax({
				url : "${ctx}/template/generate/",
				type : "POST",
				data : {
					"_method" : "PUT"
				},
				dataType : "json",
				success : function(data) {
					if (data.state && data.state.indexOf("SUCCESS") != -1) {
						alert("模板文件已生成");
						return;
					}

					alert("模板文件生成发生错误，请稍后重试！");
					location.reload();
				}
			});

			return false;
		});

	});
</script>
</head>
<body>
	<div class="nrgt">
		<form id="myForm" action="${ctx }/template/list/" method="get">
			<div class="chsm">
				<span>模板管理</span>
			</div>
			<div class="czsrt">
				<span>你可以：</span>
				<shiro:hasPermission name="template:delete">
					<input class="tjbtn" id="del" type="button" value="删除" />
				</shiro:hasPermission>
				<shiro:hasPermission name="template:edit">
					<input class="tjbtn" id="generate" type="button" value="生成文件" />
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
						<th>模板名称</th>
						<th>模板路径</th>
						<th>模板编码</th>
						<th>最后更新时间</th>
						<th>操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="template">
								<tr>
									<td style="border-left: 0;"><input type="checkbox"
										name="items" id="checkbox" value="${template.id}" /></td>
									<td>${template.name}&nbsp;</td>
									<td>${template.path }&nbsp;</td>
									<td>${template.encode}&nbsp;</td>
									<td class="date">${template.modifyTime}&nbsp;</td>
									<td><shiro:hasPermission name="template:edit">
											<a href="${ctx }/template/${template.id}/edit/">编辑</a>&nbsp;
		</shiro:hasPermission></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" align="center"><b>暂无内容</b></td>
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
