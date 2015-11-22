<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone and password changing
-->

<%@	page import="util.Db" %>
<%@ page import="util.User" %> 
<%@	page import="util.Person" %>

<% 
	User user = null;
	Person person = null;
	Db database = new Db();
	database.connect_db();
	String status = null;
	
	String first_name = null;
	String last_name = null;
	String address = null;
	String email = null;
	String phone = null;
		
	try {
	   status = (String) session.getAttribute("status");
	   user = (User) session.getAttribute("user");
	   //username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
	   e.printStackTrace();
	}
	
	// Cannot access this page unless logged in
	if (user == null) {
	   String errormsg = "Please log in before accessing account information!";
	   session.setAttribute("status", errormsg);
	   response.sendRedirect("/oos-cmput391/login.jsp");
	   
	   return; // Important
	}
	
	// Prepopulate the fields with user information
	if (user != null) {
		person = database.getPerson(user.getPerson_id());
		first_name = person.getFirst_name();
		last_name = person.getLast_name();
		address = person.getAddress();
		email = person.getEmail();
		phone = person.getPhone();
	}
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
    	<%
    		String type = request.getParameter("updateType");
    		if (type.equals("info")){
    	%>
	    <form name="updateinfo" action="updateinfoservlet" method="POST" >
	      <table  border="1" width="30%" cellpadding="5">
		<tr>
		  <td colspan="2" align="center">Personal Information</td>
		</tr>
		<tr>
		  <td>Email:</td>
		  <td><input type="email" name="email" maxlength="128"
			     required="required"
			     value="<% if (email!= null) {
							out.write(email);
						} else {
							out.write("");
						}%>" />
		  </td>
		</tr>
		<tr>
		  <td>First Name:</td>
		  <td><input type="text" name="firstname" maxlength="24"
			     required="required"
			     value="<% if (first_name!= null) {
				    			out.write(first_name);
				    		} else {
				    			out.write("");
				    		}%>" />
		  </td>
		</tr>
		<tr>
		  <td>Last Name:</td>
		  <td><input type="text" name="lastname" maxlength="24"
			     required="required"
			     value="<% if (last_name!= null) {
				    			out.write(last_name);
				    		} else {
				    			out.write("");
				    		}%>" />
		  </td>
		</tr>
		<tr>
		  <td>Address:</td>
		  <td><input type="text" name="address" maxlength="128"
			     required="required"
			     value="<% if (address!= null) {
				    			out.write(address);
				    		} else {
				    			out.write("");
				    		}%>" />
		  </td>
		</tr>
		<tr>
		  <td>Phone:</td>
		  <td><input type="tel" name="phone" maxlength="10"
			     required="required"
			     value="<% if (phone!= null) {
				    			out.write(phone);
				    		} else {
				    			out.write("");
				    		}%>" />
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
	    <%
    		}
    		else if (type.equals("pass")){
	    %>
	    <form name="updatepass" action="updatepassservlet" method="POST" >
	      <table  border="1" width="30%" cellpadding="5">
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
	    <%} database.close_db() %>
    </center>
  </body>
</html>
