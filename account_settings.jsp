<!--
Webpage for handling account information in the persons table, such as
username, firstname, lastname, address, email, phone and password changing
-->

<!DOCTYPE html>
<%
   String error = null;
   String username = null;

   String email = null;
   String firstname = null;
   String lastname = null;
   String address = null;
   String phone = null;
   String password = null;

   String redirectURL = null;

   try {
      error = (String) session.getAttribute("err");
      username = (String) session.getAttribute("username");
   } catch (NullPointerException e) {
      e.printStackTrace();
   }
   // prepopulate the fields with user information if none exist
   email = (String) session.getAttribute("email");
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
   }
%>

<html>
  <head>
    <title>Account Settings</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="manageUser" action="manageUser" method="POST" >
      <table>
	<tr>
	  <td colspan="2" align="center">Account Information</td>
	</tr>
	<tr>
	  <td>Username:</td>
	  <td><% out.write(username);%></td>
	</tr>
	<tr>
	  <td>Email:</td>
	  <td><input type="email" name="email" maxlength="128"
		     placeholder="email"
		     required="required"
		     value="<% if (email!= null) {
			out.write(email);
			} else {
			out.write("");}%>" />
	  </td>
	</tr>
	<tr>
	  <td>First Name:</td>
	  <td><input type="text" name="firstname" maxlength="24"
		     placeholder="first name"
		     required="required"
		     value="<% if (firstname!= null) {
			    out.write(firstname);
			    } else {
			    out.write("");}%>" />
	  </td>
	</tr>
	<tr>
	  <td>Last Name:</td>
	  <td><input type="text" name="lastname" maxlength="24"
		     placeholder="last name"
		     required="required"
		     value="<% if (lastname!= null) {
			    out.write(lastname);
			    } else {
			    out.write("");}%>" />
	  </td>
	</tr>
	<tr>
	  <td>Address:</td>
	  <td><input type="text" name="address" maxlength="128"
		     placeholder="address"
		     required="required"
		     value="<% if (address!= null) {
			    out.write(address);
			    } else {
			    out.write("");}%>" />
	  </td>
	</tr>
	<tr>
	  <td>Phone:</td>
	  <td><input type="tel" name="phone" maxlength="10"
		     placeholder="phone"
		     required="required"
		     value="<% if (phone!= null) {
			    out.write(phone);
			    } else {
			    out.write("");}%>" />
	  </td>
	</tr>
	<tr>
	  <td>New Password:</td>
	  <td><input type="password" name="pass" maxlength="24"
		     placeholder="new password" />
	  </td>
	</tr>
	<tr>
	  <td>Confrim New Password:</td>
	  <td><input type="password" name="pass2" maxlength="24"
		     placeholder="confirm password" />
	  </td>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	    <input type="submit" value="Send Changes" />
	  </td>
	</tr>
	<tr>
	  <td colspan="2" align="center">
	    <%
	       if (error != null) {
	          out.write(error);
	          session.removeAttribute("err");
	       }
	       %> 
	  </td>
	</tr>
      </table>
    </form>
  </body>
</head>
