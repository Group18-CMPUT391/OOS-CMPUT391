<!-- 
Webpage for logging in, displays form names and servlet error response messages
-->

<!DOCTYPE html>

<%  
   String error = null;  
   String username = null;
   try{  
      error = (String) session.getAttribute("err");  
      username = (String) session.getAttribute("username");
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      response.sendRedirect("/c391proj/index.jsp");
   }
%>  

<html>
  <head>
    <title>Login Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="login" action="loginservlet" method="POST">
      <table>               
	<tbody>  
          <tr >  
            <td colspan="2"align="center">Returning user login:</td>  
          </tr>   
          <tr>  
            <td>Username:</td>  
            <td><input type="text" name="user" maxlength="24" 
		       required="required" /></td>  
          </tr>  
          <tr>  
            <td>Password:</td>  
            <td><input type="password" name="pass" maxlength="24" 
		       required="required" /></td>  
          </tr>  
          <tr >                          
            <td colspan="2" align="center"><input type="submit" 
						  value="Login" />  
              <input type="reset" value="Clear" /></td>  
          </tr>        
          <tr>  
            <td colspan="2" align="center">
	      <% 
		 if (error != null) {
		   out.println(error); 
		   session.removeAttribute("err");
		 }
	      %>
	    </td>  
	  </tr>
	</tbody> 
      </table>
    </form>
  </body>
</html>
