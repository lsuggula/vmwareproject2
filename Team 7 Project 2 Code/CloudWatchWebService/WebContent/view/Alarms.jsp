<%@page import="java.util.ArrayList"%>
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

                                                                            
	<link rel="stylesheet" href="styles/style.css">
	<link href="styles/elements.css" rel="stylesheet">
	<script src="js/popup.js"></script>
</head>
    

<body>
<!-- Nav Bar and header -->     
<%
String vmId = request.getParameter("vm_id");
String email =session.getAttribute("email").toString() ; 
%>
<div class="container">
    <div class="header"><h3><b style="align:center">VM Alarms</b></h3></div>
     <!-- Static navbar -->
     <nav class="navbar navbar-default">
         <div class="container-fluid">
             
             <div id="navbar" class="navbar-collapse collapse">
                 <ul class="nav navbar-nav">
                     <li ><a href="VMProfile.jsp?vm_id=<%=vmId%>">Description</a></li>
                     <li class="active"><a href="Alarms.jsp?vm_id=<%=vmId%>">Status Check</a></li>
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


 <div class="jumbotron">
 
 <%	
	String alarm_name = request.getParameter("name");
	String alarm_type = request.getParameter("type");
	String alarm_condition = request.getParameter("condition");
	String alarm_value = request.getParameter("value");
	
	alarm_name=(alarm_name==null?"":alarm_name);
	alarm_type=(alarm_type==null?"":alarm_type);
	alarm_condition=(alarm_condition==null?"":alarm_condition);
	alarm_value=(alarm_value==null?"":alarm_value);
		
	String header="Alarm";
	boolean updated=false;
	
	if(!(alarm_name+alarm_type+alarm_condition+alarm_value).isEmpty()) {
		
		if(alarm_name==""||alarm_type==""||alarm_condition==""||alarm_value=="") {
			header="Fields cannot be left blank!";
		} else {
			Services db = Services.getInstance();
			
			if(db.createAlarm(email, vmId, alarm_name, alarm_type, Integer.parseInt(alarm_value), alarm_condition).equals("success"))
			{
				header="Alarm created successfully!";
				updated=true;
			}
			else
			{
				header="Alarm already exists!";
			}
		}%>
		<script>alert(header);</script>
			
	<%}
	
%>
 	
 <!-- Popup Div Starts Here -->
<div id="abc">
<div id="popupContact" style="box-sizing: content-box;">

<!-- Contact Us Form -->
<form action="Alarms.jsp?vm_id=<%=vmId%>" id="form" method="post" name="form">
<img id="close" src="images/close.png" onclick ="div_hide()">
<h2>Create Alarm</h2>
<hr>
<input id="name" name="name" placeholder="Name" type="text">
When
<select id="type" name="type">
  <option value="cpu" selected="selected">CPU Usage</option>
  <option value="mem">Memory Usage</option>
  <option value="disk">Disk Usage</option>
</select>
is
<select id="condition" name="condition">
  <option value="gt" selected="selected">></option>
  <!-- <option value="gte">>=</option> <option value="lte"><=</option>-->
  <option value="lt"><</option>
  
</select>
<input id="value" name="value" placeholder="Value" type="text">
<br><br>
<button id="submit">Create</button>
</form>

</div>
</div>
<!-- Popup Div Ends Here -->




<table class="flatTable">
  <tr class="titleTr">
    <td class="titleTd">VM Alarms</td>
    <td colspan="4"></td>
    <td><button onclick="div_show()" id="submit" class="plusTd button"></button></td>
  </tr>
  <tr class="headingTr">
    <td>Name</td>
    <td>Type</td>
    <td>Condition</td>
    <td>Switch</td>
    <td>Trigger</td>
    <td>Delete</td>
  </tr>

<%
	Services db = Services.getInstance();
	ArrayList<HashMap<String,String>> alarmData = db.getAlarms(email, vmId);
	int i;
	if(alarmData!=null)
	{
		for( i=0; i<alarmData.size() ; i++)
		{
%>

  <tr>
    <td><%=alarmData.get(i).get("alarm_name") %></td>
    <td><%=alarmData.get(i).get("alarm_type") %></td>
    <td><%= alarmData.get(i).get("condition") %></td>
    <td>
    <!-- <div class="onoffswitch">
    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch1" <%=alarmData.get(i).get("status").equals("on")?"checked":"blas" %>>
    <a href="Alarms.jsp?vm_id=<%=vmId %>">
    <label class="onoffswitch-label" for="myonoffswitch1">
        <span class="onoffswitch-inner"></span>
        <span class="onoffswitch-switch"></span>
    </label>
    </a></div>  -->
    <button onclick="window.location.href='ToggleAlarm.jsp?vm_id=<%=vmId %>&alarm_name=<%=alarmData.get(i).get("alarm_name")%>&status=<%=alarmData.get(i).get("status")%>'" ><%=alarmData.get(i).get("status")%></button> 
    </td>
    
<td><%=alarmData.get(i).get("triggered") %></td>
    <td >
    <a href="DeleteAlarm.jsp?vm_id=<%=vmId %>&alarm_name=<%=alarmData.get(i).get("alarm_name") %>"><img src="styles/delete.png" /></a>
    </td>
  </tr>

<%}} %>

</table>
            </div>
        </div>
    </body>
</html>