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
import javax.sound.sampled.*;

import util.Db;


public class AudioServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		Db database = new Db();
		database.connect_db();
		String id = request.getParameter( "id" );
		
		try{
			String query = "SELECT * FROM audio_recordings WHERE recording_id =" + id;
			ResultSet rs = database.execute_stmt(query);
			
			while (rs.next()) {
				response.setContentType("audio/wav");
				response.setHeader("Content-Disposition","attachment;filename=" + String.valueOf(rs.getInt(1)) + "_" + 
						String.valueOf(rs.getInt(2))+ "_" + String.valueOf(rs.getDate(3)) + ".wav" );
				byte[] bytes = rs.getBytes(6);
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes );
				AudioInputStream stream = AudioSystem.getAudioInputStream(bais);
				OutputStream out = response.getOutputStream();
				AudioSystem.write(stream,AudioFileFormat.Type.WAVE, out);
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