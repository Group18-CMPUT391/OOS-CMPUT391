import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;

/**
 * Dummy servlet for loging in.
 * @author alexanderwwong
 */

public class loginservlet extends HttpServlet {
    private HttpSession session;
    private String eUsername;
    private String ePassword;
    private String tPassword;

    protected void service(HttpServletRequest request, 
			   HttpServletResponse response) 
	throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	session = request.getSession(true);

        try {
            eUsername=request.getParameter("user");
            ePassword=request.getParameter("pass");

	} catch(Exception ex) {
	    out.println("<hr>" + ex.getMessage() + "<hr>");
	}

	Db database = new Db();
	database.connect_db();
	tPassword = database.get_password(eUsername);
	database.close_db();

	if(ePassword.equals(tPassword)) {
	    out.println("<p><b>Login successful.</b></p>");
	    session.setAttribute("username", eUsername);
	    response.sendRedirect("/c391proj/index.jsp");
	} else {
	    String errormsg = "Username or Password invalid.";
	    session.setAttribute("err", errormsg);
	    response.sendRedirect("/c391proj/login.jsp");
	} 
	out.close();
    }
}