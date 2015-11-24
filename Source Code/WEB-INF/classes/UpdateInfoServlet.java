import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import util.Db;
import util.User;
import util.Person;


// Servlet for changing info in Persons table
public class UpdateInfoServlet extends HttpServlet {
   
	/**
	 * Handles HTTP POST request when pressing button
	 */
	public void doPost( HttpServletRequest request, 
			HttpServletResponse response )
					throws ServletException, IOException {
		// Invoke doGet to process this request
		doGet( request, response );
	}
	
    /**
	 * Handles HTTP GET request from form's values
	 */
	public void doGet( HttpServletRequest request, HttpServletResponse response ) 
						throws ServletException, IOException {
		HttpSession session;
	    String message = "";
	    Db database;
	    User user;
	    Person person;
	    String user_name;
	    long person_id;
	    String password;
	    String first_name;
	    String last_name;
	    String address;
	    String email;
	    String phone;
	    
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

		database = new Db();
		database.connect_db();
		
		person_id = Long.parseLong(request.getParameter("person_id"));
		person = database.getPerson(person_id);
		
		// Getters person
		first_name = person.getFirst_name();
		last_name = person.getLast_name();
		address = person.getAddress();
		email = person.getEmail();
		phone = person.getPhone();
		
		// GETs from form's input
		String n_first_name = request.getParameter("firstname");
		String n_last_name = request.getParameter("lastname");
		String n_address = request.getParameter("address");
		String n_email = request.getParameter("email");
		String n_phone = request.getParameter("phone");
		
		// Compare with database
		if (!email.equals(n_email)) {
		    // Check if the email already exists:
		    if (database.emailExists(n_email)) {
		    	message = message + "Email '" + n_email + "' already taken by another user.<br>";
		    } else {
		    	database.updateEmail(person_id, n_email);
		    	message = message + "Email changed from '" + email + "' to '" + n_email + "'<br>";
		    }
		}
		if (!first_name.equals(n_first_name)) {
		    database.updateFname(person_id, n_first_name);
		    message = message + "First Name changed from '" + first_name + "' to '" + n_first_name + "'<br>";
		}
		if (!last_name.equals(n_last_name)) {
		    database.updateLname(person_id, n_last_name);
		    message = message + "Last Name changed from '" + last_name + "' to '" + n_last_name + "'<br>";
		}
		if (!address.equals(n_address)) {
		    database.updateAddress(person_id, n_address);
		    message = message + "Address changed from '" + address + "' to '" + n_address + "'<br>";
		}
		if (!phone.equals(n_phone)) {
		    database.updatePhone(person_id, n_phone);
		    message = message + "Phone changed from '" + phone + "' to '" + n_phone + "'<br>";
		}
		
	    database.close_db();
		session.setAttribute("status", message);
	    session.setAttribute("user", user);
	    
	    if (request.getParameter("person_id") != null) {
	    	response.sendRedirect("/oos-cmput391/change_info.jsp?updateType=info&selected=" + person_id);
	    } else {
	    	response.sendRedirect("/oos-cmput391/change_info.jsp?updateType=info");
	    }
	}
}
