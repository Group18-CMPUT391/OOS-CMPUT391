
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
public class DeleteServlet extends HttpServlet {
   

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
			if (type.equals("deleteUser")){
				String []checkbox = request.getParameterValues("usercheckbox");
				
				
				String querrymessage =db.deleteUser(checkbox);
				session.setAttribute("err", querrymessage);
				
				response.sendRedirect("/oos-cmput391/register.jsp?usrType=delete");
							}
			else if (type.equals("deleteSensor")){
			
			
				String []checkbox1 = request.getParameterValues("sensorcheckbox");
				String querrymessage= db.deleteSensors(checkbox1);
				session.setAttribute("err", querrymessage);
				response.sendRedirect("/oos-cmput391/sensor.jsp");
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
