import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import util.OLAPCommands;

/**
 * Handles the data cube stuff, all the stats about our photo things here
 */
public class adminStats extends HttpServlet {
    private OLAPCommands olap;
    private String tframe;
    
    public void doGet(HttpServletRequest request,
		      HttpServletResponse response)
	throws ServletException, IOException {
	HttpSession session;
	PrintWriter out = response.getWriter();
	out.println("<!DOCTYPE html><html><head><title>"+
		    "Admin OLAP Stats</title>");
	try {
	    session = request.getSession(true);
	    String username = (String) session.getAttribute("username");
	    if (!username.equals("admin")) {
		String msg = "You are not allowed to access admin stats.";
		session.setAttribute("err", msg);
		response.sendRedirect("/c391proj/index.jsp");
		return;
	    }
	    request.getRequestDispatcher("includes/header.jsp").
		include(request, response);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	// Handle timeframe stuff
	out.println("</head><body>");
	out.println("<form name='choosetime' action='adminStats' "+
		    "method='PUT'>");
	out.println("<table><tr><th>Timeframe</th><td>");
	out.println("<input type='radio' name='tframe' value='daily'>"+
		    "Daily</input>");
	out.println("<input type='radio' name='tframe' value='weekly'>"+
		    "Weekly</input>");
	out.println("<input type='radio' name='tframe' value='monthly'>"+
		    "Monthly</input>");
	out.println("<input type='radio' name='tframe' value='yearly'>"+
		    "Yearly</input>");
	out.println("</td></tr>");
	out.println("<tr><td><input type='submit'></input></td></tr>");
	out.println("</table>");
	out.println("</form>");
	try {
	    tframe = (String) request.getParameter("tframe");
	} catch (Exception e) {}
	// handle displaying statistical values
	try {
	    olap = new OLAPCommands(tframe);
	    if (tframe == null)
		tframe.equals(tframe); // just to trigger that exception, SO BAD
	    out.println("<h3>Users Registered Count, grouped "+tframe+"</h3>");
	    out.println(olap.getRegUsersCount());
	    out.println("<h3>Images Uploaded Count, grouped "+tframe+"</h3>");
	    out.println(olap.getDateUploadImagesCount());
	    out.println("<h3>Images Description Count, grouped "+tframe+
			"</h3>");
	    out.println(olap.getImgSubjCount());
	    out.println("<h3>Images User Count, grouped "+tframe+"</h3>");
	    out.println(olap.getImgUsrCount());
	    out.println("<hr><h3>Users Registed Details, grouped "+tframe+
			"</h3>");
	    out.println(olap.getRegUsers());
	    out.println("<h3>Images Uploaded Details, grouped "+tframe+"</h3>");
	    out.println(olap.getDateUploadImages());
	} catch (Exception e) {
	    //e.printStackTrace();
	    // can only be the null pointer exception that I purposely trigger
	}
	out.println("</body></html>");
    }
}