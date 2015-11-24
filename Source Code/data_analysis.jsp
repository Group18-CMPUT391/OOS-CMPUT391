<%@	page import="util.Db" %>
<%@	page import="util.OLAPCommands" %>
<%@ page import="util.User" %> 
<%@ page import="util.Analysis_Date" %> 
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
	    Long person_id= user.getPerson_id();
// 	    String selected_sensor = (String) session.getAttribute("selected_sensor");
// 	    String selected_year = (String) session.getAttribute("selected_year");
	    ArrayList<String> years = new ArrayList<String>();
	    ArrayList<String> sensors = new ArrayList<String>();
	    String selected_year=(String) session.getAttribute("selected_year");
	    String tframe=(String) session.getAttribute("tframe");
		String selected_sensor = (String) session.getAttribute("selected_sensor");

	    
	    if (years != null) {
			submit ="2";
	    }
	    if(selected_year !=null){
	    	submit="3";
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
 			<%out.println("<form name='choosesubmit1' action='analysisservlet?submit="+submit+"' method='POST'>");%>
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
				<%

			    
			    if (selected_sensor != null) {%>
					
					<table border="1" width="30%" cellpadding="5">
						<tr><th colspan="4">Yearly Results</th></tr>
							<tr><td>Year</td><td>Average</td><td>Min</td><td>Max</td></tr>
									<%
		
		ArrayList<Analysis_Date> years_date=(ArrayList<Analysis_Date>) session.getAttribute("years_date");
		

		for(int i =0; i<years_date.size(); i++) {
			
			out.println("<tr><td>" + years_date.get(i).getAnalysisDate() + "</td><td>"
			+ String.valueOf(years_date.get(i).getAnalysisAvg()) + "</td><td>" + years_date.get(i).getAnalysisMin() 
			+ "</td><td>" + years_date.get(i).getAnalysisMax() + "</td></tr>");						
		}
	%>
						
					</table><%} %>
			<br> 
				
					<%	if (selected_sensor != null) {%> 
							<%out.println("<form name='choosesubmit1' action='analysisservlet?submit="+submit+"' method='POST'>");%>
								<table> 
									<tr><th>Select year</th> 
									<% years = (ArrayList<String>) session.getAttribute("years");%>
									<td><select name="selected_year"><% for (String year : years) { 
																out.println("<option value=\"" + year + "\">" + year + "</option>");
 																							}%> 
									</select><%out.println("<input type=\"hidden\" name=\"selected_sensor\" value=\""+selected_sensor+"\" >");%>
									</td><td><input type='submit' value='Submit'/></td></tr>
								</table></form><%}%> 
								
								
					<%
					
					if (selected_year != null) {%> 
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
 								<%out.println("<input type=\"hidden\" name=\"selected_year\" value=\""+selected_year+"\" >");%> 
							</td><td><input type='submit' value='Submit'/></td></tr>						
  						</table></form><%}%> 
  			<% 
  				
 			if (tframe != null){
					out.println("<table border='1' width='30%' cellpadding='5'>");
					out.println("<tr><th align='center' colspan='4'>Drilldown to "+tframe+" </th>");
 					
 				if (tframe.equals("daily")){
 					
 					 ArrayList<Analysis_Date> day=(ArrayList<Analysis_Date>) session.getAttribute("day");
 					 for(int j=0;j<day.size();j++){
 						out.println("<tr><td>" + day.get(j).getAnalysisDate() + "</td><td>"
 						+ String.valueOf(day.get(j).getAnalysisAvg()) + "</td><td>" + day.get(j).getAnalysisMin() 
 						+ "</td><td>" + day.get(j).getAnalysisMax() + "</td></tr>");
 					 }
 				}
 				else if(tframe.equals("weekly")){
 					
					 ArrayList<Analysis_Date> week=(ArrayList<Analysis_Date>) session.getAttribute("week");
					 for(int j=0;j<week.size();j++){
						out.println("<tr><td>" + week.get(j).getAnalysisDate() + "</td><td>"
						+ String.valueOf(week.get(j).getAnalysisAvg()) + "</td><td>" + week.get(j).getAnalysisMin() 
						+ "</td><td>" + week.get(j).getAnalysisMax() + "</td></tr>");
					 }
				}
 				else if(tframe.equals("monthly")){
 					
					 ArrayList<Analysis_Date> month=(ArrayList<Analysis_Date>) session.getAttribute("month");
					 for(int j=0;j<month.size();j++){
						out.println("<tr><td>" + month.get(j).getAnalysisDate() + "</td><td>"
						+ String.valueOf(month.get(j).getAnalysisAvg()) + "</td><td>" + month.get(j).getAnalysisMin() 
						+ "</td><td>" + month.get(j).getAnalysisMax() + "</td></tr>");
					 }
				
 				}
 				else if (tframe.equals("quarterly")){
					 ArrayList<Analysis_Date> quarter=(ArrayList<Analysis_Date>) session.getAttribute("quarter");
					 for(int j=0;j<quarter.size();j++){
						out.println("<tr><td>" + quarter.get(j).getAnalysisDate() + "</td><td>"
						+ String.valueOf(quarter.get(j).getAnalysisAvg()) + "</td><td>" + quarter.get(j).getAnalysisMin() 
						+ "</td><td>" + quarter.get(j).getAnalysisMax() + "</td></tr>");
					 }
				
 				}
 				out.println("</table>");
 			}

 				
 			session.removeAttribute("selected_sensor"); 
			session.removeAttribute("selected_year");
			session.removeAttribute("tframe");

			  	%> 
	</center>
  </body>
  <footer>
<center><jsp:include page="includes/footer.jsp"/></center>
</footer> 
</html>

<% 
	
	} catch (NullPointerException e) {
		   e.printStackTrace();
	}
	database.close_db(); 
	olap.close_OLAP();
%>
