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
    
	public void doPost( HttpServletRequest request, 
		HttpServletResponse response )
		throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet( request, response );
	}
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
		HttpSession session;
		User user;
		String selected_sensor = null;
		long sensor_id = 0;
    	String selected_year = null;
    	//String yearly;
    	ArrayList<String> years = new ArrayList<String>() ;
    	ResultSet rs;
    	Double min = 0.0, max = 0.0, avg = 0.0;
    	
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
	    
	    // Check diferrent submits
	    String submit = (String) request.getParameter("submit");
	    selected_sensor = (String) request.getParameter("selected_sensor");
	    selected_year = (String) request.getParameter("selected_year");
	    
	    try {
 		    olap = new OLAPCommands();
 		    rs = olap.getAnalysisYearly(user.getPerson_id(), selected_sensor);

 			while(rs != null && rs.next()) {
 				sensor_id = rs.getLong("sensor_id");
 				//yearly = rs.getString("yearly");

 				
 				//years.add(yearly);

 				avg = rs.getDouble("average");
 				min = rs.getDouble("min");
 				max = rs.getDouble("max");
 				
 			}
 		    
 		} catch (Exception e) {
 		    e.printStackTrace();
 		}
	    
	    
	    if(submit != null && !submit.isEmpty()) {
			/*try {
			    tframe = (String) request.getParameter("tframe");
			} catch (Exception e) {}
			
			*/

	    	session.setAttribute("selected_sensor", selected_sensor);
			session.setAttribute("avg", avg);
			session.setAttribute("min", min);
			session.setAttribute("max", max);
			
	    	//session.setAttribute("years", years);
			
	    	if (submit.equals("2")) {
		    	session.setAttribute("selected_year", selected_year);
		    } 	
	    	
	    	response.sendRedirect("/oos-cmput391/data_analysis.jsp");
	    }


		
		out.println("</body></html>");
		
		olap.close_OLAP();
    }
}
