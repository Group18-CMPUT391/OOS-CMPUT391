import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import util.Db;
import util.User;
import util.Person;


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

		// Getters user
		user_name = user.getUser_name();
		person_id = user.getPerson_id();
		
		database = new Db();
		database.connect_db();
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
		response.sendRedirect("/oos-cmput391/change_info.jsp");
	}

}
    /*
    protected void service(HttpServletRequest request,
			   HttpServletResponse response)
	throws ServletException, IOException {
	Integer isPopulated = 1;

	response.setContentType("text/html");
	session = request.getSession(true);
	
	// Check if the user is logged in
	eUsername = (String) session.getAttribute("username");
	if (eUsername == null) {
	    String errormsg = "Please login before accessing account info";
	    session.setAttribute("err", errormsg);
	    response.sendRedirect("/c391proj/login.jsp");
	    return;
	}

	// Check if we must populate the fields
	try {
	    isPopulated = (Integer) session.getAttribute("isPopulated");
	} catch (NullPointerException e) {
	    isPopulated = 1;
	}
	// Grab the user and populate the fields for the user 
	Db database = new Db();
	database.connect_db();
	user = database.get_user(eUsername);
	database.close_db();
	
	// Parse the fields and throw it into the necessary strings
	// Don't grab the password, user will only be allowed to set
	eEmail = user.getEmail();
	ePhone = user.getPhone();
	eFirstname = user.getFname();
	eLastname = user.getLname();
	eAddress = user.getAddress();
	
	// Populate the database fields with the necessary new info
	String returnmsg = "";
	database.connect_db();
	
	String nEmail = request.getParameter("email");
	if (!nEmail.equals(eEmail)) {
	    // Check if the email already exists:
	    if (database.emailExists(nEmail)) {
		returnmsg = returnmsg + "Email '" + 
		    nEmail + "' already taken by another user.<br>";
		session.setAttribute("email", eEmail);
	    } else {
		database.setEmail(eUsername, nEmail);
		returnmsg = returnmsg + "Email changed from '" +
		    eEmail + "' to '" + nEmail + "'<br>";
		session.setAttribute("email", nEmail);
	    }
	}
	
	    String nFirstname = request.getParameter("firstname");
	if (!nFirstname.equals(eFirstname)) {
	    database.updateFname(eUsername, nFirstname);
	    returnmsg = returnmsg + "First Name changed from '" +
		eFirstname + "' to '" + nFirstname + "'<br>";
	    session.setAttribute("firstname", nFirstname);
	}
	
	String nLastname = request.getParameter("lastname");
	if (!nLastname.equals(eLastname)) {
	    database.updateLname(eUsername, nLastname);
	    returnmsg = returnmsg + "Last Name changed from '" +
		eLastname + "' to '" + nLastname + "'<br>";
	    session.setAttribute("lastname", nLastname);
	}
	
	String nAddress = request.getParameter("address");
	if (!nAddress.equals(eAddress)) {
	    database.updateAddress(eUsername, nAddress);
	    returnmsg = returnmsg + "Address changed from '" +
		eAddress + "' to '" + nAddress + "'<br>";
	    session.setAttribute("address", nAddress);
	}
	
	String nPhone = request.getParameter("phone");
	if (!nPhone.equals(ePhone)) {
	    database.updatePhone(eUsername, nPhone);
	    returnmsg = returnmsg + "Phone changed from '" +
		ePhone + "' to '" + nPhone + "'<br>";
	    session.setAttribute("phone", nPhone);
	}
	
	String nPass = request.getParameter("pass");
	String nPass2= request.getParameter("pass2");
	// Check if new passwords match 
	if (!nPass.equals(nPass2) && nPass != null && nPass2 != null){
	    returnmsg = returnmsg + "New passwords do not match<br>";
	} else {
	    database.updatePass(eUsername, nPass);
	    returnmsg = returnmsg + "Sucessfully updated password<br>";
	}
	
	
	database.close_db();
	session.setAttribute("err", returnmsg);
	response.sendRedirect("/c391proj/account_settings.jsp");
	
	return;
    }
    */
