import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Db;
import util.Sensor;

public class SensorServlet extends HttpServlet {
	
	private HttpSession session;
	
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession( true );
		
		
		String sensor_id = request.getParameter( "sensor_id" );
		String location = request.getParameter( "location" );
		String sensor_type = request.getParameter( "sensor_type" );
		String description = request.getParameter( "description" );
		int sID = Integer.parseInt(sensor_id);
		String type = request.getParameter( "type" );
		
		Sensor sens = new Sensor();
		
		
		if (type.equals("sensor")) {
			String querrymessage = sens.newSensor(sID, location, sensor_type, description);
			session.setAttribute("err", querrymessage);
			response.sendRedirect("/oos-cmput391/uploadsensor.jsp?type=sensor");
		}
			
	}
	
	/**
	    * Handles HTTP POST request
	    */
	public void doPost( HttpServletRequest request, 
		HttpServletResponse response )
		throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet( request, response );
	}
}