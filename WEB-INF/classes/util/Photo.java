package util;

import java.sql.*;

public class Photo {
    int photo_id;
    String ownername = "";
    String dateUploaded = "";
    String location = "";
    String subject = "";
    String description = "";
    int permitted = 2; // default is private
    
    public Photo(int photo_id, String ownername, String dateUploaded, 
		 String location, String subject, String description, 
		 int permitted) {
	this.photo_id = photo_id;
	this.ownername = ownername;
	this.dateUploaded = dateUploaded;
	this.location = location;
	this.subject = subject;
	this.description = description;
	this.permitted = permitted;
    }

    /**
     * Overloaded minimalist constructor
     */
    public Photo(int photo_id, String ownername, int permitted) {
	this.photo_id = photo_id;
	this.ownername = ownername;
	this.permitted = permitted;
    }
    
    /*
     * Getter and Setters
     */
    public int getPhotoId() {return photo_id;}
    public String getPhotoIdString() {return new Integer(photo_id).toString();}
    public void setPhotoId(int photo_id) {this.photo_id = photo_id;}
    public String getOwnerName() {return ownername;}
    public void setOwnerName(String ownername) {this.ownername = ownername;}
    public String getDate() {return dateUploaded;}
    public void setDateUploaded(String dateUploaded) {
	this.dateUploaded = dateUploaded;}
    public String getLocation() {
	if (location == null)
	    return "";
	else
	    return location;
    }
    public void setLocation(String location) {this.location = location;}
    public String getDescription() {
	if (description == null)
	    return "";
	else
	    return description;
    }
    public void setDescription(String description) {
	this.description = description;}
    public int getPermitted() {return permitted;}
    public void setPermitted(int permitted) {this.permitted = permitted;}
    public String getSubject() {
	System.out.println("Subject: " + subject);
	if (subject == null)
	    return "";
	else
	    return subject;
    }
    public void setSubject(String subject) {this.subject = subject;}
}


