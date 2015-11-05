import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegServlet extends HttpServlet {
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_USER = "hbtruong";
	private static final String DB_PASSWORD = "qwerty123456";
	
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
			Connection dbConnection = getDBConnection();
			Statement stmt = dbConnection.createStatement();
			ResultSet rs;

			if (nuser.equals("yes")){
				String checkmail = "SELECT email FROM persons WHERE email = '" + email + "'" ;
				rs = stmt.executeQuery(checkmail);

				if (rs.next()){
					out.println("Email " + email + " is already in the system.");
				}
				else {
					out.println("Added User " + uname + " to the system.");
					String maxID = "SELECT max(person_id) FROM persons";
					rs = stmt.executeQuery(maxID);
					rs.next();
					personID = rs.getInt(1);
					personID++;

					String insertNewPerson = "INSERT INTO persons Values('" + personID + "','" + fname + "','" + lname + "','" + address + "','" + email + "','" + phone + "')";
					stmt.executeUpdate(insertNewPerson);

					String insertNewUser = "INSERT INTO users Values('" + uname + "','" + pass + "','" + role + "','" + personID + "', CURRENT_TIMESTAMP)";
					stmt.executeUpdate(insertNewUser);
				}
			}
			else if (nuser.equals("no")) {
				String checkuname = "SELECT user_name FROM users WHERE user_name = '" + uname + "'" ;
				rs = stmt.executeQuery(checkuname);

				if (rs.next()){
					out.println("User" + uname + " is already in the system.");
				}
				else {
					String getID = "SELECT person_id FROM persons WHERE email = '" + email + "'" ;
					rs = stmt.executeQuery(getID);

					while (rs.next()) {
						personID = rs.getInt("person_id");

						String insertNewUserOldID = "INSERT INTO users Values('" + uname + "','" + pass + "','" + role + "','" + personID + "', CURRENT_TIMESTAMP)";
						stmt.executeUpdate(insertNewUserOldID);

						out.println("Added User " + uname + " to the system.");
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(
                              DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
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
