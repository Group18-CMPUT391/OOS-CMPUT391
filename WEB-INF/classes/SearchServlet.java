import java.io.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;
import java.util.*;

import util.Db;
import util.User;


public class SearchServlet extends HttpServlet {
	/**
	 * Handles HTTP POST request
	 */
	public void doPost( HttpServletRequest request, 
			HttpServletResponse response )
					throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet( request, response );
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session;
		User user;
		Db database = new Db();
        ResultSet rs = null;
        
        // Set the content type to HTML
 		response.setContentType( "text/html" );
 		// Get the output stream from the response object
 		PrintWriter out = response.getWriter();
 		// Get the session (Create a new one if required)
 		session = request.getSession(true);
     		
        database.connect_db();   
        String keywords = request.getParameter("query");
        String location = request.getParameter("location");
        String fromDate = request.getParameter("fromdate");
        String toDate = request.getParameter("todate");
        String sensor_type = request.getParameter("sensor_type");
        String pid = "";
        
        // Check if the user is logged in
 		user = (User) session.getAttribute("user");
 		if (user == null) {
 			String errormsg = "Please login before accessing account info";
 		    session.setAttribute("status", errormsg);
 		    response.sendRedirect("/oos-cmput391/login.jsp");
 		    return;
 		}
     		

        
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<title>Search Results</title>");
        out.println("<center>");

        /* 
         * Display header 
         */    
        try {
            request.getRequestDispatcher("includes/header.jsp").include( 
                                        request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        out.println("</center></head>");
        out.println("<body>");
        out.println("<br>");
        
        try{
        	
        	//Check if first date is less than second date in the search field 
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String[] datef = fromDate.split("T");
        	String[] datet = toDate.split("T");
        	String date1 = null;
        	String date2 = null;
        	if (datef[1].split(":").length != 3) {
        		date1 = datef[0] +" "+datef[1] + ":00";
    		}
    		else {
    			date1 = datef[0] +" "+datef[1];
    		}
        	if (datet[1].split(":").length != 3) {
        		date2 = datet[0] +" "+datet[1] + ":00";
    		}
    		else {
    			date2 = datet[0] +" "+datet[1];
    		}
        	
        	if(formatter.parse(date1).after(formatter.parse(date2)) || 
        			formatter.parse(date1).equals(formatter.parse(date2))){
        		
        		session.setAttribute("err", "The first data has to be smaller than the second date.");
				response.sendRedirect("/oos-cmput391/search.jsp");
        	}
        	else {
        		//search with sensor type
        		if (!(sensor_type.equals("empty"))) {
            		rs = database.getResultsSensor(user.getPerson_id(), sensor_type, date1, date2, keywords, location);
                    out.println("<center><b>Your results for '" + keywords + "' between "
                                + fromDate + " and " + toDate + ":\n<b></center>");
                    
                    //Results for audio recordings
                    if (sensor_type.equals("a")) {
    	            	 out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\"style=\"white-space:nowrap;\">");
    	            	 out.println("<thead style=\"white-space:nowrap;\">"
    									+"<tr><td colspan=\"6\"><center><b>Audio Sensors</b></center></td></tr>"
    									+"<tr><td><b>Recording ID</b></td>"
    									+"<td><b>Sensor ID</b></td>"
    									+"<td><b>Date Created</b></td>"
    									+"<td><b>Length</b></td>"
    									+"<td><b>Description</b></td>"
    									+"<td><b>Audio File (Right Click Save audio as...)</b></td></tr></thead>");
    	            	 
    	            	while(rs.next()){
    	    				out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    	    						"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    	    						"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    	    						"<td>" + String.valueOf(rs.getInt(4))+"</td>"+
    	    						"<td>" + rs.getString(5) + "</td>" + 
    	    						"<td><audio controls><source src=\"/oos-cmput391/audioservlet?id="+rs.getInt(1)+"\" type=\"audio/wav\"></audio></td></tr>");
    	                }
    	            	out.println("</table></center><br>");
                    }
                    
                    //Results for images
    	           	else if (sensor_type.equals("i")) {
    			          	 out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\"style=\"white-space:nowrap;\">");
    			          	 out.println("<thead style=\"white-space:nowrap;\">"
    										+"<tr><td colspan=\"6\"><center><b>Image Sensors</b></center></td></tr>"
    										+"<tr><td><b>Image ID</b></td>"
    										+"<td><b>Sensor ID</b></td>"
    										+"<td><b>Date Created</b></td>"
    										+"<td><b>Description</b></td>"
    										+"<td><b>Thumbnail</b></td>"
    										+"<td><b>Full Size</b></td></tr></thead>");
    			          	while(rs.next()){
    							out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    									"<td>" + rs.getString(4)+"</td>"+
    									"<td><center><image src=\"/oos-cmput391/imageservlet?full=no&id="+
    									rs.getInt(1)+"\"><center></td>"+
    									"<td><center><a href=\"/oos-cmput391/imageservlet?full=yes&id="+rs.getInt(1)+"\">Download Full Size Image!<center></td></tr>");
    			          	}
    	
    						out.println("</table></center>");
    	           	}
                    
                    //Results for scalar data
    	           	else if (sensor_type.equals("s")) {
    	           		out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\"style=\"white-space:nowrap;\">");
    	           		out.println("<thead style=\"white-space:nowrap;\">"
    	           						+"<tr><td colspan=\"6\"><center><b>Scalar Data</b><br><a href=\"/oos-cmput391/scalarservlet?user=yes&fromdate="
    	           						+fromDate+"&todate="+toDate+"&id="+user.getPerson_id()+"&value="+keywords+"&location="+location+"\">Download CSV File</a></center></td></tr>"
    	           						+"<tr><td><b>ID</b></td>"
    	           						+"<td><b>Sensor ID</b></td>"
    	           						+"<td><b>Date Created</b></td>"
    	           						+"<td><b>Value</b></td></tr></thead>");
    	           		while (rs.next()){
    	           			out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    	       									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    	       									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    	       									"<td>" + String.valueOf(rs.getInt(4)) + "</td></tr>");
    	       			}
    	           		out.println("</table></center>");
    	           	}
            	}
        		
        		//Searching without sensor type
            	else if (sensor_type.equals("empty")){
            		rs = database.getResultAll(user.getPerson_id(), date1, date2, keywords, location);
            		
            		out.println("<center><table border=\"1\" width=\"30%\" cellpadding=\"5\"style=\"white-space:nowrap;\">");
    	          	 out.println("<thead style=\"white-space:nowrap;\">"
    								+"<tr><td colspan=\"6\"><center><b>Result</b></center></td></tr>"
    								+"<tr><td><b>Image ID</b></td>"
    								+"<td><b>Sensor ID</b></td>"
    								+"<td><b>Date Created</b></td>"
    								+"<td><b>Description/Value</b></td>"
    								+"<td><b>Download</b></td></tr></thead>");
            		
            		
            		while(rs.next()){
            			if (rs.getString(5).equals("a")){
            				out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    	    						"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    	    						"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    	    						"<td>" + rs.getString(4) + "</td>" + 
    	    						"<td><audio controls><source src=\"/oos-cmput391/audioservlet?id="+rs.getInt(1)+"\" type=\"audio/wav\"></audio></td></tr>");
            			}
            			if (rs.getString(5).equals("i")){
            				out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    								"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    								"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    								"<td>" + rs.getString(4)+"</td>"+
    								"<td><center><image src=\"/oos-cmput391/imageservlet?full=no&id="+
    								rs.getInt(1)+"\"><br><a href=\"/oos-cmput391/imageservlet?full=yes&id="+rs.getInt(1)+"\">Download Full Size Image!<center></td></tr>");
            			}
            			if (rs.getString(5).equals("s")){
            				out.println("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
    									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
    									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
    									"<td>" + rs.getString(4) + "</td>" + 
    									"<td><a href=\"/oos-cmput391/scalarservlet?user=yes&fromdate="
    	           						+fromDate+"&todate="+toDate+"&id="+user.getPerson_id()+"&location="+location+"&value="+keywords+"\">CSV File for current search</a>"
    	           								+ "</center></td></tr>");
            			}
            		}
            		out.println("</table></center><br>");
            				
            	}
        	}
        	
        	
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        database.close_db();
        out.print("</body>");
        out.print("</html>");
        out.close();
    }
}
