<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone and password changing
-->

<%@	page import="util.Db" %>
<%@ page import="util.User" %> 

<% 
	User user = null;
	Db database = new Db();
	database.connect_db();
		
	String status = null;
	
	String email = null;
	String firstname = null;
	String lastname = null;
	String address = null;
	String phone = null;
	String password = null;
	
	String redirectURL = null;
	
	try {
	   status = (String) session.getAttribute("status");
	   user = (User) session.getAttribute("user");
	   //username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
	   e.printStackTrace();
	}
	
	if (user != null) {
		session.setAttribute("user", user);
	}
	
	// prepopulate the fields with user information if none exist
	/*email = (String) session.getAttribute("email");
	firstname = (String) session.getAttribute("firstname");
	lastname = (String) session.getAttribute("lastname");
	address = (String) session.getAttribute("address");
	phone = (String) session.getAttribute("phone");
	if (email==null) {
	    redirectURL = "/c391proj/getUserInfo";
	}
	
	// cannot access this page unless logged in
	if (username == null) {
	   String errormsg = "Please log in before accessing account information";
	   session.setAttribute("err", errormsg);
	   redirectURL = "/c391proj/login.jsp";
	}
	
	if (redirectURL != null) {
	    response.sendRedirect(redirectURL);
	}*/
%>

<html>
  <head>
    <title>Account Settings</title>
    <center>
        <jsp:include page="includes/header.jsp"/>
    </center>
  </head>	
  <body>
    	<div style="text-align:center">
    		<div style="display:inline-block; text-align:left;">
				<a href="/oos-cmput391/change_pass.jsp">change user_name & password</a><br> 
				<a href="/oos-cmput391/change_info.jsp">change personal info</a> 
			</div>
		</div>
  </body>
</html>
