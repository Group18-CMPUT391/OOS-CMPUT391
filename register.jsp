<!-- 
Web page for registering a new user
-->


<!DOCTYPE html>

<%  
   String error = null;  
   String username = null;
   try{  
      error = (String) session.getAttribute("err");  
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
%>  

<html>
    <head>
        <title>Registration</title>
        <center><jsp:include page="includes/header.jsp"/></center>
    </head>
    <body>
    	<%
    	 String type = request.getParameter("usrType");
    	if (type.equals("new")){
    		
    	%>
    	  <form action="regservlet?nuser=yes" method="post" onsubmit="this">
            <center>
            <table border="1" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="2">Registration Information:</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>First Name:</td>
                        <td><input type="text" name="fname" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td>Last Name:</td>
                        <td><input type="text" name="lname" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td>Address:</td>
                        <td><input type="text" name="address" 
                        required="required" /></td>
                    </tr>
                     <tr>
                        <td>Phone Number:</td>
                        <td><input type="tel" name="phone" 
                        required="required" /></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="email" name="email" 
                        required="required" /></td>
                    </tr>
                    </tr>
                     <tr>
                        <td>Role:</td>
                        <td><select name="role">
                                <option value="a">Administrator</option>
                                <option value="d">Data Curator</option>
                                <option value="s">Scientist</option>
                            </select></td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="uname" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="pass" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><center><input type="submit" value="Submit" /> 
                        				<input type="reset" value="Reset" /></center></td>
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
            </center>
        </form>
        <%
    	}
    	else if (type.equals("existing")) {
        %>
        <form action="regservlet?nuser=no" method="post" onsubmit="this">
            <center>
            <table border="1" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="2">Registration Information for Exising User:</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Email:</td>
                        <td><input type="email" name="email" 
                        required="required" /></td>
                    </tr>
                    </tr>
                     <tr>
                        <td>Role:</td>
                        <td><select name="role">
                                <option value="a">Administrator</option>
                                <option value="d">Data Curator</option>
                                <option value="s">Scientist</option>
                            </select></td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="uname" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="pass" 
                        required="required" maxlength="24"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><center><input type="submit" value="Submit" /> 
                        				<input type="reset" value="Reset" /></center></td>
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
            </center>
        </form>
        <%} %>
       
    </body>
</html>
