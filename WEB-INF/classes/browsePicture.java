import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;

import util.Db;
import util.Photo;

/**
 *  Extension of the simple servlet used to display photos
 *  Taken from Li-Yan Yuan's example code
 * 
 *  This servlet sends one picture stored in the table below to the client 
 *  who requested the servlet.
 *
 *   picture( photo_id: integer, title: varchar, place: varchar, 
 *            sm_image: blob,   image: blob )
 *
 *  The request must come with a query string as follows:
 *    GetOnePic?12:        sends the picture in sm_image with photo_id = 12
 *    GetOnePic?big12: sends the picture in image  with photo_id = 12
 *
 *  @author  Alexander Wong, Li-Yan Yuan
 *
 */
public class browsePicture extends HttpServlet 
    implements SingleThreadModel {
    private Db database;
    private HttpSession session;
    private String username;
    private Photo photo;
    
    /**
     *    This method first gets the query string indicating PHOTO_ID,
     *    and then executes the query 
     *          select image from yuan.photos where photo_id = PHOTO_ID   
     *    Finally, it sends the picture to the client
     */

    public void doGet(HttpServletRequest request,
		      HttpServletResponse response)
	throws ServletException, IOException {
	session = request.getSession(true);
	username = (String) session.getAttribute("username");
	
	if (username == null) {
	    String errormsg = "Please login before viewing photos";
	    session.setAttribute("err", errormsg);
	    response.sendRedirect("/c391proj/login.jsp");
	    return;
	}

	//  construct the query  from the client's QueryString
	String picid  = request.getQueryString();
	String truepicid;
	if (picid.startsWith("big")) {
	    truepicid = picid.substring(3);
	} else {
	    truepicid = picid;
	}
	database = new Db();
	database.connect_db();
	photo = database.getPhotoDesc(Integer.parseInt(truepicid));
	database.close_db();
	// Check that the photo is not private being viewed by someone else
	if (photo.getPermitted() == 2) {
	    // If private, only owner or admin can see photo
	    System.out.println("photo ownername = '"+photo.getOwnerName()+"'");
	    System.out.println("username = '"+username+"'");
	    if (!username.equals(photo.getOwnerName()) ^ 
		!username.equals("admin")) {
		// do nothing, continue
	    } else {
		String errormsg = "You are not permitted to view this photo";
		session.setAttribute("err", errormsg);
		response.sendRedirect("/c391proj/index.jsp");
		return;
	    }
	} else if (photo.getPermitted() != 1) {
	    // Photo is part of a group, make sure only group members
	    // or admin can see this photo
	    database.connect_db();
	    ArrayList<String> users = database.
		get_users_from_group(photo.getPermitted());
	    database.close_db();
	    if (!users.contains(username) ^ !username.equals("admin")) {}
	    else {
		String errormsg = "You are not permitted to view this photo";
		session.setAttribute("err", errormsg);
		response.sendRedirect("/c391proj/index.jsp");
		return;
	    }
	}
	
	ResultSet rset = null;
	PrintWriter out = response.getWriter();
	database.connect_db();
	if (picid.startsWith("big")) {
	    database.imageCountViewInc(picid.substring(3), username);
	    rset = database.getPhoto(picid.substring(3));
	} else
	    rset = database.getThumbnail(picid);
       	try {
	    if (rset.next()) {
		response.setContentType("image/gif");
		InputStream input = rset.getBinaryStream(1);	    
		int imageByte;
		while((imageByte = input.read()) != -1) {
		    out.write(imageByte);
		}
		input.close();
	    } 
	    else 
		out.println("no picture available");
	} catch( Exception ex ) {
	    out.println(ex.getMessage() );
	}
	// to close the connection
	finally {
	    database.close_db();
	    out.close();
	}
    }
}
