package util;

import java.io.Console;
import util.Role;
import java.util.ArrayList;

public class User {
    private String fname;
    private String lname;
    private String address;
    private String phone;
    private String email;
    private String role;
    private String username;
    private char[] password;
    private String date_reg;
    
    public User(String username, String email, String fname, String lname,
		String phone, String address, String role, 
		String date_reg){
		this.username = username;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.date_reg = date_reg;
    }
    
    /**
     * Overloaded constructor to create the most basic of users
     * @param username
     * @param groups
     */
    public User(String username, String role) {
	this.username = username;
	this.role = role;
    }
    
    /** Overloaded constructor for just username
     * 
     * @param username
     */
    public User(String username) {
	this.username = username;
    }
    
    // Getters and Setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
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
    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}
    public char[] getPassword() {return password;}
    public void setPassword(char[] password) {this.password = password;}
    public String getDate_reg() {return date_reg;}
    public void setDate_reg(String date_reg) {this.date_reg = date_reg;}
}
