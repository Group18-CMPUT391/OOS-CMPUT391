<!--
Search page allows to user to enter a search query 
-->

<%@	page import="util.Db" %>
<%@	page import="util.OLAPCommands" %>
<%@ page import="util.User" %> 
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>

<% 
	User user = null;
	Db database = new Db();
	database.connect_db();
	
	OLAPCommands olap = new OLAPCommands();
		
	String status = null;
	String submit = "1";	
	
	try {
	   	status = (String) session.getAttribute("status");
	   	user = (User) session.getAttribute("user");	
    
	    if (user == null) {
	        status = "Please log in before continue";
	        session.setAttribute("status", status);
	        response.sendRedirect("/oos-cmput391/login.jsp");
	    }
	    
	    String selected_sensor = (String) session.getAttribute("selected_sensor");
	    String selected_year = (String) session.getAttribute("selected_year");
	    ArrayList<String> years = (ArrayList<String>) session.getAttribute("years");
	    ArrayList<String> sensors = new ArrayList<String>();
	    
	    if (years != null) {
			submit ="2";
	    }
	    ResultSet rs = olap.getSIDScalar(user.getPerson_id());
	    while(rs != null && rs.next()) {
	    	sensors.add(String.valueOf(rs.getLong("sensor_id")));
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
	<center>
 			<%out.println("<form name='choosesubmit1' id=\"submit\" action='analysisservlet?submit="+submit+"' method='POST'>");%>
	 			<table>
 					<tr><th>Select sensor</th>
 						<td><select name="selected_sensor">
 								<% for (String sensor : sensors) {
 										out.println("<option value=\""+ sensor + "\">" + sensor + "</option>");
								   }%>
							</select>
						</td><td>
						<%if (sensors.isEmpty()) {%>
							<input type='button' value='Submit'/></td></tr>
						<%}
						  else{%>
							<input type='submit' value='Submit'/></td></tr>
						<%}%>
						
				</table></form>
				<%if (selected_sensor != null) {%>
					<table border="1" width="30%" cellpadding="5">
						<tr><th colspan="4">Yearly Results</th></tr>
							<tr><td>sensor_id</td><td>Average</td><td>Min</td><td>Max</td></tr>
							<tr><%out.println("<td>" + selected_sensor + "</td><td>" 
								+ (Double) session.getAttribute("avg") + "</td><td>" + (Double) session.getAttribute("min") 
								+ "</td><td>" + (Double) session.getAttribute("max") + "</td>");%>
						</tr>
					</table><%} %>
				<br>
				
 					<%	if (years != null) {%>
							<%out.println("<form name='choosesubmit1' action='analysisservlet?submit="+submit+"' method='POST'>");%>
								<table>
									<tr><th>Select year</th>
									<td><select name="selected_year"><% for (String year : years) {
																	out.println("<option value=\"" + year + "\">" + year + "</option>");
																							}%>
									</select><%out.println("<input type=\"hidden\" name=\"selected_sensor\" value=\""+selected_sensor+"\" >");%>
									</td><td><input type='submit' value='Submit'/></td></tr>	
								</table></form><%}%>
								
								
 					<%	if (selected_year != null) {%>
 						<%out.println("<form name='choosesubmit1' action='analysisservlet?submit="+submit+"' method='POST'>");%>
 						<table>
 							<tr><th align="left">Timeframe</th>
							<td colspan="3">
								<input type='radio' name='tframe' value='daily'>Daily</input>
								<input type='radio' name='tframe' value='weekly'>Weekly</input>
								<input type='radio' name='tframe' value='monthly'>Monthly</input>
								<input type='radio' name='tframe' value='quarterly'>Quarterly</input>
								<%out.println("<input type=\"hidden\" name=\"selected_sensor\" value=\""+selected_sensor+"\" >");%>
								<%out.println("<input type=\"hidden\" name=\"years\" value=\""+years+"\" >");%>
							</td><td><input type='submit' value='Submit'/></td></tr>						
 						</table></form><%}%>
			<%	session.removeAttribute("selected_sensor");
			  	session.removeAttribute("years");
			  	session.removeAttribute("selected_year");%>
	</center>
  </body>
</html>

<% 
	
	} catch (NullPointerException e) {
		   e.printStackTrace();
	}
	database.close_db(); 
%>