<!-- 
Web page for registering a new user
-->


<!DOCTYPE html>
<%@ page import="util.User" %> 

<%  
	   String error = null;  
	   String username = null;
	   User user = null;
	   try{  
		   user = (User) session.getAttribute("user");
	       error = (String) session.getAttribute("err");  
	       
	        // Cannot access this page unless logged in
			if (user == null) {
			   String errormsg = "Please log in before accessing account information!";
			   session.setAttribute("status", errormsg);
			   response.sendRedirect("/oos-cmput391/login.jsp");
			}
	   } catch(NullPointerException e) {
	      e.printStackTrace();
	   }
	   
	   Db db = new Db();
	   db.connect_db();
%>  
<%@ page import="util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@page import="java.util.*" %>
<html>
    <head>
        <title>Registration</title>
        <center><jsp:include page="includes/header.jsp"/></center>
        <script type="text/javascript">
			$(document).ready(function(){
				$( "#deleteSub" ).submit(function (e) {
			        //check atleat 1 checkbox is checked
			        if (!$('.select').is(':checked')) {
			            //prevent the default form submit if it is not checked
			            e.preventDefault();
			        }
			    })
			});
		</script>
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
                        <td><input type="number" name="phone" 
                        required="required" minlength="10" maxlength="10"/></td>
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
                        <th colspan="2">Registration Information for Existing User:</th>
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
        <%}
    	  else if (type.equals("delete")) {%>
    	  		<center><form action="deleteservlet?type=deleteUser" method="post"
					id="deleteSub" onsubmit="this">
					<table width="59%" border="1">
							<thead><tr><th colspan="6">Manage User</th></tr></thead>
						<tr>	
							<th></th>
							<th>User Name</th>
							<th>Password</th>
							<th>Role</th>
							<th>Person ID</th>
							<th>Date Registered</th>
		
						</tr>
						<%
						ArrayList<User> result_set=db.printUsers();
						for(int i=0;i<result_set.size();++i){
							out.println("<tr>"); %>
						<td><input type="checkbox" class="select" name="usercheckbox" value=<%=String.valueOf(result_set.get(i).getPerson_id())%> /></td>
							<%
									
							out.println("<td>"+String.valueOf(result_set.get(i).getUser_name())+"</td>");
												
							out.println("<td>"+String.valueOf(result_set.get(i).getPassword())+"</td>");
												
							out.println("<td>"+String.valueOf(result_set.get(i).getRole())+"</td>");
					
							out.println("<td>"+String.valueOf(result_set.get(i).getPerson_id())+"</td>");					
							out.println("<td>"+String.valueOf(result_set.get(i).getDate_registered())+"</td>");
							out.println("</tr>");
						} 
							
				 		if (error != null) {
		   					out.println(error); 
		   					session.removeAttribute("err");
				   		}%>
		
					</table>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" name="update" value="Update Password" />
							<input type="submit" name="update" value="Update Personal" />
							<input type="submit" name="update" value="Delete" /> 
							<input type="reset" value="Reset" />
						</td>
					</tr>
				</form></center>
    		  
    	 <% } db.close_db(); %>
       
    </body>
</html>
