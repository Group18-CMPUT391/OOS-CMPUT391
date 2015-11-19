import java.io.*;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import java.text.*;
import java.net.*;

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
        ResultSet rset = null;
        
        // Set the content type to HTML
 		response.setContentType( "text/html" );
 		// Get the output stream from the response object
 		PrintWriter out = response.getWriter();
 		// Get the session (Create a new one if required)
 		session = request.getSession(true);
     		
        database.connect_db();   
        String keywords = request.getParameter("query");
        String fromDate = request.getParameter("fromdate");
        String toDate = request.getParameter("todate");
        String fromdatesql = null;
        String todatesql = null;
        String pid = "";
        
        // Check if the user is logged in
 		user = (User) session.getAttribute("user");
 		if (user == null) {
 			String errormsg = "Please login before accessing account info";
 		    session.setAttribute("status", errormsg);
 		    response.sendRedirect("/oos-cmput391/login.jsp");
 		    return;
 		}
     		
        /*
         * Changing format from yyyy-MM-dd to dd-MMM-yy for sql
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");
        
        try {
            Date date = sdf.parse(fromDate);
            Date date1 = sdf.parse(toDate);
            fromdatesql = sdf1.format(date);
            todatesql = sdf1.format(date1);
        } catch (Exception e) {
            e.getMessage();
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
        
        /*
         * The user has to input from and to dates otherwise
         * only keyword search to get resultset of query
         */
        if (!(keywords.equals(""))) {
            rset = database.getResultsByDateAndKeywords(user.getPerson_id(), fromdatesql, todatesql, keywords);
            out.println("Your results for '" + keywords + "' between "
                        + fromDate + " and " + toDate + ":\n");
        } else {
            out.println("<b>Please enter a search query</b>");
        }
        out.println("<br>");

        /*
         * Displays the results
         */
        try {
            while(rset.next()){
                pid = (rset.getObject(2)).toString();
                // specify the servlet for the image
                out.println("<a href=\"/oos-cmput391/browsePicture?big"
                            + pid + "\">");
                // specify the servlet for the thumbnail
                out.println("<img src=\"/oos-cmput391/browsePicture?"
                            + pid + "\"></a>");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        
        database.close_db();
        out.print("</body>");
        out.print("</html>");
        out.close();
    }
}
