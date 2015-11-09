package util;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sensor {
	
	Db database = new Db();
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
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
				return "SORRY Sensor with ID " + sensor_id + " is already in the system.";
			}
			else {
				String insertNewSensor = "INSERT INTO sensors Values('" + sensor_id + "','" + location + 
						"','" + sensor_type + "','" + description + "')";
		    	database.execute_update(insertNewSensor);	
		    	return "New sensor with ID " + sensor_id + " created.";
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			}
		return " ";
	}
	
	public String uploadAudio (int recording_id, int sensor_id, String date_created, 
			int length, String description, InputStream recorded_data) {

		try {
			
			SimpleDateFormat format = new SimpleDateFormat( "YYYY-MM-DD" );  // United States style of format.
			java.util.Date myDate = format.parse( date_created );
			java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
			
			Connection dbConnection = getDBConnection();
			//Statement stmt = dbConnection.createStatement();
			PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO audio_recordings VALUES(" + recording_id +
					"," + sensor_id +",?,"+ length + ",'"+ description +"', ?)");   
			statement.setDate(1,sqlDate);
			statement.setBlob(2,recorded_data);
			statement.executeQuery();
			statement.executeUpdate("commit");
			return "Audio File Added";
			 
//			String insertaudio = "INSERT INTO audio_recordings Values(?,?,?,?,?,?)";
//			PreparedStatement statement = dbConnection.prepareStatement(insertaudio);
//			statement.setInt(1, recording_id);
//	        statement.setInt(2, sensor_id);
//	        statement.setDate(3, java.sql.Date.valueOf(date_created.replace("T"," ")));
//	        statement.setInt(4, length);
//	        statement.setString(5, description);
//	        statement.setBlob(6, empty_blob());
//	        statement.executeUpdate();
//	        statement.executeUpdate("commit");
//	        return "Audio File Added";
	        
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return "Audio File was not added.";
	}
	
	
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

