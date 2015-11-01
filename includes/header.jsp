<!--
Assume necessary html definitions are already made
-->
<%@ page import="javax.servlet.http.*" %>
<% 
   String username = (String) session.getAttribute("username");
   if (username != null) {
      out.println("Welcome, " + username);
   }
%>
<p>
<a href="/oos-cmput391/index.jsp">home</a> | 
<%
    if (username == null) {
	out.println("<a href=\"/oos-cmput391/register.jsp\">register</a>");
        out.println(" | <a href=\"/oos-cmput391/login.jsp\">login</a>");
	out.println(" | <a href=\"/oos-cmput391//help.jsp\">help</a>");
    } else {
        out.println("<a href=\"/oos-cmput391/search.jsp\">search</a>");
        out.println(" | <a href=\"/oos-cmput391/manage_Groups\">manage groups</a>");
	out.println(" | <a href=\"/oos-cmput391/uploadimage.jsp\">upload photo</a>");
	out.println(" | <a href=\"/oos-cmput391/uploadFolder.jsp\">upload folder</a>");
	out.println(" | <a href=\"/oos-cmput391/choosePhotos.jsp\">browse photos</a>");
	out.println(" | <a href=\"/oos-cmput391/account_settings.jsp\">account settings</a>");
	out.println(" | <a href=\"/oos-cmput391/help.jsp\">help</a>");
        out.println(" | <a href=\"/oos-cmput391/logout.jsp\">logout</a>"); 
	if (username.equals("admin")) {
	   out.println(" | <a href=\"/oos-cmput391/adminStats\">admin stats</a>");
	}
    }
%>
</p>
