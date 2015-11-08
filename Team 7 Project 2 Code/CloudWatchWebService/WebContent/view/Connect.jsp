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

<div class="container">
    <div class="header"><h3><b style="align:center">VM Overview</b></h3></div>
     <!-- Static navbar -->
     <nav class="navbar navbar-default">
         <div class="container-fluid">
             
             <div id="navbar" class="navbar-collapse collapse">
                 <ul class="nav navbar-nav">
                     <li ><a href="VMProfile.jsp">Description</a></li>
                     <li ><a href="Alarms.jsp">Status Check</a></li>
                     <li ><a href="Kibana.jsp">Monitoring</a></li>
                     <li class="active"><a href="Connect.jsp">Connect</a></li>
                 </ul>
                 
             </div><!--/.nav-collapse -->
         </div><!--/.container-fluid -->
     </nav>
			
 <div class="jumbotron">
			
 <!-- Popup Div Starts Here -->
<div id="abc">
<div id="popupContact" style="box-sizing: content-box;">
<!-- Contact Us Form -->
<form action="#" id="form" method="post" name="form">
<img id="close" src="images/3.png" onclick ="div_hide()">
<h2>Create Alarm</h2>
<hr>
<input id="name" name="name" placeholder="Name" type="text">
When
<select>
  <option value="cpu" selected="selected">CPU Usage</option>
  <option value="mem">Memory Usage</option>
  <option value="disk">Disk Usage</option>
</select>
is
<select>
  <option value="gt" selected="selected">></option>
  <option value="gte">>=</option>
  <option value="lt"><</option>
  <option value="lte"><=</option>
</select>
<input id="value" name="value" placeholder="Value" type="text">
<br><br>
<a href="javascript:%20check_empty()" id="submit">Create</a>
</form>
</div>
</div>
<!-- Popup Div Ends Here -->




<table class="flatTable">
  <tr class="titleTr">
    <td class="titleTd">VM Alarms</td>
    <td colspan="3"></td>
    <td><button onclick="div_show()" id="submit" class="plusTd button"></button></td>
  </tr>
  <tr class="headingTr">
    <td>Name</td>
    <td>Type</td>
    <td>Condition</td>
    <td>Switch</td>
    <td>Delete</td>
  </tr>

  <tr>
    <td>#2331212</td>
    <td>Feb 21,2013</td>
    <td>White Out</td>
    <td><div class="onoffswitch">
    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch1">
    <label class="onoffswitch-label" for="myonoffswitch1">
        <span class="onoffswitch-inner"></span>
        <span class="onoffswitch-switch"></span>
    </label>
</div> </td>
    <td class="controlTd">
      <div class="settingsIcons">
      
      </div>  
    </td>
  </tr>


</table>
            </div>
        </div>
    </body>
</html>