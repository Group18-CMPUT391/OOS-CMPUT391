<!-- 
Webpage for logging in, displays form names and servlet error response messages
-->

<%  
   String status = null;  
   String username = null;
   try{  
      status = (String) session.getAttribute("status");  
      username = (String) session.getAttribute("username");
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      response.sendRedirect("/oos-cmput391/index.jsp");
   }
%>  

<html>
  <head>
    <title>Login Page</title>
    <center>
    	<jsp:include page="includes/header.jsp"/>
    </center>
  </head>
  <body>
    <form name="login" action="loginservlet" method="POST">
    	<center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Returning User Login:</th>
                    </tr>
                </thead>
                <tbody>
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
                    <tr>
                        <td colspan="2"><center><input type="submit" value="Login" />&nbsp;<input type="reset" value="Reset" /></center></td>
                    </tr>
                    <tr>
                    	<td colspan="2" align="center">
							<% 
								if (status != null) {
								  out.println(status); 
								  session.removeAttribute("status");
								}
							%>
	    				</td>
		    		</tr>
                </tbody>
            </table>
            </center>
        </form>
  </body>
  <footer>
<center><jsp:include page="includes/footer.jsp"/></center>
</footer> 
</html>
