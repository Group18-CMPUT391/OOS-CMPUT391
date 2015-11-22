<!--
Assume necessary html definitions are already made
-->

<%@ page import="javax.servlet.http.*" %>
<%@ page import="util.User" %>

<% 
	User user = null;
	try {
		user = (User) session.getAttribute("user");
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	
	if (user != null) {
		String role = null;
		if (user.getRole().equals("a")) {
			role = "admin";
		} else if (user.getRole().equals("d")) {
			role = "data curator"; 
		} else if (user.getRole().equals("s")) {
			role = "scientist";
		}
		
		out.println("Welcome " + role + ", " + user.getUser_name());	
	}
%>

<meta charset="utf-8">
  <title>jQuery UI Dialog - Default functionality</title>
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/start/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">

<script>
	$(document).ready(function(){
		$( "#register" ).dialog({ height:'auto', 
								  width:'auto',		
								  autoOpen: false });
		$( "#regclick" ).click(function() {
			$( "#register" ).dialog( "open" );
		});
		$( "#account" ).dialog({ height:'auto', 
			  					 width:'auto',		
			 	 				 autoOpen: false });
		$( "#accountclick" ).click(function() {
			$( "#account" ).dialog( "open" );
		});
	});
</script>


<p>
	<div id="register" title="Administrator">
		<a href="/oos-cmput391/register.jsp?usrType=new">This is a New User</a> <br>
		<a href="/oos-cmput391/register.jsp?usrType=existing">This is an Existing User</a> <br>
		<a href="/oos-cmput391/sensor.jsp">Manage Sensors</a>
	</div> 
	<div id="account" title="Change User Information">
		<a href="/oos-cmput391/change_info.jsp?updateType=pass" >Change Username and Password</a> <br>
		<a href="/oos-cmput391/change_info.jsp?updateType=info">Change Personal Information</a>
		
	</div> 
	
	
	<a href="/oos-cmput391/index.jsp">Home</a> | 
	
	<%
		if (user == null) {
	        out.println("<a href=\"/oos-cmput391/login.jsp\" class = \"btn btn-primary\">login</a> | ");
			out.println("<a href=\"/oos-cmput391/README.md.html\">help</a>");
			
			return;
	    	}
	
		if (user.getRole().equals("a")) {
			out.println("<a href=\"#\" id=\"regclick\">User and Sensor Management</a> | ");
			out.println("<a href=\"/oos-cmput391/admin_delete.jsp\">Admin Delete</a> | ");
		} else if (user.getRole().equals("d")) {
	        out.println("<a href=\"/oos-cmput391/sensor.jsp\">Upload</a> | ");
		} else if (user.getRole().equals("s")) {
	        out.println("<a href=\"/oos-cmput391/sensor.jsp\">Sensor</a> | ");
	        out.println("<a href=\"/oos-cmput391/subscription.jsp\">Subscribe</a> | ");
	        out.println("<a href=\"/oos-cmput391/search.jsp\">Search</a> | ");	
		out.println("<a href=\"/oos-cmput391/data_analysis.jsp\">Generate Report</a> | ");
		}

		out.println("<a href=\"#\" id=\"accountclick\">Account Settings</a> | ");
		out.println("<a href=\"/oos-cmput391/logout.jsp\">Logout</a>"); 
	%>
</p>





