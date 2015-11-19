package util;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.*;



public class Db {
	static final String USERNAME = "wkchoi";
	static final String PASSWORD = "Kingfreak95";
	// JDBC driver name and database URL
	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
	//static final String DB_URL = "jdbc:oracle:thin:@localhost:1525:CRS";

	private Connection conn;
	private Statement stmt;
	FileOutputStream image;

	// Connect jdbc
	public int connect_db() {
		try {
			Class drvClass = Class.forName(DRIVER_NAME);
			DriverManager.registerDriver((Driver) drvClass.newInstance());
			this.conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			this.stmt = conn.createStatement();
			return 1;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void close_db() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet execute_stmt(String query) {
		try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Integer execute_update(String query) {
		try {
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// Get a user object from user_name
	public User getUser (String user_name) {
		User user = null;
	    String password = null;
	    String role = null;
	    long person_id = 0;
	    String date_registered = null;
		
	    String query = "select * from users "
			+ "where user_name = '" + user_name + "'";
		ResultSet rs = execute_stmt(query);
		try {
			while(rs != null && rs.next()) {
				user_name = rs.getString("user_name");
				password = rs.getString("password");
				role = rs.getString("role");
				person_id = rs.getLong("person_id");
				date_registered = String.valueOf(rs.getTimestamp("date_registered"));
			}
			user = new User(user_name, password, role, person_id, date_registered);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	// Get password from username
	public String getPassword (String username) {
		String password = "";
		String query = "select password from users where user_name = '" + username + "'";
		ResultSet rs = execute_stmt(query);
		try {
			while(rs != null && rs.next()) {
				password = (rs.getString(1)).trim();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	// Change password
	public int updatePassword(String username, String password) {
		String query = "update users set password = '"+password+
		    "' where user_name = '" + username + "'";
		return execute_update(query);
	}
	
	// Check if username exists in database
	public boolean userExists(String username) {
		String userquery = "select * from users where user_name='" + 
			username + "'";
		ResultSet rset = execute_stmt(userquery);
		try {
			if (!rset.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Change username
	public Integer updateUsername (String user_name, String n_user_name) {
		String query = "update users set user_name = '" + 
				n_user_name + "' where user_name = '" + user_name + "'";
		return execute_update(query);
	}

	// Insert a user
	public Integer addUser(String username, String password) {
		String query = "insert into users values ('" + 
			username + "', '" + 
			password + "', sysdate)";
		return execute_update(query);
	}

	// Return a person object
	public Person getPerson (long person_id) {
		Person person = null;
	    String first_name = null;
	    String last_name = null;
	    String address = null;
	    String email = null;
	    String phone = null;
		
	    String query = "select * from persons "
				+ "where person_id = '" + person_id + "'";
	    ResultSet rs = execute_stmt(query);
			
		// Get data from rs
		try {
			while (rs.next()) { 
				first_name = rs.getString("first_name");
				last_name = rs.getString("last_name");
				address = rs.getString("address");
				email = rs.getString("email");
				phone = rs.getString("phone");
			}
			person = new Person(person_id, first_name, last_name, address, email, phone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return person;
	}

	// Check email exists
	public boolean emailExists(String email) {
		String query = "select email from persons where email = '" + 
			email + "'";
		ResultSet rs = execute_stmt(query);
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Change email
	public Integer updateEmail (long person_id, String email) {
		String query = "update persons set email = '" + 
				email + "' where person_id = '" + person_id + "'";
		return execute_update(query);
	}
	
	// Change first_name
	public Integer updateFname(long person_id, String fname) {
		String query = "update persons set first_name = '" + 
				fname + "' where person_id = '" + person_id + "'";
		return execute_update(query);
	}

	// Change last_name
	public Integer updateLname(long person_id, String lname) {
		String query = "update persons set last_name = '" + 
				lname + "' where person_id = '" + person_id + "'";
		return execute_update(query);
	}

	// Change address
	public Integer updateAddress(long person_id, String address) {
		String query = "update persons set address = '" + 
				address + "' where person_id = '" + person_id + "'";
		return execute_update(query);
	}

	// Change phone
	public Integer updatePhone(long person_id, String phone) {
		String query = "update persons set phone = '" + 
				phone + "' where person_id = '" + person_id + "'";
		return execute_update(query);
	}


    // Returns the resultset of the search by keywords and date
    public ResultSet getResultsByDateAndKeywords(long person_id, String fromdate, String todate, String keywords) {
    	String query = "SELECT sensor_id FROM "
	    					+ "("
	    					+ "SELECT * FROM SENSORS "
	    					+ "WHERE location LIKE '%" + keywords + "%' "
	    					+ "OR sensor_type LIKE '%" + keywords + "%' "
	    					+ "OR description LIKE '%" + keywords + "%' "
	    					+ "UNION "
	    					+ "SELECT * FROM AUDIO_RECORDINGS "
	    					+ "WHERE recording_id LIKE '%" + keywords + "%' "
	    					+ "OR description LIKE '%" + keywords + "%' "
	    					+ "OR recorded_data LIKE '%" + keywords + "%' "
	    					+ "OR sensor_id LIKE '%" + keywords + "%' "
	    					+ "AND date_created BETWEEN '" + fromdate + "' AND '" + todate + "' "
	    					+ "UNION "	
	    					+ "SELECT * FROM IMAGES "
	    					+ "WHERE image_id LIKE '%" + keywords + "%' "
	    					+ "OR description LIKE '%" + keywords + "%' "
	    					+ "OR recorded_dat LIKE '%" + keywords + "%' "
	    					+ "OR sensor_id LIKE '%" + keywords + "%' "
	    					+ "AND date_created BETWEEN '" + fromdate + "' AND '" + todate + "' "
	    					+ "UNION "
	    					+ "SELECT * FROM SCALAR_DATA "
	    					+ "WHERE id LIKE '%" + keywords + "%' "
	    					+ "OR value LIKE '%" + keywords + "%' "
	    					+ "OR sensor_id LIKE '%" + keywords + "%' "
	    					+ "AND date_created BETWEEN '" + fromdate + "' AND '" + todate + "') "
	    					+ ") "
    					+ "WHERE sensor_id in (SELECT sensor_id FROM subsriptions WHERE person_id = '" + person_id + "')";

        return execute_stmt(query);
    }
    
    // Delete a sensor
    public Integer delete_sensor(long sensor_id) {
    	String query = "delete from sensors where sensor_id = " + sensor_id + "'";
    	return execute_update(query);
    }
    
    // Delete both user and person
    public void delete_user_person(long person_id) {
    	String query_user = "delete from users where person_id = " + person_id + "'";
    	String query_person  = "delete from persons where person_id = " + person_id + "'";
    	
    	execute_update(query_user);
    	execute_update(query_person);
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
		
		try{
			SimpleDateFormat format = new SimpleDateFormat( "YYYY-MM-DD HH:mm:ss" );
			java.util.Date date_time = format.parse(dateTimeLocal );
			java.sql.Timestamp sqlDate = new java.sql.Timestamp( date_time.getTime());
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO audio_recordings VALUES(" + recording_id +
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
    
    public String addScalarData (int sensor_id, String[] date_created, double value) {
		
		String dateTimeLocal = null;
		String[] time = date_created[1].split(":");
		if (time.length != 3) {
			dateTimeLocal = date_created[0]  + " " + date_created[1] + ":00";
		}
		else {
			dateTimeLocal = date_created[0] + " " + date_created[1];
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat( "DD/MM/YYYY HH:mm:ss" );
			java.util.Date date_time = format.parse(dateTimeLocal );
			java.sql.Timestamp sqlDate = new java.sql.Timestamp( date_time.getTime());
		
			String maxID = "SELECT max(id) FROM scalar_data";
			
			ResultSet rs = execute_stmt(maxID);
			rs.next();
			int id = rs.getInt(1);
			id++;
			
			PreparedStatement statement = conn.prepareStatement("INSERT INTO scalar_data VALUES(" + id +
					"," + sensor_id +",?,"+ value +")"); 
			statement.setTimestamp(1,sqlDate);
			statement.executeQuery();
			statement.executeUpdate("commit");
			return "Scalar Data file Added";
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return "Scalar Data file was not Added";
	}
    
    public String newSensor (int sensor_id, String location, String sensor_type, String description) {
		
		try {
			String sid = "SELECT sensor_id FROM sensors WHERE sensor_id = " + sensor_id;
			ResultSet rs = execute_stmt(sid);
			if (rs.next()){
				return "SORRY! Sensor with ID " + sensor_id + " is already in the system.";
			}
			else {
				String insertNewSensor = "INSERT INTO sensors Values(" + sensor_id + ",'" + location + 
						"','" + sensor_type + "','" + description + "')";
		    	execute_update(insertNewSensor);	
		    	return "New Sensor with ID " + sensor_id + " created.";
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			}
		return " ";
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
			ImageIO.write(img, "jpg", baos);
			original = new ByteArrayInputStream(baos.toByteArray());
			
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
			ImageIO.write(scaledImg, "jpg", baos2);
			InputStream scaled = new ByteArrayInputStream(baos2.toByteArray());
		
			PreparedStatement statement = conn.prepareStatement("INSERT INTO images VALUES(" + image_id +
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
	
	public List<String> getSensorA_id_list() {
		List<String> sensor_id = new ArrayList<String>();
		try{
			String sid = "SELECT sensor_id FROM sensors WHERE sensor_type = 'a' ORDER BY sensor_id";
			ResultSet rs = execute_stmt(sid);
					while(rs.next()) {
						sensor_id.add(String.valueOf(rs.getInt(1)));
					}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return sensor_id;
	}
	
	public List<String> getSensorI_id_list() {
		List<String> sensor_id = new ArrayList<String>();
		try{
			String sid = "SELECT sensor_id FROM sensors WHERE sensor_type = 'i' ORDER BY sensor_id";
			ResultSet rs = execute_stmt(sid);
					while(rs.next()) {
						sensor_id.add(String.valueOf(rs.getInt(1)));
					}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return sensor_id;
	}
	
	public List<String> recording_list() {
		List<String> recordingList = new ArrayList<String>();
		try{
			String list = "SELECT * FROM audio_recordings ORDER BY recording_id";
			ResultSet rs = execute_stmt(list);
			while (rs.next()){
				recordingList.add("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
									"<td>" + String.valueOf(rs.getInt(4))+"</td>"+
									"<td>" + rs.getString(5) + "</td>" + 
									"<td><audio controls><source src=\"/oos-cmput391/audioservlet?id="+rs.getInt(1)+"\" type=\"audio/wav\"></audio></td></tr>");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return recordingList;
	}
	
	public List<String> image_list() {
		List<String> imageList = new ArrayList<String>();
		
		try{
			String list = "SELECT * FROM images ORDER BY image_id";
			ResultSet rs = execute_stmt(list);
			while (rs.next()){			
				imageList.add("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
									"<td>" + rs.getString(4)+"</td>"+
									"<td><center><image src=\"/oos-cmput391/imageservlet?full=no&id="+
											rs.getInt(1)+"\"><center></td>"+
									"<td><center><a href=\"/oos-cmput391/imageservlet?full=yes&id="+rs.getInt(1)+"\">Download Full Size Image!<center></td></tr>");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return imageList;
	}
	
	public List<String> scalar_list() {
		List<String> scalarList = new ArrayList<String>();
		try{
			String list = "SELECT * FROM scalar_data ORDER BY id";
			ResultSet rs = execute_stmt(list);
			while (rs.next()){
				scalarList.add("<tr><td>" + String.valueOf(rs.getInt(1))+"</td>"+
									"<td>" + String.valueOf(rs.getInt(2))+"</td>"+
									"<td>" + String.valueOf(rs.getTimestamp(3))+"</td>"+
									"<td>" + String.valueOf(rs.getInt(4)) + "</td></tr>");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return scalarList;
	}
	
}
