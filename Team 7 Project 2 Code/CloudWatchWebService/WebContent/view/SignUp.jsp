<%@page import="com.sjsu.team7.ws.Services"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta charset="UTF-8">

  <title>Cloud Watch</title>

    <link rel="stylesheet" href="styles/login-style.css">
 <script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>

  <script src="js/index.js"></script>
</head>

<body>

<%	String email = request.getParameter("email");
	String password = request.getParameter("pass");
	String first_name = request.getParameter("fname");
	String last_name = request.getParameter("lname");
	
	email=(email==null?"":email);
	password=(password==null?"":password);
	first_name=(first_name==null?"":first_name);
	last_name=(last_name==null?"":last_name);
	
	String header="Enter your details!";
	boolean updated=false;
	
	if(!(email+password+first_name+last_name).isEmpty()) {
		
		if(email==""||password==""||first_name==""||last_name=="") {
			header="Fields cannot be left blank!";
		} else {
			Services db = Services.getInstance();
			
			if(db.signUp(email, password, first_name, last_name).equals("success"))
			{
				header="SignUp successful! Sign In to continue.";
				updated=true;
			}
			else
			{
				header="User already exists!";
			}
		}
			
	}
	
%>


  <div class="wrapper">
	<div class="container">
		<h1><%=header %></h1>

		<form class="form" action="SignUp.jsp">
			<input type="text" placeholder="Email" name="email" value="<%=email %>"/>
			<input type="password" placeholder="Password" name="pass" value="<%=password %>"/>
			<input type="text" placeholder="First Name" name="fname" value="<%=first_name %>"/>
			<input type="text" placeholder="Last Name" name="lname" value="<%=last_name %>"/>
			<button type="submit" id="login-button">Create Account</button><br><br>
		<button type="button" onclick="window.location.href='SignIn.jsp'" id="login-button">Sign In</button>			
		</form>
		
		
	</div>

	<ul class="bg-bubbles">
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
</div>

 

</body>

</html>