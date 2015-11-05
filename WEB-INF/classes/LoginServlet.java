import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*; 
import util.Db;

   
public class LoginServlet extends HttpServlet {
	private HttpSession session;
	private String user;
	private String pass;
	private String password;

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
			HttpSession session = request.getSession( true );

			// Get user and pasword from login page
			user = request.getParameter( "user" );
			pass = request.getParameter("pass");

			Db database = new Db();
			database.connect_db();
			password = database.get_password(user);
			database.close_db();

			if(pass.equals(password)) {
				out.println("<p><b>Login successful.</b></p>");
				session.setAttribute("username", eUsername);
				response.sendRedirect("/oos-cmput391/index.jsp");
			} else {
				String errormsg = "Username or Password invalid.";
				session.setAttribute("err", errormsg);
				response.sendRedirect("/oos-cmput391/login.jsp");
			} 

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

	/**
	 * Returns a brief description about this servlet
	 */
	public String getServletInfo() {
		return "Servlet that stores user's name in the Session";
	}
}

