import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.*;
import java.io.*;
import java.sql.*;

import oracle.sql.*;
import oracle.jdbc.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.*;
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
			String query = "SELECT thumbnail, recoreded_data FROM images WHERE image_id =" + id;
			ResultSet rs = database.execute_stmt(query);
			while(rs.next()) {
				response.setContentType("image/jpg");
				if (full.equals("yes")){
					blob = rs.getBlob(2);
				}
				else {
					blob = rs.getBlob(1);
				}
				
				int blobLength = (int) blob.length();
				byte[] data = blob.getBytes(1, blobLength);
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(data));
				OutputStream out = response.getOutputStream();
				ImageIO.write(bufferedImage, "jpg", out);
				out.close();
			}
				
			database.close_db();
//				Blob blob = rs.getBlob("thumbnail");
//				int blobLength = (int) blob.length();
//				byte[] data = blob.getBytes(1, blobLength);
//				response.setContentType("image/jpeg");
//				OutputStream o = response.getOutputStream();
//				o.write(data);
			
		}catch (SQLException e) {
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