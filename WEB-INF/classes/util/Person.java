package util;

import java.io.Console;
import java.util.ArrayList;

public class Person {
	
    private long person_id;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String phone;
    
    public Person (long person_id, String first_name, String last_name, String address, String email, String phone){
		this.person_id = person_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;		
		this.phone = phone;
    }
    
    // Getters and Setters
    public long getPerson_id() {
    	return person_id;
    }
    
    public void setPerson_id(long person_id) {
    	this.person_id = person_id;
    }
    
    public String getFirst_name() {
    	return first_name;
    }
    
    public void setFirst_name(String first_name) {
    	this.first_name = first_name;
    }
    
    public String getLast_name() {
    	return last_name;
    }
    
    public void setLast_name(String lname) {
    	this.last_name = last_name;
    }

    public String getAddress() {
    	return address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getPhone() {
    	return phone;
    }
    
    public void setPhone(String phone) {
    	this.phone = phone;
    }
}
