<!--
Search page allows to user to enter a search query 
-->

<%@	page import="util.Db" %>
<%@ page import="util.User" %> 

<% 
	User user = null;
	Db database = new Db();
	database.connect_db();
		
	String status = null;
	
	try {
	   status = (String) session.getAttribute("status");
	   user = (User) session.getAttribute("user");
	   //username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
	   e.printStackTrace();
	}
    
    if (user == null) {
        status = "Please log in before continue";
        session.setAttribute("status", status);
        response.sendRedirect("/oos-cmput391/login.jsp");
    }
%>

<html>
  <head>
    <title>Data analysis</title>
    <center>
    	<jsp:include page="includes/header.jsp"/>
    </center>
  </head>
  <body>
	<div style="text-align:center">
  		<div style="display:inline-block">
			<form name='choosetime' action='analysisservlet' " + "method='PUT'>
				<table>
					<tr><th>Timeframe</th>
						<td>
							<input type='radio' name='tframe' value='daily'>Daily</input>
							<input type='radio' name='tframe' value='weekly'>Weekly</input>
							<input type='radio' name='tframe' value='monthly'>Monthly</input>
							<input type='radio' name='tframe' value='yearly'>Yearly</input>
						</td>
					</tr>
					<tr><td><input type='submit'></input></td></tr>
				</table>
			</form>
		</div>
	</div>
  </body>
</html>
