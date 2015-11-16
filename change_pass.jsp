<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone and password changing
-->

<%@ page import="util.User" %> 

<% 
	User user = null;
	String status = null;
		
	try {
	   status = (String) session.getAttribute("status");
	   user = (User) session.getAttribute("user");
	   //username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
	   e.printStackTrace();
	}
	
	// Cannot access this page unless logged in
	if (user == null) {
	   String errormsg = "Please log in before accessing account information";
	   session.setAttribute("status", errormsg);
	   response.sendRedirect("/oos-cmput391/login.jsp");
	   
	   return;
	}
	
	/* if (redirectURL != null) {
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
    <center>
	    <form name="updatepass" action="updatepassservlet" method="POST" >
	      <table>
		<tr>
		  <td colspan="2" align="center">Account Information</td>
		</tr>
		<tr>
		  <td>User_name:</td>
		  <td>
		  	<input type="username" name="username" value=<%out.write(user.getUser_name());%> />
		  </td>
		</tr>
		<tr>
		  <td>New Password:</td>
		  <td>
		  	<input type="password" name="pass" maxlength="24"
			     placeholder="new password" />
		  </td>
		</tr>
		<tr>
		  <td>Confirm New Password:</td>
		  <td>
		  	<input type="password" name="pass2" maxlength="24"
			     placeholder="confirm password" />
		  </td>
		</tr>
		<tr>
		  <td colspan="2" align="center">
		    <input type="submit" value="Save changes" />
		  </td>
		</tr>
		<tr>
		  <td colspan="2" align="center">
		    <%
		       if (status != null) {
		          out.write(status);
		          session.removeAttribute("status");
		       }
		       %> 
		  </td>
		</tr>
	      </table>
	    </form>
    </center>
  </body>
</html>
