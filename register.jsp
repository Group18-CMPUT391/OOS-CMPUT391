<!--
Webpage for registering a new user. Only displays if not logged in.
-->

<!DOCTYPE html>

<%
   String error = null;
   String username = null;
   try {
      error = (String) session.getAttribute("err");
      username = (String) session.getAttribute("username");
   } catch (NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      response.sendRedirect("/c391proj/index.jsp");
   }
%>

<html>
  <head>
    <title>Register Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="register" action="registerservlet" method="POST">
      <table>
	<tbody>
	  <tr>
	    <td colspan="2" align="center"> New user registration: </td>
	  </tr>
	  <tr>
	    <td>New Username:</td>
	    <td> 
	      <input type="text" name="user" maxlength="24" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Email:</td>
	    <td>
	      <input type="email" name="email" maxlength="128" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New First Name:</td>
	    <td>
	      <input type="text" name="firstname" maxlength="128" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Last Name:</td>
	    <td>
	      <input type="text" name="lastname" maxlength="128" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Address:</td>
	    <td>
	      <input type="text" name="address" maxlength="128" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Phone:</td>
	    <td>
	      <input type="tel" name="phone" maxlength="128" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>New Password:</td>
	    <td> 
	      <input type="password" name="pass" maxlength="24" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td>Confirm Password:</td>
	    <td> 
	      <input type="password" name="pass2" maxlength="24" 
		     required="required" />
	    </td>
	  </tr>
	  <tr>
	    <td colspan="2" align="center">
	      <input type="submit" value="Register" />
	      <input type="reset" value="Clear" />
	    </td>
	  </tr>
	  <tr>
	    <td colspan="2" align="center">
	      <%
		 if (error != null) {
		   out.println(error);
		   session.setAttribute("err", null);
		 }
	      %>
	    </td>
	  </tr>
	</tbody>
      </table>
    </form>
  </body>
</html>
