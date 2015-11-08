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
                     <li class="active"><a href="Kibana.jsp">Monitoring</a></li>
                     <li ><a href="Connect.jsp">Connect</a></li>
                 </ul>
                 
             </div><!--/.nav-collapse -->
         </div><!--/.container-fluid -->
     </nav>
			
 <div class="jumbotron">
			
			<iframe src="http://10.189.116.50:5601/?embed&#/dashboard/test1-logstash?_g=(refreshInterval:(display:'5%20minutes',section:2,value:300000),time:(from:now-1h,mode:quick,to:now))&_a=(filters:!(),panels:!((col:1,id:test1-logstash-avg-cpu,row:1,size_x:6,size_y:2,type:visualization),(col:7,id:test1-logstash-avg-disk,row:3,size_x:6,size_y:2,type:visualization),(col:1,id:test1-logstash-avg-IO,row:5,size_x:12,size_y:2,type:visualization),(col:7,id:test1-logstash-avg-mem,row:1,size_x:6,size_y:2,type:visualization),(col:1,id:test1-logstash-avg-net,row:3,size_x:6,size_y:2,type:visualization)),query:(query_string:(analyze_wildcard:!t,query:'*')),title:test1-logstash)" height="600" width="800"></iframe>
 

            </div>
        </div>
    </body>
</html>