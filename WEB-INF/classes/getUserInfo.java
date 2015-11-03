import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;
import util.User;

/**
 * Class for getting data within the persons table, such as:
 * username, firstname, lastname, address, email, phone, and
 * changing password.
 * @author alexanderwwong
 */

public class getUserInfo extends HttpServlet {
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
	// Remove all attributes before beginning
	session.removeAttribute("email");
	session.removeAttribute("phone");
	session.removeAttribute("firstname");
	session.removeAttribute("lastname");
	session.removeAttribute("address");
	session.removeAttribute("err");

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
	
	session.setAttribute("email", eEmail);
	session.setAttribute("phone", ePhone);
	session.setAttribute("firstname", eFirstname);
	session.setAttribute("lastname", eLastname);
	session.setAttribute("address", eAddress);
	response.sendRedirect("/c391proj/account_settings.jsp");
	
	return;
    }
}