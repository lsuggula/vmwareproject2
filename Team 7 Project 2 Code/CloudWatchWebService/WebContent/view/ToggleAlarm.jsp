<%@page import="com.sjsu.team7.ws.Services"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VM Alarm Button</title>
</head>
<body>
<% 
	Services db = Services.getInstance();
	String email = session.getAttribute("email").toString();
	String vmId = request.getParameter("vm_id");
	String alarm_name = request.getParameter("alarm_name");
	String status = request.getParameter("status");
	
	if(status.equals("on"))
		db.toggleAlarm(email, vmId, alarm_name, "off");
	else
		db.toggleAlarm(email, vmId, alarm_name, "on");
	
	response.sendRedirect("/Cloud7VM/view/Alarms.jsp?vm_id="+vmId);
%>
</body>
</html>