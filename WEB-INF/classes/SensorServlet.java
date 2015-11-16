import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.Db;
import util.Sensor;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;


public class SensorServlet extends HttpServlet {
	
	private HttpSession session;
	
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession( true );
		
		int sensor_id = 0, recording_id = 0, length = 0, image_id = 0, id = 0;
		double value = 0;
		String location = null, sensor_type = null, description = null, 
				date_created = null, ext = null;
		InputStream fileContent = null;

		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Sensor sens = new Sensor();
			
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					if(item.getFieldName().equals("sensor_id"))
			        {   
						sensor_id=Integer.parseInt(item.getString());
			        }
					else if(item.getFieldName().equals("location"))
			        {   
						location=item.getString();
			        }
					else if(item.getFieldName().equals("sensor_type"))
			        {   
						sensor_type=item.getString();
			        }
					else if(item.getFieldName().equals("description"))
			        {   
						description=item.getString();
			        }
					else if(item.getFieldName().equals("recording_id"))
			        {   
						recording_id=Integer.parseInt(item.getString());
			        }
					else if(item.getFieldName().equals("image_id"))
			        {   
						image_id=Integer.parseInt(item.getString());
			        }
					else if(item.getFieldName().equals("date_created"))
			        {   
						date_created=item.getString();
			        }
					else if(item.getFieldName().equals("length"))
			        {   
						length=Integer.parseInt(item.getString());
			        }
					else if(item.getFieldName().equals("value"))
			        {   
						value=Double.parseDouble(item.getString());
			        }
				}
				else {
					fileContent = item.getInputStream();
					
					ext = FilenameUtils.getExtension(item.getName());
				}
			}
			
			String type = request.getParameter( "type" );
			if (type.equals("sensor")) {
				String querrymessage = sens.newSensor(sensor_id, location, sensor_type, description);
				session.setAttribute("err", querrymessage);
				response.sendRedirect("/oos-cmput391/sensor.jsp");
			}
			else if (type.equals("audio_recordings")) {
				if(ext.equalsIgnoreCase("wav")){
					String querrymessage = sens.uploadAudio (recording_id, sensor_id, 
							date_created.split("T"), length, description, fileContent);
					session.setAttribute("err", querrymessage);
					response.sendRedirect("/oos-cmput391/sensor.jsp");
				}
				else {
					session.setAttribute("err", "This is not a wav file");
					response.sendRedirect("/oos-cmput391/sensor.jsp");
				}
				
			}
			else if (type.equals("images")) {
				if(ext.equalsIgnoreCase("jpg")){
					String querrymessage = sens.uploadImage (image_id, sensor_id, 
							date_created.split("T"), description, fileContent);
					session.setAttribute("err", querrymessage);
					response.sendRedirect("/oos-cmput391/sensor.jsp?type=images");
				}
				else {
					session.setAttribute("err", "This is not a jpg file");
					response.sendRedirect("/oos-cmput391/sensors.jsp");
				}
				
			}
			else if (type.equals("scalar")) {
				String querrymessage = sens.addScalarData (id, sensor_id, date_created.split("T"),value);
				session.setAttribute("err", querrymessage);
				response.sendRedirect("/oos-cmput391/sensor.jsp");
			}

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