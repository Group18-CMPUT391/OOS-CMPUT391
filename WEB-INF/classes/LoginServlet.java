import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*; 
import util.Db;

   
public class LoginServlet extends HttpServlet {
	private HttpSession session;
	private String username;
	private String password;
	private String db_password;

	/**
	 * Handles HTTP GET request
	 */
	public void doGet( HttpServletRequest request, 
			HttpServletResponse response )
					throws ServletException, IOException {

		// Set the content type to HTML
		response.setContentType( "text/html" );
		// Get the output stream from the response object
		PrintWriter out = response.getWriter();
		// Get the session (Create a new one if required)
		HttpSession session = request.getSession(true);

		// Get user and pasword from login page
		username = request.getParameter("user");
		password = request.getParameter("pass");

		Db database = new Db();
		database.connect_db();
		db_password = database.getPassword(username);

		if(password.equals(db_password)) {
			out.println("<p><b>Login successful.</b></p>");
			session.setAttribute("user", database.getUser(username));
			response.sendRedirect("/oos-cmput391/index.jsp");
		} else {
			String errormsg = "Username or Password invalid.";
			session.setAttribute("status", errormsg);
			response.sendRedirect("/oos-cmput391/login.jsp");
		} 

		database.close_db();

		// Flush the output stream
		out.flush();

		// Close the output stream
		out.close();
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

