package util;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Sensors{
	private long sensor_id;
	private String location;
	private String sensor_type;
	private String description;
	public Sensors(long sensor_id, String location, String sensor_type, String description)
	{
		this.sensor_id = sensor_id;
		this.location = location;
		this.sensor_type = sensor_type;
		this.description = description;
	}
	public long getSensor_id()
	{
	
		return sensor_id;
	}
	
	public void setSensor_id(long sensor_id)
	{
	
		this.sensor_id = sensor_id;
	}
	
	public String getLocation()
	{
	
		return location;
	}
	
	public void setLocation(String location)
	{
	
		this.location = location;
	}
	
	public String getSensor_type()
	{
	
		return sensor_type;
	}
	
	public void setSensor_type(String sensor_type)
	{
	
		this.sensor_type = sensor_type;
	}
	
	public String getDescription()
	{
	
		return description;
	}
	
	public void setDescription(String description)
	{
	
		this.description = description;
	}
}
