<%@page import="com.sjsu.team7.ws.Services"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<meta name="description" content="">
    	<meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">
            
         <title>VM Overview</title>
            <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
                
                <!-- jQuery library -->
  				<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  
			  <!-- Latest compiled JavaScript -->
			  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
			  
			  <!-- Bootstrap core CSS -->
			  <link href="../../dist/css/bootstrap.min.css" rel="stylesheet">
      
		      <!-- Custom styles for this template -->
		      <link href="navbar.css" rel="stylesheet">
                                                                                        
      		<script src="../../assets/js/ie-emulation-modes-warning.js"></script>

                                                                            
	<!-- <link rel="stylesheet" href="styles/style.css">
	<link href="styles/elements.css" rel="stylesheet">
	<script src="js/popup.js"></script> -->
	<link rel="stylesheet" type="text/css" href="styles/main.css">
</head>
    

<body>
<!-- Nav Bar and header -->     
<%
String vmId = request.getParameter("vm_id");
%>
<div class="container">
    <div class="header"><h3><b style="align:center">VM Overview</b></h3></div>
     <!-- Static navbar -->
     <nav class="navbar navbar-default">
         <div class="container-fluid">
             
             <div id="navbar" class="navbar-collapse collapse">
                 <ul class="nav navbar-nav">
                     <li class="active"><a href="VMProfile.jsp?vm_id=<%=vmId%>">Description</a></li>
                     <li ><a href="Alarms.jsp?vm_id=<%=vmId%>">Status Check</a></li>
                     <li><a href="Kibana.jsp?vm_id=<%=vmId%>">Monitoring</a></li>
                     <li><a href="Connect.jsp?vm_id=<%=vmId%>">Connect</a></li>
                     <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">User<span class="caret"></span></a>
                         <ul class="dropdown-menu" role="menu">
                             <li><a href="UserProfile.jsp">Profile</a></li>
                             <li><a href="Logout.jsp">Logout</a></li>
                         </ul>
                     </li>
                 </ul>
                 
             </div><!--/.nav-collapse -->
         </div><!--/.container-fluid -->
     </nav>
			
 <div class="jumbotron" style="height:450px;">

<%
	Services db = Services.getInstance();
	HashMap<String,String> vmDetails = db.getVMInfo(vmId);

%>
<div style="float:left;">
<table class="query-table">
<tr colspan="2"><th>General Details</th></tr>
<tr><td>VM Name</td><td><%=vmId %></td></tr>
<tr><td>Guest OS</td><td><%=vmDetails.get("GuestOs") %></td></tr>
<tr><td>VM Version</td><td><%=vmDetails.get("VMVersion") %></td></tr>
<tr><td>CPU</td><td><%=vmDetails.get("CPU") %></td></tr>
<tr><td>Memory</td><td><%=vmDetails.get("Memory") %></td></tr>
<tr><td>Memory Overhead</td><td><%=String.valueOf(Long.parseLong(vmDetails.get("MemOverhead"))/1048576)+" MB" %></td></tr>
<tr><td>IP Address</td><td><%=vmDetails.get("IPAddress") %></td></tr>
<tr><td>DNS Name</td><td><%=vmDetails.get("DNSName") %></td></tr>
<tr><td>State</td><td><%=vmDetails.get("State") %></td></tr>
<tr><td>Host</td><td><%=vmDetails.get("Host") %></td></tr>
</table>
</div>

<div style="float:right;">
<table class="query-table" >
<tr colspan="2"><th>Resource Details</th></tr>
<tr><td>Consumed Host Memory</td><td><%=vmDetails.get("MaxMemoryUsage") %></td></tr>
<tr><td>Consumed Host CPU</td><td><%=vmDetails.get("MaxCPUUsage") %></td></tr>
<tr><td>Connection State</td><td><%=vmDetails.get("ConnectionState") %></td></tr>
<tr><td>Boot Time</td><td><%=vmDetails.get("BootTime") %></td></tr>
</table>
</div>
            </div>
        </div>
    </body>
</html>