<!--
Assume necessary html definitions are already made
Author: Alexander Wong
-->
<%@ page import="javax.servlet.http.*" %>
<% 
   String username = (String) session.getAttribute("username");
   if (username != null) {
      out.println("Welcome, " + username);
   }
%>
<p>
<a href="/c391proj/index.jsp">home</a> | 
<%
    if (username == null) {
	out.println("<a href=\"/c391proj/register.jsp\">register</a>");
        out.println(" | <a href=\"/c391proj/login.jsp\">login</a>");
	out.println(" | <a href=\"/c391proj/help.jsp\">help</a>");
    } else {
        out.println("<a href=\"/c391proj/search.jsp\">search</a>");
        out.println(" | <a href=\"/c391proj/manage_Groups\">manage groups</a>");
	out.println(" | <a href=\"/c391proj/uploadimage.jsp\">upload photo</a>");
	out.println(" | <a href=\"/c391proj/uploadFolder.jsp\">upload folder</a>");
	out.println(" | <a href=\"/c391proj/choosePhotos.jsp\">browse photos</a>");
	out.println(" | <a href=\"/c391proj/account_settings.jsp\">account settings</a>");
	out.println(" | <a href=\"/c391proj/help.jsp\">help</a>");
        out.println(" | <a href=\"/c391proj/logout.jsp\">logout</a>"); 
	if (username.equals("admin")) {
	   out.println(" | <a href=\"/c391proj/adminStats\">admin stats</a>");
	}
    }
%>
</p>
