import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Db;
import org.apache.commons.fileupload.*;
import util.User;
public class SubscriptionServlet extends HttpServlet {
   

	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
						 throws ServletException, IOException{
		HttpSession session;
	    String message = "";
	    Db database;
	    User user;
	    String user_name;
	    String password;

	    
		// Set the content type to HTML
		response.setContentType( "text/html" );
		// Get the output stream from the response object
		PrintWriter out = response.getWriter();
		// Get the session (Create a new one if required)
		session = request.getSession(true);
	
		// Check if the user is logged in
		user = (User) session.getAttribute("user");

		long user_id= user.getPerson_id();
		String type=request.getParameter("type");
		
		
		try{
				Db db = new Db();
				db.connect_db();
			if (type.equals("deleteSubscription")){
				String []checkbox = request.getParameterValues("deletecheckbox");
				
				
				String querrymessage =db.deleteSubscription(checkbox,user_id);
				session.setAttribute("err", querrymessage);
				
				response.sendRedirect("/oos-cmput391/subscription.jsp?type=deleteSubscription");
							}
			else if (type.equals("addSubscription")){
			
			
				String []checkbox1 = request.getParameterValues("addcheckbox");
				String querrymessage= db.newSubscription(checkbox1,user_id);
				session.setAttribute("err", querrymessage);
				response.sendRedirect("/oos-cmput391/subscription.jsp?type=addSubscription");
								}
				db.close_db();}		

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
        
     
	public void doPost( HttpServletRequest request, 
		HttpServletResponse response )
		throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet( request, response );
	}
}

