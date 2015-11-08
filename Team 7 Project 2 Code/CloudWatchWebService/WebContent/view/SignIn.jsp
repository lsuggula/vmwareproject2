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
	String username;
	email=(email==null?"":email);
	password=(password==null?"":password);
	
	String header="Cloud Watch";
	boolean updated=false;
	
	if(!(email+password).isEmpty()) {
		
		if(email==""||password=="") {
			header="Fields cannot be left blank!";
		} else {
			Services db = Services.getInstance();
			username=db.signIn(email, password);
			if(username.isEmpty())
			{
				header="Incorrect username or password!";
				email="";
				password="";
			}
			else
			{	
				session.setAttribute("email", email);
				session.setAttribute("userName", username);
				response.sendRedirect("/Cloud7VM/view/UserProfile.jsp?username="+username);
			}
		}
			
	}	
%>



  <div class="wrapper">
	<div class="container">
		<h1><%=header %></h1>

		<form class="form" action="SignIn.jsp">
			<input type="text" placeholder="Email" name="email" value="<%=email %>"/>
			<input type="password" placeholder="Password" name="pass" value="<%=password %>"/>
			<button type="submit" id="login-button">Login</button><br><br>
		<button type="button" onclick="window.location.href='SignUp.jsp'" id="login-button">Sign Up</button>			
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
	</ul>
</div>

 

</body>

</html>