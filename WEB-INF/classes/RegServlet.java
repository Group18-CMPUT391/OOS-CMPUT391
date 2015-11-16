import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Db;



public class RegServlet extends HttpServlet {
	
	private HttpSession session;
	
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		String fname = null;
		String lname = null;
		String address = null;
		String phone = null;
		String email = null;
		String role = null;
		String uname = null;
		String pass = null;
		String nuser = null;
		int personID;

		
		Db database = new Db();
		database.connect_db();
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession( true );

		fname = request.getParameter( "fname" );
		lname = request.getParameter( "lname" );
		address = request.getParameter( "address" );
		phone = request.getParameter( "phone" );
		email = request.getParameter( "email" );
		role = request.getParameter( "role" );
		uname = request.getParameter( "uname" );
		pass = request.getParameter( "pass" );
		nuser = request.getParameter( "nuser" );

		try {
			
			
			

			ResultSet rs;

			if (nuser.equals("yes")){
				String checkmail = "SELECT email FROM persons WHERE email = '" + email + "'" ;
				rs = database.execute_stmt(checkmail);
				set html parameter to null

				if (rs.next()){
					session.setAttribute("err","Email " + email + " is already in the system.");
					response.sendRedirect("/oos-cmput391/new_user.jsp");
				}
				else {
					
					String maxID = "SELECT max(person_id) FROM persons";
					rs = database.execute_stmt(maxID);
					rs.next();
					personID = rs.getInt(1);
					personID++;
					
					String insertNewPerson = "INSERT INTO persons Values('" + personID + "','" + fname + "','" + lname + "','" + address + "','" + email + "','" + phone + "')";
					database.execute_stmt(insertNewPerson);

			    	
			    	String insertNewUser = "INSERT INTO users Values('" + uname + "','" + pass + "','" + role + "','" + personID + "', CURRENT_TIMESTAMP)";
			    	database.execute_stmt(insertNewUser);

			    	
			    	session.setAttribute("err","Added User " + uname + " to the system.");
					response.sendRedirect("/oos-cmput391/new_user.jsp");
				}
			}
			else if (nuser.equals("no")) {
				String checkuname = "SELECT user_name FROM users WHERE user_name = '" + uname + "'" ;
				rs = database.execute_stmt(checkuname);

				
				if (rs.next()){
					session.setAttribute("err","Added User " + uname + " to the system.");
					response.sendRedirect("/oos-cmput391/existing_user.jsp");
					
				}
				else {
					String checkmail2 = "SELECT email FROM persons WHERE email = '" + email + "'" ;
					rs = database.execute_stmt(checkmail2);

					if (rs.next()){
						String getID = "SELECT person_id FROM persons WHERE email = '" + email + "'" ;
						rs = database.execute_stmt(getID);

						
						while (rs.next()) {
							personID = rs.getInt("person_id");
							
							String insertNewUserOldID = "INSERT INTO users Values('" + uname + "','" + pass + "','" + role + "','" + personID + "', CURRENT_TIMESTAMP)";
							database.execute_stmt(insertNewUserOldID);

					    	
					    	session.setAttribute("err","Added User " + uname + " to the system.");
							response.sendRedirect("/oos-cmput391/existing_user.jsp");
				    	}
					}
					else {
						session.setAttribute("err","Email " + email + " is not in the system.");
						response.sendRedirect("/oos-cmput391/existing_user.jsp");
					}
					
					
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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


////    /**
////     * Returns a brief description about this servlet
////     */
////    public String getServletInfo() {
////	return "Servlet that stores user's name in the Session";
////    }
//}
//
