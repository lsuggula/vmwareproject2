<%@page import="com.sjsu.team7.ws.Services"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete user</title>
</head>
<body>
<% 
	session.invalidate();
	String userId = request.getParameter("userId");
	response.sendRedirect("/Cloud7VM/view/SignIn.jsp");
%>
</body>
</html>