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

	private Connection conn;
	private Statement stmt;
	private FileOutputStream image;

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

	// Close connection
	public void close_db() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Execute a query and return a ResultSet 
	public ResultSet execute_stmt(String query) {
		try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Execute an update and return 1 if success
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
		
	    String query = "select * from users where user_name = '" + user_name + "'";
		ResultSet rs = execute_stmt(query);
		try {
			if (rs != null && rs.next()) {
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
			if (rs != null && rs.next()) {
				password = (rs.getString(1)).trim();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	// Change password
	public int updatePassword(String username, String password) {
		String query = "update users set password = '"+password +
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

	// Get all sensors' id of user
	public List<String> getSensors(long person_id) {
		List<String> sensors = new ArrayList<String>();
		String sensor;
		
		String query = "SELECT sensor_id FROM subscriptions WHERE person_id = " + person_id;
		ResultSet rs = execute_stmt(query);
		try {
			while (rs != null && rs.next()) {
				sensor = rs.getString("sensor_id");
				sensors.add(sensor);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return sensors;
	}
	
    // Returns the resultset of the search by keywords and date
    public ResultSet getResultsSensor(long person_id, String sensor_type, String fromdate, String todate, String keywords, String location) {
    	ResultSet results = null;
    	String query = null;
    	
    	try {
			if (sensor_type.equals("a")) {
				query = "SELECT au.* FROM audio_recordings au "
						+ "JOIN subscriptions su on au.sensor_id = su.sensor_id "
						+ "JOIN sensors s on au.sensor_id = s.sensor_id "
						+ "WHERE su.person_id =" + person_id 
						+ "AND au.description LIKE '%"+keywords+"' "
								+ "AND s.location LIKE '%"+location+"'"
										+ "AND au.date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD')"
												+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD')"
														+ "ORDER BY au.recording_id";
				results = execute_stmt(query);
			}
			else if (sensor_type.equals("i")) {
				query = "SELECT i.* FROM images i "
						+ "JOIN subscriptions su on i.sensor_id = su.sensor_id "
						+ "JOIN sensors s on i.sensor_id = s.sensor_id "
						+ "WHERE su.person_id =" + person_id 
						+ "AND i.description LIKE '%"+keywords+"' "
								+ "AND s.location LIKE '%"+location+"' "
										+ "AND i.date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
												+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD')"
														+ "ORDER BY i.image_id";
				results = execute_stmt(query);
			}
			else if (sensor_type.equals("s")) {
				if(isNumber( keywords )){
					query = "SELECT s.id,s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + person_id
							+ "AND s.value =" + Float.parseFloat(keywords)
							+ "AND se.location LIKE '%"+location+"' "
									+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
											+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
													+ "ORDER BY s.id";
					results = execute_stmt(query);
				}
				else if(keywords.equals("")) {
					query = "SELECT s.id,s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + person_id
							+ "AND se.location LIKE '%"+location+"' "
									+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
											+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
													+ "ORDER BY s.id";
				}
				else{
					query = "SELECT s.id,s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + person_id
							+ "AND s.value =null "
							+ "AND se.location LIKE '%"+location+"' "
									+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
											+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
													+ "ORDER BY s.id";
				}
				
				results = execute_stmt(query);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
        return results;
    }
    
    //get result for all the sensor data that the user has subscribed to
    public ResultSet getResultAll(long person_id, String fromdate, String todate, String keywords, String location) {
    	ResultSet results = null;
    	String  query = null,query2 = null;

    	try{
    		query = "SELECT au.recording_id, au.sensor_id,au.date_created,au.description,s.sensor_type "
    				+ "FROM audio_recordings au "
    				+ "JOIN subscriptions su on au.sensor_id = su.sensor_id "
    				+ "JOIN sensors s on au.sensor_id = s.sensor_id "
    				+ "WHERE au.description LIKE '%"+keywords+"' "
    				+ "AND s.location LIKE '%"+location+"' "
    				+ "AND su.person_id =" +person_id
    				+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
    				+ "AND TO_DATE('"+todate+"','YYYY-MM-DD') "
    				+ "UNION ALL "
    				+ "SELECT i.image_id as id,i.sensor_id ,i.date_created,i.description, s.sensor_type "
    				+ "FROM images i "
    				+ "JOIN subscriptions su on i.sensor_id = su.sensor_id "
    				+ "JOIN sensors s on i.sensor_id = s.sensor_id "
    				+ "WHERE i.description LIKE '%"+keywords+"' "
    				+ "AND s.location LIKE '%"+location+"' "
    				+ "AND su.person_id ="+person_id + ""
    				+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
    				+ "AND TO_DATE('"+todate+"','YYYY-MM-DD') "
    				+ "UNION ALL ";
    		
    		if(isNumber( keywords )){
    			query2 ="SELECT sc.id,sc.sensor_id,sc.date_created,CAST(sc.value as varchar(128)),s.sensor_type "
        				+ "FROM scalar_data sc "
        				+ "JOIN subscriptions su on sc.sensor_id = su.sensor_id "
        				+ "JOIN sensors s on sc.sensor_id = s.sensor_id "
        				+ "WHERE sc.value = " + Float.parseFloat(keywords)
        				+ "AND s.location LIKE '%"+location+"'"
        				+ "AND su.person_id ="+person_id
        				+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
        				+ "AND TO_DATE('"+todate+"','YYYY-MM-DD')";
    		}
    		else if(keywords.equals("")){
    			query2 ="SELECT sc.id,sc.sensor_id,sc.date_created,CAST(sc.value as varchar(128)),s.sensor_type "
	    				+ "FROM scalar_data sc "
	    				+ "JOIN subscriptions su on sc.sensor_id = su.sensor_id "
	    				+ "JOIN sensors s on sc.sensor_id = s.sensor_id "
	    				+ "WHERE s.location LIKE '%"+location+"'"
	    				+ "AND su.person_id ="+person_id
	    				+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
	    				+ "AND TO_DATE('"+todate+"','YYYY-MM-DD')";
    		}
    		else {
    			query2 ="SELECT sc.id,sc.sensor_id,sc.date_created,CAST(sc.value as varchar(128)),s.sensor_type "
	    				+ "FROM scalar_data sc "
	    				+ "JOIN subscriptions su on sc.sensor_id = su.sensor_id "
	    				+ "JOIN sensors s on sc.sensor_id = s.sensor_id "
	    				+ "WHERE s.location LIKE '%"+location+"'"
	    				+ "AND su.person_id ="+person_id
	    				+ "AND s.value =null "
	    				+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
	    				+ "AND TO_DATE('"+todate+"','YYYY-MM-DD')";
    		}
    		
    		results = execute_stmt(query + query2);
    		
    	}catch (Exception e) {
			System.out.println(e.getMessage());
			}
    	
    	return results;
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
    
    //Upload an audio file in "wav" form to the DB
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
    
    //Batch add scalar data to the DB
    public String addScalarData (int sensor_id, String[] date_created, float value) {
		
    	
		String dateTimeLocal = null;
		String[] time = date_created[1].split(":");
		if (time.length != 3) {
			dateTimeLocal = date_created[0]  + " " + date_created[1] + ":00";
		}
		else {
			dateTimeLocal = date_created[0] + " " + date_created[1];
		}
		
		try {

			String maxID = "SELECT max(id) FROM scalar_data";
			
			ResultSet rs = execute_stmt(maxID);
			rs.next();
			int id = rs.getInt(1);
			id++;
			
			String insert = ("INSERT INTO scalar_data VALUES(" + id + "," + sensor_id +", "
					+ "TO_DATE('"+dateTimeLocal+"', 'DD/MM/YYYY HH24:MI:SS'),"+ value +")"); 
			execute_update(insert);

			return "Scalar Data file Added";
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return "Scalar Data file was not Added";
	}
    
    //Creates new sensors in the DB
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
    
    //Upload and image in "jpg" form to the DB
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
	
	//Get list of sensors ID that are audio from the DB and store it in a list
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
	
	//Get all sensors ID that are images and stores it in a list
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
	
	//Get all sensors data that are audio and stores it in a list
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
	
	//Get all sensors data that are images and stores it in a list
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
	
	//Get all sensors data that are scalar data and stores it in a list
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

	//Removes a scientist's subscription
	public String deleteSubscription(String[] sensor_ids,long person_id){	

		int count=0;
		String str="null";
		for(int i=0;i<sensor_ids.length;i++){

				long sensor_id=Long.valueOf(sensor_ids[i]);
				
				String deletesid = "DELETE FROM subscriptions WHERE sensor_id = " + sensor_id+" AND person_id = "+ person_id;
		    		int check=execute_update(deletesid);
				
				if (check!=0){
					
		    			count=count+1;}
				}
		if (count>0){
			str= String.valueOf(count)+" subscriptions have been deleted";}
			
		
		
	return str;

	}
	
	//Scientist subscription method
	public String newSubscription(String[] sensor_ids,long person_id){
		int count=0;
		String str="null";
		for(int i=0;i<sensor_ids.length;i++){

				long sensor_id=Long.valueOf(sensor_ids[i]);
				
				String insertNewSensor = "INSERT INTO subscriptions Values("+sensor_id + "," + person_id + ")";
		    		int check=execute_update(insertNewSensor);
				
				if (check!=0){	
		    			count=count+1;}
				}
		if (count>0){
			str= String.valueOf(count)+" subscriptions have been added";}
	return str;
	}
	
	//Check the scientist's subscriptions
	public int checkSubscriptions(long person_id){
		int ret=-1;
		String check = "SELECT * FROM subscriptions WHERE person_id="+person_id;
		try{
			ResultSet rs=execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (SQLException e) {
			}
		return ret;
	}
	//Get a list of subscriptions a scientist can subscribe to
	public ArrayList<Sensors> printAddSubscriptions(long user_id){
		int chk=checkSubscriptions(user_id);
		ArrayList<Sensors> ret_list=new ArrayList<Sensors>();
		ArrayList<Long> sensor_id_list=new ArrayList<Long>();
		String us_id_b= "SELECT sensor_id,location,sensor_type,description FROM sensors WHERE sensor_id != ";
		String sensor_check="SELECT sensor_id FROM subscriptions WHERE person_id="+user_id;
		
		try{
			ResultSet sensor_chk=execute_stmt(sensor_check);
			while (sensor_chk.next()){
				sensor_id_list.add(sensor_chk.getLong("sensor_id"));
				}
			
			for(int fl=0;fl<(sensor_id_list.size()-1);fl++){
				us_id_b=us_id_b+sensor_id_list.get(fl)+" AND sensor_id !=";
				}
			us_id_b=us_id_b+sensor_id_list.get(sensor_id_list.size());
			
		}
		catch (Exception e){}


		if(chk==0){
			us_id_b = "SELECT sensor_id,location,sensor_type,description FROM sensors";
			try{
				ResultSet rs_b = execute_stmt(us_id_b);
				while (rs_b.next()){
					ret_list.add(new Sensors(rs_b.getLong("sensor_id"),rs_b.getString("location"),rs_b.getString("sensor_type"),rs_b.getString("description")));
				
				
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				}
			}
		else{
			
			try{
				us_id_b=us_id_b+sensor_id_list.get(sensor_id_list.size()-1);
				
				ResultSet rs_b = execute_stmt(us_id_b);
				while (rs_b.next()){
					ret_list.add(new Sensors(rs_b.getLong("sensor_id"),rs_b.getString("location"),rs_b.getString("sensor_type"),rs_b.getString("description")));
				
				
				}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				}
			}
		
		return ret_list; }
	
	//Get a list of subscriptions a scientist has
	public ArrayList<Sensors> printDeleteSubscriptions(long person_id){
		int chk=checkSubscriptions(person_id);
		ArrayList<Sensors> ret_list=new ArrayList<Sensors>();
		String us_id_b = "SELECT sen.sensor_id,sen.location,sen.sensor_type,sen.description FROM subscriptions sub, sensors sen WHERE sub.person_id ="+String.valueOf(person_id)+ " AND sen.sensor_id =sub.sensor_id";

		try{
			ResultSet rs_b =execute_stmt(us_id_b);
			while (rs_b.next()){ 
				ret_list.add(new Sensors(rs_b.getLong("sensor_id"),rs_b.getString("location"),rs_b.getString("sensor_type"),rs_b.getString("description")));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return ret_list; }

	public ArrayList<Sensors> printSensors(){
		ArrayList<Sensors> ret_list=new ArrayList<Sensors>();
		String us_id_b = "SELECT * FROM sensors";

		try{
			ResultSet rs_b =execute_stmt(us_id_b);
			while (rs_b.next()){ 
				ret_list.add(new Sensors(rs_b.getLong("sensor_id"),rs_b.getString("location"),rs_b.getString("sensor_type"),rs_b.getString("description")));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return ret_list; }

	public ArrayList<User> printUsers(){
		ArrayList<User> ret_list=new ArrayList<User>();
		String us_id_b = "SELECT * from users";

		try{
			ResultSet rs_b =execute_stmt(us_id_b);
			while (rs_b.next()){ 
				ret_list.add(new User(rs_b.getString("user_name"),rs_b.getString("password"),rs_b.getString("role"),rs_b.getLong("person_id"),String.valueOf(rs_b.getDate("date_registered"))));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
		return ret_list; }

	public int checkSubscriptionsSensor(long sensor_id){
		int ret=-1;
		String check = "SELECT * FROM subscriptions WHERE sensor_id="+sensor_id;
		try{
			ResultSet rs=execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (SQLException e) {
			return -2;
			}
	
			 


		
				
		return ret;
			
		
		
	}
	public int checkAudio(long sensor_id){
		int ret=-1;
		String check = "SELECT * FROM audio_recordings WHERE sensor_id="+sensor_id;
		try{
			ResultSet rs=execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (SQLException e) {
			return -2;
			}
	
			 


		
				
		return ret;
			
		
		
	}

	public int checkScalar(long sensor_id){
		int ret=-1;
		String check = "SELECT * FROM scalar_data WHERE sensor_id="+sensor_id;
		try{
			ResultSet rs=execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (SQLException e) {
			return -2;
			}
	
			 


		
				
		return ret;
			
		
		
	}
	public int checkImage(long sensor_id){
		int ret=-1;
		String check = "SELECT * FROM images WHERE sensor_id="+sensor_id;
		try{
			ResultSet rs=execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (SQLException e) {
			return -2;
			}
	
			 


		
				
		return ret;
			
		
		
	}




	public String deleteUser(String[] userbox){
		String str="";
		for(int i=0;i<userbox.length;i++){
			try{
				
				int chksubs=checkSubscriptions(Long.valueOf(userbox[i]));
				if (chksubs>0){
					String deletesid = "DELETE FROM subscriptions WHERE person_id = " +Long.valueOf(userbox[i]);
		    			int check=execute_update(deletesid);}
				String delete_user="DELETE FROM users WHERE person_id= "+Long.valueOf(userbox[i]);
				//String delete_person="DELETE FROM persons WHERE person_id= "+Long.valueOf(userbox[i]);
				execute_update(delete_user);
				//execute_update(delete_person);

			}catch (Exception e) {
				System.out.println(e.getMessage());
				}		
		
		
		str= String.valueOf(userbox.length)+" users have been deleted";}
			
		
		
	return str;


	}
	public String deleteSensors(String[] sensorbox){
		int sub_count=0;
		int sens_count=0;
		String str="null";
		int count=0;
		ArrayList<Long> sensor_ids=new ArrayList<Long>();
		for(int i=0;i<sensorbox.length;i++){
			sensor_ids.add(Long.valueOf(sensorbox[i]));
			int chksubs=checkSubscriptionsSensor(Long.valueOf(sensorbox[i]));
			if (chksubs>0){
				String deletesid = "DELETE FROM subscriptions WHERE sensor_id = " +Long.valueOf(sensorbox[i]);
		    		int check=execute_update(deletesid);}
		try{
			for (int j=0;j<sensor_ids.size();j++){
				String type_list="SELECT sensor_id,sensor_type FROM sensors WHERE sensor_id="+sensor_ids.get(j);
				ResultSet rs_type=execute_stmt(type_list);
				while (rs_type.next()){
					if(rs_type.getString("sensor_type").equals("i")){
						int check_image=checkImage(sensor_ids.get(j));
						if (check_image>0){
							String delete_image="DELETE FROM images WHERE sensor_id="+sensor_ids.get(j);
							int img_check=execute_update(delete_image);}
						String delete_sens="DELETE FROM sensors WHERE sensor_id="+sensor_ids.get(j);
						count=execute_update(delete_sens);
						continue;}
					else if(rs_type.getString("sensor_type").equals("s")){
						int check_scalar=checkScalar(sensor_ids.get(j));
						if(check_scalar>0){
							String delete_scalar="DELETE FROM scalar_data WHERE sensor_id="+sensor_ids.get(j);
							int img_check=execute_update(delete_scalar);}
						String delete_sens="DELETE FROM sensors WHERE sensor_id="+sensor_ids.get(j);
						count=execute_update(delete_sens);
						continue;}
					else if(rs_type.getString("sensor_type").equals("a")){
						int check_audio=checkAudio(sensor_ids.get(j));
						if(check_audio>0){
							String delete_audio="DELETE FROM audio_recordings WHERE sensor_id="+sensor_ids.get(j);
							int img_check=execute_update(delete_audio);}
						String delete_sens="DELETE FROM sensors WHERE sensor_id="+sensor_ids.get(j);
						count=execute_update(delete_sens);
						continue;}
				}
				
			}
			}catch (Exception e) {
				System.out.println(e.getMessage());
				}}		
		
		
		str= String.valueOf(sensorbox.length)+" sensors have been deleted";
			
		
		
	return str;

	}

	//Check's if String is a number
	public boolean isNumber( String input ){
		   try{
			   Double.parseDouble( input );
		      return true;
		   }catch( Exception e ){
		      return false;
		   }
		}
	
}
