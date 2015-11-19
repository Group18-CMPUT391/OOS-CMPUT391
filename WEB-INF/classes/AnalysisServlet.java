import java.io.*;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.http.*;

import util.Db;
import util.OLAPCommands;
import util.User;


public class AnalysisServlet extends HttpServlet {
    private OLAPCommands olap;
    private String tframe;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
		HttpSession session;
		User user;
		long sensor_id;
    	String location;
    	String daily;
    	String weekly;
    	String monthly;
    	String yearly;
    	ResultSet rs;
    	Double min, max, avg;
    	
    	// Set the content type to HTML
		response.setContentType( "text/html" );
		// Get the output stream from the response object
		PrintWriter out = response.getWriter();
		// Get the session (Create a new one if required)
		session = request.getSession(true);
	
		// Check if the user is logged in
		user = (User) session.getAttribute("user");
		if (user == null) {
			String errormsg = "Please login before accessing account info";
		    session.setAttribute("status", errormsg);
		    response.sendRedirect("/oos-cmput391/login.jsp");
		    return;
		}
		
		try {
		    tframe = (String) request.getParameter("tframe");
		} catch (Exception e) {}
		// handle displaying statistical values
		
		
		out.println("<!DOCTYPE html><html><head><title>"+
			    "Scientist OLAP Report</title>");
	    out.println("<center><jsp:include page=\"includes/header.jsp\"/></center></head>");
	    out.println("<body><h3>Sensors Data Analysis, grouped "+tframe+"</h3>");
	    
		try {
		    olap = new OLAPCommands(tframe);
		    rs = olap.getAnalysis(user.getPerson_id());
		    
			while(rs != null && rs.next()) {
				sensor_id = rs.getLong("sensor_id");
				location = rs.getString("location");
				daily = String.valueOf(rs.getTimestamp(3));
				weekly = String.valueOf(rs.getTimestamp(4));
				monthly = String.valueOf(rs.getTimestamp(5));
				yearly = String.valueOf(rs.getTimestamp(6));
				avg = rs.getDouble(7);
				min = rs.getDouble(8);
				max = rs.getDouble(9);
				
				out.println(sensor_id + "|" + location + "|" + daily + "|" + avg + "|" + min + "|" + max);
			}

		    
		    if (tframe == null)
		    	tframe.equals(tframe); // just to trigger that exception, SO BAD
	
		    

		    //out.println(olap.getSensorsScalarDaily());
		} catch (Exception e) {
		    //e.printStackTrace();
		    // can only be the null pointer exception that I purposely trigger
		}
		
		out.println("</body></html>");
    }
}
