<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="codec" uri="/WEB-INF/tld/codec.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="${ctx }/resources/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$(".del").each(function() {
			var $this = $(this);
			$this.click(function() {
				if (confirm("确定删除该数据吗？")) {
					var pathname = $this.attr("id");
					$.ajax({
						url : "${ctx}/security-resource/delete/",
						type : "POST",
						data : {
							"pathname" : pathname,
							"_method" : "DELETE"
						},
						success : function() {
							location.reload();
						},
						error : function() {
							alert("无法删除该数据！");
						}
					});
				}

				return false;
			});
		});

		$("#upload").click(
				function() {
					var $form = $("#uploadForm");
					$form.ajaxSubmit({
						url : "${ctx}/security-resource/upload/",
						type : "POST",
						data : {
							"pathname" : "${param.pathname}"
						},
						dataType : "json",
						success : function(data) {
							if (data && data.state
									&& data.state.indexOf("SUCCESS") != -1) {
								location.reload();
								return;
							}

							alert(data.message);
							location.reload();
						},
						error : function(request, e) {
							alert("上传失败，请重试！");
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

		<div class="chsm">
			<span>后台源文件管理</span>
		</div>
		<div class="czsrt">
			<span>当前目录：<c:choose>
					<c:when test="${not empty param.pathname }">${param.pathname }</c:when>
					<c:otherwise>/</c:otherwise>
				</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
			</span> <input class="tjbtn" onclick="javascript:history.go(-1);"
				type="button" value="后退" />
		</div>
		<div class="czsrt">
			<shiro:hasPermission name="security-resource:upload">
				<form id="uploadForm" enctype="multipart/form-data">
					<span class="dist">上传文件：</span> <input id="file" type="file"
						name="file" size="30" class="ifile"
						onchange="this.form.filePath.value=this.value.substr(this.value.lastIndexOf('\\')+1);" />
					<input id="filePath" type="text" size="30" readonly="readonly" />
					<input class="tjbtn" id="review" type="button" value="浏览"
						onclick="this.form.file.click();" /> <input class="tjbtn"
						id="upload" type="button" value="上传" />
				</form>
			</shiro:hasPermission>
		</div>

		<form id="myForm" action="${ctx }/security-resource/list/"
			method="get">
			<input type="hidden" name="pathname" value="${param.pathname}" />
			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th width="25%">文件名称</th>
						<th>文件路径</th>
						<th>文件大小</th>
						<th>文件最后修改时间</th>
						<th width="8%">操作</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="bean">
								<tr>
									<td><c:choose>
											<c:when test="${bean.directory }">
												<a
													href="${ctx }/security-resource/list/?pathname=${codec:urlEncode(bean.path)}">
													[目录]&nbsp;${bean.name}</a>
											</c:when>
											<c:otherwise>
		[普通文件]&nbsp;${bean.name}
		</c:otherwise>
										</c:choose> &nbsp;</td>
									<td>${bean.fullRelativePath }&nbsp;</td>
									<td class="fileSize">${bean.size }&nbsp;</td>
									<td class="date">${bean.lastModified }</td>
									<td>&nbsp; <shiro:hasPermission
											name="security-resource:delete">
											<c:choose>
												<c:when test="${not bean.directory }">
													<a href="javascript:void(0);" id="${bean.path }"
														class="del">删除</a>&nbsp;
			</c:when>
											</c:choose>
										</shiro:hasPermission> <shiro:hasPermission name="security-resource:download">
											<a
												href="${ctx }/security-resource/download/?pathname=${codec:urlEncode(bean.path)}">下载</a>
										</shiro:hasPermission> &nbsp;
									</td>
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
