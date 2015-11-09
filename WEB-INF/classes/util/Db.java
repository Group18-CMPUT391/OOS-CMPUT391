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
import oracle.sql.*;
import oracle.jdbc.*;

import util.User;
import util.Person;
//import util.Photo;

public class Db {
	static final String USERNAME = "hbtruong";
	static final String PASSWORD = "qwerty123456";
	// JDBC driver name and database URL
	static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

	private Connection conn;
	private Statement stmt;

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
				date_registered = rs.getString("date_registered");
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
		String query = "select password from users where user_name = '" + 
			username + "'";
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

	
}
