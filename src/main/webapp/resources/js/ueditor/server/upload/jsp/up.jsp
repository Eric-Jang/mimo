<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	out.clear();
	out = pageContext.pushBody();
	request.getRequestDispatcher("/photo-resource/upload/").forward(request, response);
%>
