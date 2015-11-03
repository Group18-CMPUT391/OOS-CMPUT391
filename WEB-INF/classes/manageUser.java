import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;
import util.User;

/**
 * Class for setting data within the persons table, such as:
 * username, firstname, lastname, address, email, phone, and
 * changing password.
 * @author alexanderwwong
 */

public class manageUser extends HttpServlet {
    private HttpSession session;
    private User user;
    private String eUsername;
    private String ePassword;
    private String eFirstname;
    private String eLastname;
    private String eAddress;
    private String eEmail;
    private String ePhone;

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
}