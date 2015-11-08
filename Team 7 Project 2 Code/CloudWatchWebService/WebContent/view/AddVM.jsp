<%@page import="com.sjsu.team7.ws.Services"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add VM</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
</head>
<body>
<div style="height:50px;">
<img align="left" src="images/logo.png"/>
<div id="auth">
<% 
	String userName,emailId;
	userName = (String) session.getAttribute("userName");
	emailId = (String) session.getAttribute("userId");
	
	if(userName!=null) {
%>

<h4 align="right"> <span>Welcome <a href="UserProfile.jsp?username=<%=userName %>"><%=userName %></a></span><button type="button" onclick="window.location.href='Logout.jsp'"> Logout </button> </h4>
<%} else { %>
<h4 align="right"> <button type="button" onclick="window.location.href='SignUp.jsp'"> Sign Up</button> <button type="button" onclick="window.location.href='SignIn.jsp'"> Sign In</button> </h4>
<%} %>
</div>
</div>
<br>
<!-- <div style="height:20px; text-align:left; background-color:#D3D3D3;" >&nbsp; &nbsp;<a href="AddVM.jsp"> Add VM </a></div><br> -->
<div>
<%	String vmName = request.getParameter("vmName");
	String os = request.getParameter("os");
		
	vmName=(vmName==null?"":vmName);
	os=(os==null?"":os);
			
	String header="Enter VM details!";
		
	if(!(vmName+os).isEmpty()) {
		
		if(vmName==""||os=="") {
			header="Fields cannot be left blank!";
		} else {
			Services db = Services.getInstance();
			
			db.addVM(vmName, os, emailId);
			
			header="VM will be added shortly! Check your profile page in 10 minutes.";
		}
			
	}
	
%>
<br><br>
<h1><span class='color_h1'><%=header%></span></h1>
<form id="add-business" method="post" action="AddVM.jsp">
<table class="form-table">
	<tr><td>VM Name</td><td><input type="text" name="vmName" value="<%=vmName %>"/></td></tr>
	<tr><td>OS </td><td> <select name="os"><option value="ubuntu">Ubuntu 32bit</option><option value="windows">Windows</option></select></td></tr>
	<!-- <tr><td>Type </td><td> <input type="text" name="businessType" value="<%=os %>"/></td></tr> 
	<tr><td>Description </td><td> <textarea id="description" name="description"><%=os %></textarea></td></tr>-->
	<tr><td></td><td><input class="btn" type="submit" value="submit"></td>
</table>

</form>

</div>
</body>
</html>