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
import util.Analysis_Date;


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

    	String tframe = null;
    	String sel_yr=null;
    	Double min = 0.0, max = 0.0, avg = 0.0;

    	ResultSet rs;
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
	    selected_year=(String) request.getParameter("selected_year");
	    tframe=(String) request.getParameter("tframe");
 		 olap = new OLAPCommands();
 		 int check=olap.checkFact();
 		 if (check>0){
 			 olap.drop_Fact();}
 		olap.setup_Fact(user.getPerson_id());


 		    
// 		} catch (Exception e) {
// 		    e.printStackTrace();
// 		}
	    
	    
	    if(submit != null && !submit.isEmpty()) {
			/*try {
			    tframe = (String) request.getParameter("tframe");
			} catch (Exception e) {}
			
			*/
// returns rows of data which hold yearly averages, minimums, and maximums as well as the distinct years
	    	ArrayList<Analysis_Date> years_date = olap.getAnalysisYearly(user.getPerson_id(), selected_sensor);
	    	ArrayList<String> years=olap.getYearsScalar(selected_sensor);
//sets attributes from front side
	    	session.setAttribute("years_date",years_date);
	    	session.setAttribute("years",years);
	    	session.setAttribute("selected_sensor",selected_sensor);
// if year has been selected. set selected_year and tframe
	    	if (submit.equals("2")) {
		    	session.setAttribute("selected_year", selected_year);
		    	session.setAttribute("tframe",tframe);
		    } 
// if time frame has been selected, run all drilldowns for all time measurements and store to session
	    	if (submit.equals("3")){
	    			
	    			session.setAttribute("selected_year", selected_year);
	    			session.setAttribute("tframe",tframe);
	    			ArrayList<Analysis_Date> day= olap.drillDowntoDay(selected_year,Long.parseLong(selected_sensor));
	    			ArrayList<String>days_in=olap.getDailyScalar(selected_sensor);
	    			session.setAttribute("day",day);
	    			session.setAttribute("day_in",days_in);

	    			ArrayList<Analysis_Date> week=olap.drillDowntoWeek(selected_year,Long.parseLong(selected_sensor));
	    			ArrayList<String>weeks_in=olap.getWeeklyScalar(selected_sensor);
	    			session.setAttribute("week",week);
	    			session.setAttribute("weeks_in",weeks_in);

	    			ArrayList<Analysis_Date> month=olap.drillDowntoMonth(selected_year,Long.parseLong(selected_sensor));
	    			ArrayList<String>months_in=olap.getMonthlyScalar(selected_sensor);
	    			
	    			session.setAttribute("month",month);
	    			session.setAttribute("months_in",months_in);

	    			ArrayList<Analysis_Date> quarter=olap.drillDowntoQuarter(selected_year,Long.parseLong(selected_sensor));
	    			ArrayList<String>quarter_in=olap.getQuarterlyScalar(selected_sensor);
	    			session.setAttribute("quarter",quarter);
	    			session.setAttribute("quarter_in",quarter_in);
	    		}
	    	
	    	
//	    	    }
	    	
	    	response.sendRedirect("/oos-cmput391/data_analysis.jsp?submit="+submit);
	    	

	    }
		

		olap.close_OLAP();
	    
    }
}
