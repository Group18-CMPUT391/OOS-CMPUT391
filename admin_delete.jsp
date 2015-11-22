<!DOCTYPE html>
<html>
<head>
<title>Admin Delete</title>
<center><jsp:include page="includes/header.jsp" /></center>
<meta charset="utf-8">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/start/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script type="text/javascript">
			$(document).ready(function(){
				$( "#deleteUser" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										 });
				$( "#deleteUserClick" ).click(function() {
					$( "#deleteUser" ).dialog( "open" );
					return false;
					
				});
				$( "#deleteSensor" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										 });
				$( "#deleteSensorClick" ).click(function() {
					$( "#deleteSensor" ).dialog( "open" );
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
   
%>
	

	<div id="deleteUser" title="Delete Users">

		<center><form action="deleteservlet?type=deleteUser" method="post"
			id="deleteUserClick" onsubmit="this">
			<table width="59%" border="1">
					<thead><tr><th colspan="6">Delete User</th></tr></thead>
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
				<td><input type="checkbox" name="usercheckbox" value=<%=String.valueOf(result_set.get(i).getPerson_id())%> /></td>
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
				<td colspan="2" align="center"><input type="submit"
					value="Submit" /> <input type="reset" value="Reset" /></td>
			</tr>
		</form></center>
	</div>
	<div id="deleteSensor" title="Delete Sensors">
		<center><form action="deleteservlet?type=deleteSensor"
			method="post" id="deleteSensorClick" onsubmit="this">
			<table width="59%" border="1">
					<thead><tr><th colspan="5">Delete Sensors</th></tr></thead>
				<tr>
					<th></th>
					<th>Sensor ID</th>
					<th>Location</th>
					<th>Sensor Type</th>
					<th>Description</th>

				</tr>
				<%
				ArrayList<Sensors> result_set1=db.printSensors();
				for(int j=0;j<result_set1.size();++j){
					out.println("<tr>"); %>
				<td><input type="checkbox" name="sensorcheckbox" value= <%=String.valueOf(result_set1.get(j).getSensor_id())%>></td>
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
		</form></center>
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
										<a href="#" id="deleteUserClick">Delete Users</a>
									</center>
								</td>
							</tr>
							<tr>
								<td>
									<center>
										<a href="#" id="deleteSensorClick">Delete
											Sensors</a>
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
