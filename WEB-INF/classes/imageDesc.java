import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Db;
import util.Photo;

/**
 * Class for updating a photo's descriptors.
 * Called by imageDesc.jsp
 */

public class imageDesc extends HttpServlet {

    protected void doGet(HttpServletRequest request, 
			 HttpServletResponse response) 
	throws ServletException, IOException {
	HttpSession session = null;
	String username = "";
	String date = "";
	String location = "";
	String subject = "";
	String description = "";
	String permission = "";
	int photo_id = 0;
	
	try {
            /*
             * Check if user is logged in, if not redirect
             */
            response.setContentType("text/html");
            session = request.getSession(true);
            username = (String) session.getAttribute("username");
            if(username == null) {
                response.sendRedirect("login.jsp");
            }
	    photo_id = (Integer) session.getAttribute("photo_id");
	} catch (Exception e) {
            System.out.println(e.getMessage());
	}

	/* get variables */
	try {
	    date = request.getParameter("date");
	    location = request.getParameter("location");
	    subject = request.getParameter("subject");
	    description = request.getParameter("description");
	    permission = request.getParameter("security");
	} catch (Exception e) {
            System.out.println(e.getMessage());
	}

	int perm_int = Integer.parseInt(permission);
	Photo photo = new Photo(photo_id, username, date, location,
				subject, description, perm_int);

	Db database = new Db();
	database.connect_db();
	database.update_photo_desc(photo);

	response.sendRedirect("index.jsp");
    }

}

