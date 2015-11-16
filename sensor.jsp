
<!-- 
Web page for Sensor
-->
<!DOCTYPE html>

<html>
    <head>
        <title>Sensors</title>
        <center><jsp:include page="includes/header.jsp"/></center>
        <meta charset="utf-8">
  		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
 			<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  		<link rel="stylesheet" href="/resources/demos/style.css">
			<script type="text/javascript">
			$(document).ready(function(){
				$( "#audio" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
    										window.location.reload();
  										} });
				$( "#audioClick" ).click(function() {
					$( "#audio" ).dialog( "open" );
					return false;
					
				});
				$( "#image").dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
    										window.location.reload();
  										} });
				$( "#imageClick" ).click(function() {
					$( "#image" ).dialog( "open" );
					return false;
				});
				$( "#sensor" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
    										window.location.reload();
  										} });
				$( "#sensorClick" ).click(function() {
					$( "#sensor" ).dialog( "open" );
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
	<%@ page import="util.Db" %>
    <%@ page import="java.sql.*" %>
	<% 
   String username = (String) session.getAttribute("username");
   if (username == null) {
   	  session.setAttribute("status", "Please login to access page");
   	  response.sendRedirect("/oos-cmput391/login.jsp");
   }
   
   String error = null;  
   try{  
      error = (String) session.getAttribute("err");  
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   
   try{
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	   //Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1525:CRS","wkchoi", "Kingfreak95");
	   Connection con = DriverManager.getConnection("jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS","wkchoi", "Kingfreak95");
	   Statement s = con.createStatement();
   %>
	
			<div id="sensor" title="New Sensor">
				<form action="sensorservlet?type=sensor" method="post" enctype="multipart/form-data" onsubmit="this">
					<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Sensor Information:</th></tr></thead>
	    				<tbody>
	    				<tr><td>Sensor ID:</td>
	    				<td><input type="number" name="sensor_id" required="required"/></td></tr>
	    				<tr><td>location:</td>
	    				<td><input type="text" name="location" required="required" maxlength="64" /></td></tr>
	    				<tr><td>Sensor Type:</td>
	              		<td><center><select name="sensor_type">
	              		<option value="a">Audio</option>
						<option value="i">Image</option>
						<option value="s">Scalar Value</option>
	              		</select></center></td></tr>
	    				<tr><td>Description:</td>
	    				<td><input type="text" name="description" required="required" maxlength="128" /></td></tr>
	    				<tr><td colspan="2" align="center"><input type="submit" value="Submit"/> <input type="reset" value="Reset" /></td></tr>
	    				<tr><td colspan="2" align="center">
				 					<%if (error != null) {
				   					out.println(error); 
				   					session.removeAttribute("err");
				   				}%>
		    				</td></tr>
		    				</tbody></table></center></form>			
			</div>
			<div id="audio" title="New Audio" >
				<form action="sensorservlet?type=audio_recordings" method="post" enctype="multipart/form-data" onsubmit="this">
				<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Audio Information:</th></tr></thead>
					<tbody>
						<tr><td>Recording ID:</td>
						<td><input type="number" name="recording_id" required="required"/></td></tr>
						<tr><td>Sensor ID:</td>
						<td><select name="sensor_id">
							<%String sid_a = "SELECT sensor_id FROM sensors WHERE sensor_type = 'a' ORDER BY sensor_id";
							ResultSet rs_a = s.executeQuery(sid_a);
									while(rs_a.next()) {
										out.println("<option value=\""+String.valueOf(rs_a.getInt(1))+"\">" + rs_a.getInt(1) + "</option>");
									}%>
						</select></td>
						<tr><td>Date:</td>
						<td><input type="date" name="date_created" step="1" required="required"/></td></tr>
						<tr><td>length:</td>
						<td><input type="number" name="length" required="required"/></td></tr>
						<tr><td>Description:</td>
						<td><input type="text" name="description" required="required" maxlength="128" /></td></tr>
						<td colspan="2" align="center"><input type="file" accept=".wav" name="recorded_data" required="required" maxlength="128" /></td></tr>
						<tr><td colspan="2" align="center"><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></td></tr>
						<tr><td colspan="2" align="center">
			 					<%if (error != null) {
			   					out.println(error); 
			   					session.removeAttribute("err");
			   				}%>
	    				</td></tr>
	    				</tbody></table></center></form>
			</div>
			<div id="image" title="New Image" >
				<form action="sensorservlet?type=images" method="post" enctype="multipart/form-data" onsubmit="this">
				<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Image Information:</th></tr></thead>
					<tbody>
						<tr><td>Image ID:</td>
						<td><input type="number" name="image_id" required="required"/></td></tr>
						<tr><td>Sensor ID:</td>
						<td><select name="sensor_id">
							<%String sid_i = "SELECT sensor_id FROM sensors WHERE sensor_type = 'i' ORDER BY sensor_id";
							ResultSet rs_i = s.executeQuery(sid_i);
									while(rs_i.next()) {
										out.println("<option value=\""+String.valueOf(rs_i.getInt(1))+"\">" + rs_i.getInt(1) + "</option>");
									}%>
						</select></td>
						<tr><td>Date:</td>
						<td><input type="date" name="date_created" step="1" required="required"/></td></tr>
						<tr><td>Description:</td>
						<td><input type="text" name="description" required="required" maxlength="128" /></td></tr>
						<td colspan="2" align="center"><input type="file" accept=".wav" name="recorded_data" required="required" maxlength="128" /></td></tr>
						<tr><td colspan="2" align="center"><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></td></tr>
						<tr><td colspan="2" align="center">
			 					<%if (error != null) {
			   					out.println(error); 
			   					session.removeAttribute("err");
			   				}%>
	    				</td></tr>
	    		</tbody></table></center></form>
			</div>
		
    
	<table>	
		<tr><td valign="top">	
			<table border=\"1\" width=\"30%\" cellpadding=\"5\">
				<thead>
					<tr>
						<th>Select Sensor Type to Add: </th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
						<center><a href="#" id="sensorClick">New Sensor</a></center>
						</td>
					</tr>
					<tr>
						<td>
							<center><a href="#" id="audioClick">Audio</a></center>
						</td>
					</tr>
					<tr>
						<td>
						<center><a href="#" id="imageClick">Image</a></center>
						</td>
					</tr>  				
				</tbody>
			</table>
		</td>
		<td valign="top">
			<table border="1" width="30%" cellpadding="5">
				<tr >
					<form action="/oos-cmput391/sensor.jsp" method="post" onsubmit="this">
						<td>Select Sensor Type to Display:&nbsp;
							<select name="sensor_selected">
		              		<option value="a">Audio</option>
							<option value="i">Image</option>
							<option value="s">Scalar Value</option>
		              		</select>&nbsp;<input type="submit" value="Select" /></td>
		           </form>
				</tr>
				<tr>
					<td>
						<table border="1" width="30%" cellpadding="5" style="white-space:nowrap;border-collapse:collapse;">
						<%
							String sensorSelect = null;
							sensorSelect = request.getParameter("sensor_selected");
							if (sensorSelect == null) {
							out.println("Please Select Sensor type to view table");
							}
							else if (sensorSelect.equals("a")) {
						%>
								<thead><tr><td><b>Recording ID</b></td>
									<td><b>Sensor ID</b></td>
									<td><b>Date Created</b></td>
									<td><b>Length</b></td>
									<td><b>Desciption</b></td>
									<td><b>Download</b></td></tr></thead>
						<%		
								String list_a = "SELECT * FROM audio_recordings ORDER BY recording_id";
								ResultSet rs_LA = s.executeQuery(list_a);
								while (rs_LA.next()){
									out.println("<tr><td>" + String.valueOf(rs_LA.getInt("recording_id")) + "</td>");
									out.println("<td>" + String.valueOf(rs_LA.getInt("sensor_id")) + "</td>");
									out.println("<td>" + String.valueOf(rs_LA.getDate("date_created")) + "</td>");
									out.println("<td>" + String.valueOf(rs_LA.getInt("length")) + "</td>");
									out.println("<td>" + String.valueOf(rs_LA.getString("description")) + "</td>");
								}
							}
						%>
						</table>
					</td>
				</tr>
			</table>
		</td></tr>	
	</table>	
	
			
			
			
			
<%} catch (SQLException e) {
				System.out.println(e.getMessage());
		    }%>	    	
</body> 
</html>
