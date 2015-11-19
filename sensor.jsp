
<!-- 
Web page for Sensor
-->
<!DOCTYPE html>

<html>
    <head>
        <title>Sensors</title>
        <center><jsp:include page="includes/header.jsp"/></center>
        <meta charset="utf-8">
  		<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/start/jquery-ui.css">
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
				$( "#scalar" ).dialog({ height:'auto', 
										width:'auto', 
										autoOpen: false, 
										resizable: false,
										close: function() {
    										window.location.reload();
  										} });
				$( "#scalarClick" ).click(function() {
					$( "#scalar" ).dialog( "open" );
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
		</script>
	</head>
	<body>
	<%@ page import="util.Db" %>
    <%@ page import="java.sql.*" %>
    <%@ page import="java.io.*" %>
    <%@ page import="util.User" %>
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
	
			<div id="sensor" title="New Sensor">
				<form action="sensorservlet?type=sensor" method="post" enctype="multipart/form-data" id="sensorClick" onsubmit="this">
					<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Sensor Information:</th></tr></thead>
	    				<tbody>
	    				<tr><td>Sensor ID:</td>
	    				<td><input type="number" name="sensor_id" required="required" min="1"/></td></tr>
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
	    				
		    				</td></tr>
		    				</tbody></table></center></form>			
			</div>
			<div id="audio" title="New Audio" >
				<form action="sensorservlet?type=audio_recordings" method="post" enctype="multipart/form-data" onsubmit="this">
				<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Audio Information:</th></tr></thead>
					<tbody>
						<tr><td>Recording ID:</td>
						<td><input type="number" name="recording_id" required="required" min="1"/></td></tr>
						<tr><td>Sensor ID:</td>
						<td><select name="sensor_id">
						<%for(int i =0; i < db.getSensorA_id_list().size(); i++) {
							out.println("<option value=\""+ db.getSensorA_id_list().get(i)+"\">"+db.getSensorA_id_list().get(i)+ "</option>");
						}
						%>
						</select></td>
						<tr><td>Date:</td>
						<td><input type="datetime-local" name="date_created" step="1" required="required"/></td></tr>
						<tr><td>length:</td>
						<td><input type="number" name="length" required="required" min="0"/></td></tr>
						<tr><td>Description:</td>
						<td><input type="text" name="description" required="required" maxlength="128" /></td></tr>
						<td colspan="2" align="center"><input type="file" accept=".wav" name="recorded_data" required="required" maxlength="128" /></td></tr>
						<tr><td colspan="2" align="center"><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></td></tr>
						
	    				</tbody></table></center></form>
			</div>
			<div id="image" title="New Image" >
				<form action="sensorservlet?type=images" method="post" enctype="multipart/form-data" onsubmit="this">
				<center><table border="1" width="30%" cellpadding="5">
					<thead><tr><th colspan="2">Input New Image Information:</th></tr></thead>
					<tbody>
						<tr><td>Image ID:</td>
						<td><input type="number" name="image_id" required="required" min="1"/></td></tr>
						<tr><td>Sensor ID:</td>
						<td><select name="sensor_id">
						<%for(int i =0; i < db.getSensorI_id_list().size(); i++) {
							out.println("<option value=\""+ db.getSensorI_id_list().get(i)+"\">"+db.getSensorI_id_list().get(i)+ "</option>");
						}
						%>
						</select></td>
						<tr><td>Date:</td>
						<td><input type="datetime-local" name="date_created" step="1" required="required"/></td></tr>
						<tr><td>Description:</td>
						<td><input type="text" name="description" required="required" maxlength="128" /></td></tr>
						<td colspan="2" align="center"><input type="file" accept=".jpg" name="recorded_data" required="required" maxlength="128" /></td></tr>
						<tr><td colspan="2" align="center"><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></td></tr>
	    		</tbody></table></center></form>
			</div>
			<div id="scalar" title="Input Scalar Data" >
				<form action="sensorservlet?type=scalar" method="post" enctype="multipart/form-data" onsubmit="this">
				<table>
						<thead><tr><th colspan="2">Add Comma Separated Value Text:</th></tr></thead>
						<tbody>
							<tr><td colspan="2" align="center"><input type="file" name="csv" required="required"/>
									<input type="submit" value="Load"/></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div id="fullsize" title="Fullsize Image">
			</div>
	<center><table>	
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
					<tr>
						<td>
						<center><a href="#" id="scalarClick">Scalar data</a></center>
						</td>
					</tr>
					<tr><td colspan="2" align="center">
				 					<%if (error != null) {
				   					out.println(error); 
				   					session.removeAttribute("err");
				   				}%>
		    		</td></tr> 				
				</tbody>
			</table>
		</td>
		<td valign="top">
			<table border="1" width="30%" cellpadding="5" style="white-space:nowrap;">
				<tr >
					<thead>
						<form action="/oos-cmput391/sensor.jsp" method="post" onsubmit="this">
							<td><b>Select Sensor Type to Display:</b>&nbsp;
							<select name="sensor_selected">
				              		<option value="a">Audio</option>
									<option value="i">Image</option>
									<option value="s">Scalar Value</option>
		              			</select>&nbsp;<input type="submit" value="Select" /></td>
		           </form></thead>
				</tr>
				<tr>
					<td>
						<center><table border="1" width="30%" cellpadding="5" style="white-space:nowrap;border-collapse:collapse;">
						<%
							String sensorSelect = null;
							sensorSelect = request.getParameter("sensor_selected");
							if (sensorSelect == null) {
							out.println("Please Select Sensor type to view table");
							}
							else if (sensorSelect.equals("a")) {
						%>
								<thead>
									<tr><td colspan="6"><center><b>Audio Sensors</b></center></td></tr>
									<tr><td><b>Recording ID</b></td>
									<td><b>Sensor ID</b></td>
									<td><b>Date Created</b></td>
									<td><b>Length</b></td>
									<td><b>Description</b></td>
									<td><b>Audio File (Right Click Save audio as... to Save)</b></td></tr></thead>
						<%		
								for(int i =0; i < db.recording_list().size(); i++) {
									out.println(db.recording_list().get(i));
									
								}
							}
							else if (sensorSelect.equals("i")) {
						%>
								<thead>
									<tr><td colspan="6"><center><b>Image Sensors</b></center></td></tr>
									<tr><td><b>Image ID</b></td>
									<td><b>Sensor ID</b></td>
									<td><b>Date Created</b></td>
									<td><b>Description</b></td>
									<td><b>Thumbnail</b></td>
									<td><b>Full Size</b></td></tr>
								</thead>
								
						<%		
								for(int i =0; i < db.image_list().size(); i++) {
									out.println(db.image_list().get(i));
								}
							}
							else if (sensorSelect.equals("s")) {
						%>
								<thead>
									<tr><td colspan="6"><center><b>Scalar Data</b><br><a href="/oos-cmput391/scalarservlet">Download CSV File</a></center></td></tr>
									<tr><td><b>ID</b></td>
									<td><b>Sensor ID</b></td>
									<td><b>Date Created</b></td>
									<td><b>Value</b></td></tr>
								</thead>
						<%		
								for(int i =0; i < db.scalar_list().size(); i++) {
									out.println(db.scalar_list().get(i));
								}
							}
							db.close_db();
						%>
						</table></center>
					</td>
				</tr>
			</table>
		</td></tr>	
	</table></center>					
</body> 
</html>
