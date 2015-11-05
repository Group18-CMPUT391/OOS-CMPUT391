<!--
Assume necessary html definitions are already made
-->

<%@ page import="javax.servlet.http.*" %>
<%@ page import="util.User" %>

<% 
	//User user = (User) session.getAttribute("user");
	String username = (String) session.getAttribute("username");
	if (username != null) {
		out.println("Welcome, " + username);
		//out.println("Role: " + role);
	}
%>

<script>
	function popup(url) {
		window.open(url,'name','height=100,width=200');
		if (window.focus) {newwindow.focus()}
		return false;
	}
</script>

<p>
<a href="/oos-cmput391/index.jsp">home</a> | 
<%
	if (username == null) {
		out.println("<a href=\"/oos-cmput391/register.jsp\" target=\"popup\" onclick=\"popup(this.href='/oos-cmput391/register.jsp' );return false;\">register</a>");
		out.println(" | <a href=\"/oos-cmput391/login.jsp\">login</a>");
		out.println(" | <a href=\"/oos-cmput391//help.jsp\">help</a>");
	} else {
		out.println("<a href=\"/oos-cmput391/search.jsp\">search</a>");
		out.println(" | <a href=\"/oos-cmput391/upload.jsp\">upload</a>");
		out.println(" | <a href=\"/oos-cmput391/subscribe.jsp\">subscribe</a>");
		out.println(" | <a href=\"/oos-cmput391/account_settings.jsp\">account settings</a>");
		//out.println(" | <a href=\"/oos-cmput391/help.jsp\">help</a>");
		out.println(" | <a href=\"/oos-cmput391/logout.jsp\">logout</a>"); 
		/* if (user.getRole().equals("a")) {
			out.println(" | <a href=\"/oos-cmput391/adminStats\">admin stats</a>");
		} */
	} 
%>
</p>
