<!--
Search page allows to user to enter a search query 
-->

<%@	page import="util.Db" %>
<%@ page import="util.User" %> 
<%@ page import="java.util.ArrayList" %>

<% 
	User user = null;
	Db database = new Db();
	database.connect_db();
		
	String status = null;
		
	try {
	   	status = (String) session.getAttribute("status");
	   	user = (User) session.getAttribute("user");	
    
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
			<form name='choosesubmit1' action='analysisservlet?submit=1' method='POST'>
				<table>
					<tr><th>Select sensor</th>
						<td>
							<select name="selected_sensor"><% for (String sensor : database.getSensors(user.getPerson_id())) {
																	out.println("<option value=\""+ sensor + "\">" + sensor + "</option>");
															}%>
							</select>
						</td>
					</tr>
					<tr><td><input type='submit' value='Submit'></input></td></tr>
					
					<% 	String selected_sensor = (String) session.getAttribute("selected_sensor"); 
						if (selected_sensor != null) {
					%>
							<table border="1" width="30%" cellpadding="5">
								<tr><th>Yearly Results</th></tr>
									<tr><td>sensor_id</td><td>Average</td><td>Min</td><td>Max</td></tr>
									<tr><%out.println("<td>" + selected_sensor + "</td><td>" 
										+ (Double) session.getAttribute("avg") + "</td><td>" + (Double) session.getAttribute("min") 
										+ "</td><td>" + (Double) session.getAttribute("max") + "</td>");%>
								</tr>
							</table>		
					<% 		session.removeAttribute("selected_sensor"); 
					}
					%>
				</table>
			</form>
			
			<form name='choosesubmit2' action='analysisservlet?submit=2' method='POST'>
					<% 	ArrayList<String> years = (ArrayList<String>) session.getAttribute("years");
						if (years != null) {
					%>
						<table>
						<tr><th>Select year</th>
							<td>	
								<select name="selected_year"><% for (String year : years) {
													out.println("<option value=\"" + year + "\">" + year + "</option>");
												}%>
								</select>
							</td>
						</tr>
						<tr><td><input type='submit' value='Submit'></input></td></tr>
					<% 	session.removeAttribute("years");
					}
					%>
					<% 	String selected_year = (String) session.getAttribute("selected_year"); 
						if (selected_year != null) {
					%>
						<table>
							<th>Timeframe</th>
							<td>
								<input type='radio' name='tframe' value='daily'>Daily</input>
								<input type='radio' name='tframe' value='weekly'>Weekly</input>
								<input type='radio' name='tframe' value='monthly'>Monthly</input>
								<input type='radio' name='tframe' value='quarterly'>Quarterly</input>
							</td> 
							<tr><td><input type='submit' value='Submit'></input></td></tr>
						</table>
					<% 		session.removeAttribute("selected_year");
					}  
					%>
					</table>
			</form>
			
			<form name='choosesubmit3' action='analysisservlet?submit=3' method='POST'>
				
			</form>
			
		</div>
	</div>
  </body>
</html>

<% 
	} catch (NullPointerException e) {
		   e.printStackTrace();
	}
	database.close_db(); 
%>
