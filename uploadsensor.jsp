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
    </head>
    <body>
  	<%
    	String sensortype = request.getParameter("type");
    	
    	if (sensortype.equals("sensor")) {
    		out.println("<form action=\"sensorservlet?type=sensor\" method=\"post\" onsubmit=\"this\">");
    			out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\">");
    				out.println("<thead><tr><th colspan=\"2\">Input New Sensor Information:</th></tr></thead>");
    				out.println("<tbody>");
    					out.println("<tr><td>Sensor ID:</td>");
    					out.println("<td><input type=\"number\" name=\"sensor_id\" required=\"required\"/></td></tr>");
    					out.println("<tr><td>location:</td>");
    					out.println("<td><input type=\"text\" name=\"location\" required=\"required\" maxlength=\"64\" /></td></tr>");
    					out.println("<tr><td>Sensor Type:</td>");
              			out.println("<td><center><select name=\"sensor_type\">");
              					out.println("<option value=\"a\">Audio</option>");
								out.println("<option value=\"i\">Image</option>");
								out.println("<option value=\"s\">Scalar Value</option>");
              			out.println("</select></center></td></tr>");
    					out.println("<tr><td>Description:</td>");
    					out.println("<td><input type=\"text\" name=\"description\" required=\"required\" maxlength=\"128\" /></td></tr>");
    					out.println("<tr><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Submit\" /> <input type=\"reset\" value=\"Reset\" /></td></tr>");
    					out.println("<tr><td colspan=\"2\" align=\"center\">");
			 					if (error != null) {
			   					out.println(error); 
			   					session.removeAttribute("err");
			   				}
	    				out.println("</td></tr>");
	    				out.println("</tbody></table></center></form>");
    	}
    	else if (sensortype.equals("audio_recordings")) {
    		out.println("<form action=\"sensorservlet?type=sensor\" method=\"post\" onsubmit=\"this\">");
			out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\">");
				out.println("<thead><tr><th colspan=\"2\">Input New Sensor Information:</th></tr></thead>");
				out.println("<tbody>");
					out.println("<tr><td>Sensor ID:</td>");
					out.println("<td><input type=\"number\" name=\"sensor_id\" required=\"required\"/></td></tr>");
					out.println("<tr><td>location:</td>");
					out.println("<td><input type=\"datetime-local\" name=\"date_created\" required=\"required\"/></td></tr>");
					out.println("<tr><td>Description:</td>");
					out.println("<td><input type=\"text\" name=\"description\" required=\"required\" maxlength=\"128\" /></td></tr>");
					out.println("<td colspan=\"2\" align=\"center\"><input type=\"file\" name=\"recorded_data\" required=\"required\" maxlength=\"128\" /></td></tr>");
					out.println("<tr><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Submit\" /> <input type=\"reset\" value=\"Reset\" /></td></tr>");
					out.println("<tr><td colspan=\"2\" align=\"center\">");
		 					if (error != null) {
		   					out.println(error); 
		   					session.removeAttribute("err");
		   				}
    				out.println("</td></tr>");
    				out.println("</tbody></table></center></form>");
	}
    	
    %>
    </body>
</html>
