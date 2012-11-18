<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div class="nrgt">
		<form id="myForm" action="${ctx }/monitoring-record/list/"
			method="get">
			<div class="chsm">
				<span>日志管理</span>
			</div>
			<div class="czsrt">
				<span class="dist">名称：</span><input name="params[actor]"
					value="${page.params.actor}" type="text" /><input class="tjbtn"
					type="submit" value="提交" />
			</div>

			<div class="rcd_td">
				<table cellpadding="0" cellspacing="0" border="0" class="table">
					<tr>
						<th>操作人</th>
						<th>操作</th>
						<th>来源地址</th>
						<th>目标地址</th>
						<th>耗时</th>
						<th>记录时间</th>
					</tr>
					<c:choose>
						<c:when test="${not empty page.result }">
							<c:forEach items="${page.result }" var="monitoringRecord">
								<tr>
									<td>${monitoringRecord.actor}&nbsp;</td>
									<td>${monitoringRecord.action }&nbsp;</td>
									<td>${monitoringRecord.source}&nbsp;</td>
									<td>${monitoringRecord.target}&nbsp;</td>
									<td>${monitoringRecord.elapsedTime}&nbsp;毫秒</td>
									<td class="date">${monitoringRecord.createTime}&nbsp;</td>
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
