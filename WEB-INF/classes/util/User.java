package util;

import java.io.Console;
import java.util.ArrayList;

public class User {
	/*
    private String fname;
    private String lname;
    private String address;
    private String phone;
    private String email;
    */
    private String user_name;
    private String password;
    private String role;
    private long person_id;
    private String date_registered;
    
    /*
    public User(String username, String email, String fname, String lname,
		String phone, String address, String role, 
		String date_registered){
		this.username = username;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.date_registered = date_registered;
    }
    */
    
    /**
     * Overloaded constructor to create the most basic of users
     * @param username
     * @param groups
     */
    public User(String user_name, String password, String role, long person_id, String date_registered) {
    	this.user_name = user_name;
    	this.password = password;
    	this.role = role;
    	this.person_id = person_id;
    	this.date_registered = date_registered;
    }
    
    // Getters and Setters
    public long getPerson_id() {
    	return person_id;
    }
    
    public void setPerson_id(long person_id) {
    	this.person_id = person_id;
    }
    
    public String getUser_name() {
    	return user_name;
    }
    
    public void setUser_name(String user_name) {
    	this.user_name = user_name;
    	
    }
    
    public String getRole() {
    	return role;
    }
    
    public void setRole(String role) {
    	this.role = role;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getDate_registered() {
    	return date_registered;
    }
    
    public void setDate_registered(String date_registered) {
    	this.date_registered = date_registered;
    }
    /*
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getFname() {return fname;}
    public void setFname(String fname) {this.fname = fname;}
    public String getLname() {return lname;}
    public void setLname(String lname) {this.lname = lname;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    */
}
