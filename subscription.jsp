<!DOCTYPE html>
<html>
<head>
<title>Subscription</title>
<center><jsp:include page="includes/header.jsp" /></center>
<meta charset="utf-8">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script type="text/javascript">
			$(document).ready(function(){
				$( "#addSubscription" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
    										window.location.reload();
  										} });
				$( "#addSubscriptionClick" ).click(function() {
					$( "#addSubscription" ).dialog( "open" );
					return false;
					
				});

				$( "#deleteSubscription" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
											window.location.reload();
										} });
				$( "#deleteSubscriptionClick" ).click(function() {
					$( "#deleteSubscription" ).dialog( "open" );
					return false;
				});
				
				
			});
			function autoResize(id){
			    var newheight;
			    var newwidth;
			    if(document.getElementById){
			        newheight = document.getElementById(id).contentWindow.document .body.scrollHeight;
			        newwidth = document.getElementById(id).contentWindow.document .body.scrollWidth;
			    }
			    document.getElementById(id).height = (newheight) + "px";
			    document.getElementById(id).width = (newwidth) + "px";
			}
		</script>
</head>
<body>
	<%@ page import="util.*" %>
    <%@ page import="java.sql.*" %>
    <%@ page import="java.io.*" %>
    <%@page import="java.util.*" %>


	<% 
	User user = null;
	try {
		user = (User) session.getAttribute("user");
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
   if (user == null) {
   	  session.setAttribute("status", "Please login to access page");
   	  response.sendRedirect("/oos-cmput391/login.jsp");
   }
   
   String error = null;  
   try{  
      error = (String) session.getAttribute("err");  
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
	   Db db = new Db();
	   db.connect_db();
  	   long user_id=user.getPerson_id();
   
%>
	

	<div id="addSubscription" title="Add Subscription">

		<form action="subscriptionservlet?type=addSubscription" method="post"
			id="addSubscriptionClick" onsubmit="this">
			<table width="59%" border="1">
					<thead><tr><th colspan="5">ADD SUBSCRIPTION</th></tr></thead>
				<tr>	
					<th></th>
					<th>Sensor ID</th>
					<th>Location</th>
					<th>Sensor Type</th>
					<th>Description</th>

				</tr>
				<%
				ArrayList<Sensors> result_set=db.printAddSubscriptions(user_id);
				for(int i=0;i<result_set.size();++i){
					out.println("<tr>"); %>
				<td><input type="checkbox" name="addcheckbox" value=<%=String.valueOf(result_set.get(i).getSensor_id())%> /></td>
					<%
							
					out.println("<td>"+String.valueOf(result_set.get(i).getSensor_id())+"</td>");

										
					out.println("<td>"+String.valueOf(result_set.get(i).getLocation())+"</td>");

										
					out.println("<td>"+String.valueOf(result_set.get(i).getSensor_type())+"</td>");
			

										
					out.println("<td>"+String.valueOf(result_set.get(i).getDescription())+"</td>");

					out.println("</tr>");
				} 
					
		 	if (error != null) {
		   					out.println(error); 
		   					session.removeAttribute("err");
							
		   				}%>

			</table>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Submit" /> <input type="reset" value="Reset" /></td>
			</tr>
		</form>
	</div>
	<div id="deleteSubscription" title="Delete/View Subscription">
		<form action="subscriptionservlet?type=deleteSubscription"
			method="post" id="deleteSubscriptionClick" onsubmit="this">
			<table width="59%" border="1">
					<thead><tr><th colspan="5">DELETE/VIEW SUBSCRIPTIONS</th></tr></thead>
				<tr>
					<th></th>
					<th>Sensor ID</th>
					<th>Location</th>
					<th>Sensor Type</th>
					<th>Description</th>

				</tr>
				<%
				ArrayList<Sensors> result_set1=db.printDeleteSubscriptions(user_id);
				for(int j=0;j<result_set1.size();++j){
					out.println("<tr>"); %>
				<td><input type="checkbox" name="deletecheckbox" value= <%=String.valueOf(result_set1.get(j).getSensor_id())%>></td>
					<%		
					out.println("<td>"+String.valueOf(result_set1.get(j).getSensor_id())+"</td>");

										
					out.println("<td>"+String.valueOf(result_set1.get(j).getLocation())+"</td>");

										
					out.println("<td>"+String.valueOf(result_set1.get(j).getSensor_type())+"</td>");
			

										
					out.println("<td>"+String.valueOf(result_set1.get(j).getDescription())+"</td>");

					out.println("</tr>");
				} 
					
		 	if (error != null) {
		   					out.println(error); 
		   					session.removeAttribute("err");
							
		   				}%>


			</table>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Submit" /> <input type="reset" value="Reset" /></td>
			</tr>
		</form>
	</div>

	<center>
		<table>
			<tr>
				<td valign="top">
					<table border="\" "1\" width="\" "30%\" cellpadding="\""5\">
						<thead>
							<tr>
								<th>Select an Option:</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<center>
										<a href="#" id="addSubscriptionClick">Add Subscription</a>
									</center>
								</td>
							</tr>
							<tr>
								<td>
									<center>
										<a href="#" id="deleteSubscriptionClick">Delete/View
											Subscription</a>
									</center>
								</td>
							</tr>

							<tr>
								<td colspan="2" align="center">
									<%if (error != null) {
				   					out.println(error); 
				   					session.removeAttribute("err");
				   				}
									db.close_db();%>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>

