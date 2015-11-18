import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import util.OLAPCommands;

/**
 * Handles the data cube stuff, all the stats about our photo things here
 */
public class AnalysisServlet extends HttpServlet {
    private OLAPCommands olap;
    private String tframe;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
		HttpSession session;
		PrintWriter out = response.getWriter();
	
		try {
		    tframe = (String) request.getParameter("tframe");
		} catch (Exception e) {}
		// handle displaying statistical values
		
		try {
		    olap = new OLAPCommands(tframe);
		    if (tframe == null)
		    	tframe.equals(tframe); // just to trigger that exception, SO BAD
	
		    out.println("<!DOCTYPE html><html><head><title>"+
			    "Scientist OLAP Report</title>");
		    out.println("<center><jsp:include page=\"includes/header.jsp\"/></center>");
		    out.println("<h3>Sensors Data Analysis, grouped "+tframe+"</h3>");
		    out.println(olap.getSensorsScalarDaily());
		} catch (Exception e) {
		    //e.printStackTrace();
		    // can only be the null pointer exception that I purposely trigger
		}
		
		out.println("</body></html>");
    }
}
