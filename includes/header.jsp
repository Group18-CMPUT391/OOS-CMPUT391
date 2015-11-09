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
	   out.println("Welcome, " + user.getUser_name());
	}
%>

<meta charset="utf-8">
  <title>jQuery UI Dialog - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">

<script>
	$(document).ready(function(){
	$( "#register" ).dialog({ autoOpen: false });
	$( "#click" ).click(function() {
	$( "#register" ).dialog( "open" );
	});
	});
</script>


<p>
	<div id="register" title="Registering a User">
		<a href="/oos-cmput391/new_user.jsp" >This is a New User</a> <br>
		<a href="/oos-cmput391/existing_user.jsp">This is an Existing User</a>
	</div> 
	<a href="/oos-cmput391/index.jsp">home</a> | 
	
	<%
		if (user == null) {
	        out.println("<a href=\"/oos-cmput391/login.jsp\">login</a> | ");
			out.println("<a href=\"https://github.com/Group18-CMPUT391/oos-cmput391/wiki/Self-Help-Wiki\">help</a>");
			
			return;
	    }
	
		if (user.getRole().equals("a")) {
			out.println("<a href=\"#\" id=\"click\">register</a> | ");
		} else if (user.getRole().equals("d")) {
	        out.println("<a href=\"/oos-cmput391/sensor.jsp\">upload</a> | ");
		} else if (user.getRole().equals("s")) {
	        out.println("<a href=\"/oos-cmput391/sensor.jsp\">subscribe</a> | ");
	        out.println("<a href=\"/oos-cmput391/search.jsp\">search</a> | ");
		}
		out.println("<a href=\"/oos-cmput391/account_settings.jsp\">account settings</a> | ");
	    out.println("<a href=\"/oos-cmput391/logout.jsp\">logout</a>"); 

	     /* else {
	    	if (user.getRole().equals("a")) {
	    		out.println("<a href=\"#\" id=\"click\">register</a> | ");
	    	}
	        out.println("<a href=\"/oos-cmput391/sensor.jsp\">sensor</a> | ");
			out.println("<a href=\"https://github.com/Group18-CMPUT391/oos-cmput391/wiki/Self-Help-Wiki\">help</a> | ");
	    } */
	%>
</p>





