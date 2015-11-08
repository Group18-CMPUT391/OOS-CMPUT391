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
	
//	public String UploadAudio (String location, String sensor_type, String description) {
//		String maxID = "SELECT max(sensor_id) FROM sensor";
//		rs = database.execute_stmt(maxID);
//		rs.next();
//		int sensor_id; = rs.getInt(1);
//		sensor_id;++;
//		
//		String maxAID = "SELECT max(sensor_id) FROM sensor";
//		rs = database.execute_stmt(maxAID);
//		rs.next();
//		int max_Aid; = rs.getInt(1);
//		max_Aid;++;
//		
//		
//		
//		String insertNewUser = "INSERT INTO audio_recordings Values('" + max_Aid + "', '" + sensor_id + 
//				"', 'CURRENT_TIMESTAMP', '" + length + "', '" + description + "', '" + recorded_data + "')";
//    	database.execute_stmt(insertNewUser);
//		
//		
//	}
//
//	public String DeleteSensor {
//	}
	
}

