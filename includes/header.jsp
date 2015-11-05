<!--
Assume necessary html definitions are already made
-->

<%@ page import="javax.servlet.http.*" %>
<%@ page import="util.User" %>

<% 
   String username = (String) session.getAttribute("username");
   if (username != null) {
      out.println("Welcome, " + username);
   }
%>
<meta charset="utf-8">
  <title>jQuery UI Dialog - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="https://github.com/Group18-CMPUT391/oos-cmput391/wiki/Self-Help-Wiki"></script>
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
<a href="/oos-cmput391/existing_user.jsp">This is an Existing User</a></div>
<a href="/oos-cmput391/index.jsp">home</a> | 
<%
    if (username == null) {
	out.println("<a href=\"#\" id=\"click\">register</a>");
        out.println(" | <a href=\"/oos-cmput391/login.jsp\">login</a>");
	out.println(" | <a href=\"/oos-cmput391//help.jsp\">help</a>");
    } else {
        out.println("<a href=\"/oos-cmput391/search.jsp\">search</a>");
        out.println(" | <a href=\"/oos-cmput391/manage_Groups\">manage groups</a>");
	out.println(" | <a href=\"/oos-cmput391/uploadimage.jsp\">upload photo</a>");
	out.println(" | <a href=\"/oos-cmput391/uploadFolder.jsp\">upload folder</a>");
	out.println(" | <a href=\"/oos-cmput391/choosePhotos.jsp\">browse photos</a>");
	out.println(" | <a href=\"/oos-cmput391/account_settings.jsp\">account settings</a>");
	out.println(" | <a href=\"https://github.com/Group18-CMPUT391/oos-cmput391/wiki/Self-Help-Wiki">help</a>");
        out.println(" | <a href=\"/oos-cmput391/logout.jsp\">logout</a>"); 
	if (username.equals("admin")) {
	   out.println(" | <a href=\"/oos-cmput391/adminStats\">admin stats</a>");
	}
    }
%>
</p>





