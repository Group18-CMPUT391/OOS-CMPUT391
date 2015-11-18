import java.util.*;
import java.io.*;
import java.sql.*;

import oracle.sql.*;
import oracle.jdbc.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import util.Db;


public class ImageServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		Db database = new Db();
		database.connect_db();
		String id = request.getParameter( "id" );
		String full = request.getParameter( "full" );
		Blob blob;
		try {
			String query = "SELECT * FROM images WHERE image_id =" + id;
			ResultSet rs = database.execute_stmt(query);
			byte[] bytes;
			while(rs.next()) {
				response.setContentType("image/jpg");
				response.setHeader("Content-Disposition","attachment;filename=" + String.valueOf(rs.getInt(1)) + "_" + 
						String.valueOf(rs.getInt(2))+ "_" + String.valueOf(rs.getDate(3)) + ".jpg" );
				if (full.equals("yes")){
					bytes = rs.getBytes(6);
				}
				else {
					bytes = rs.getBytes(5);
				}
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
				OutputStream out = response.getOutputStream();
				ImageIO.write(bufferedImage, "jpg", out);
				out.close();
			}
			database.close_db();
			
		}catch (Exception e) {
				System.out.println(e.getMessage());
				}
		
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