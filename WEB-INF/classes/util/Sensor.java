package util;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.*;

import java.util.Date;


public class Sensor {
	
	Db database = new Db();
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	//private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1525:CRS";
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_USER = "wkchoi";
	private static final String DB_PASSWORD = "Kingfreak95";
	

	
	
//	public String getSensors {
//		
//	}

	public String newSensor (int sensor_id, String location, String sensor_type, String description) {
		
		database.connect_db();
		try {
			String sid = "SELECT sensor_id FROM sensors WHERE sensor_id = " + sensor_id;
			ResultSet rs = database.execute_stmt(sid);
			if (rs.next()){
				return "SORRY! Sensor with ID " + sensor_id + " is already in the system.";
			}
			else {
				String insertNewSensor = "INSERT INTO sensors Values(" + sensor_id + ",'" + location + 
						"','" + sensor_type + "','" + description + "')";
		    	database.execute_update(insertNewSensor);	
		    	return "New Sensor with ID " + sensor_id + " created.";
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			}
		return " ";
	}
	
	public String uploadAudio (int recording_id, int sensor_id, String[] date_created, 
			int length, String description, InputStream recorded_data) {
			
		String dateTimeLocal = null;
		String[] time = date_created[1].split(":");
		if (time.length != 3) {
			dateTimeLocal = date_created[0]  + " " + date_created[1] + ":00";
		}
		else {
			dateTimeLocal = date_created[0] + " " + date_created[1];
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat( "YYYY-MM-DD HH:mm:ss" );
			java.util.Date date_time = format.parse(dateTimeLocal );
			java.sql.Timestamp sqlDate = new java.sql.Timestamp( date_time.getTime());
			
			Connection dbConnection = getDBConnection();
			//Statement stmt = dbConnection.createStatement();
			PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO audio_recordings VALUES(" + recording_id +
					"," + sensor_id +",?,"+ length + ",'"+ description +"', ?)");   
			statement.setTimestamp(1,sqlDate);
			statement.setBlob(2,recorded_data);
			statement.executeQuery();
			statement.executeUpdate("commit");
			
			return "Audio File Added. ";
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return "Audio File was not Added.";
	}
	
	public String uploadImage (int image_id, int sensor_id, String[] date_created,
			String description, InputStream recorded_data) {
		
		String dateTimeLocal = null;
		String[] time = date_created[1].split(":");
		if (time.length != 3) {
			dateTimeLocal = date_created[0]  + " " + date_created[1] + ":00";
		}
		else {
			dateTimeLocal = date_created[0] + " " + date_created[1];
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat( "YYYY-MM-DD HH:mm:ss" );
			java.util.Date date_time = format.parse(dateTimeLocal );
			java.sql.Timestamp sqlDate = new java.sql.Timestamp( date_time.getTime());
			
			InputStream original = recorded_data;
			BufferedImage img = ImageIO.read(original);
			BufferedImage scaledImg = Thumbnails.of(img).scale(0.25).asBufferedImage();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledImg, "jpg", baos);
			InputStream scaled = new ByteArrayInputStream(baos.toByteArray());
		
			Connection dbConnection = getDBConnection();
			//Statement stmt = dbConnection.createStatement();
			PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO images VALUES(" + image_id +
					"," + sensor_id +",?,'"+ description +"', ?, ?)");   
			statement.setTimestamp(1,sqlDate);
			statement.setBlob(2,scaled);
			statement.setBlob(3,original);
			statement.executeQuery();
			statement.executeUpdate("commit");
			return "Image File Added";
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		
		return "Image File was not Added";
	}
	
	
	public String addScalarData (int id, int sensor_id, String[] date_created, double value) {
		database.connect_db();
		String dateTimeLocal = null;
		String[] time = date_created[1].split(":");
		if (time.length != 3) {
			dateTimeLocal = date_created[0]  + " " + date_created[1] + ":00";
		}
		else {
			dateTimeLocal = date_created[0] + " " + date_created[1];
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat( "YYYY-MM-DD HH:mm:ss" );
			java.util.Date date_time = format.parse(dateTimeLocal );
			java.sql.Timestamp sqlDate = new java.sql.Timestamp( date_time.getTime());
			
			Connection dbConnection = getDBConnection();
			//Statement stmt = dbConnection.createStatement();
			PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO scalar_data VALUES(" + id +
					"," + sensor_id +",?,"+ value +")"); 
			statement.setTimestamp(1,sqlDate);
			statement.executeQuery();
			statement.executeUpdate("commit");
			return "Scalar Data Added";
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return "Scalar Data was not Added";
	}
	

		//String sid = "SELECT sensor_id FROM sensors WHERE sensor_type = " + sensor_type;
	
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		}catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(
					DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			}
		return dbConnection;
	}
//
//	public String DeleteSensor {
//	}
	
}

