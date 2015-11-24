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

public class Analysis_Date{
	private float sensor_id;
	private String analysis_date;
	private float analysis_avg;
	private float analysis_min;
	private float analysis_max;
	
	public void setAnalysisDate(String ana_date){
		analysis_date=ana_date;
	}
	public void setAnalysisAvg(float avg_num){
		analysis_avg=avg_num;
	}
	public void setAnalysisMin(float min_num){
		analysis_min=min_num;
	}
	public void setAnalysisMax(float max_num){
		analysis_max=max_num;
	}
	public void setAnalysisSensor(float sensor){
		sensor_id=sensor;
	}
	public Float getAnalysisSensor(){
		return this.sensor_id;
	}
	public String getAnalysisDate(){
		return this.analysis_date;
	}
	public float getAnalysisAvg(){
		return this.analysis_avg;
	}
	public float getAnalysisMin(){
		return this.analysis_min;
	}
	public float getAnalysisMax(){
		return this.analysis_max;
	}
	public Analysis_Date(float sensor,String date,float avg_num,float min_num,float max_num){
		this.sensor_id=sensor;
		this.analysis_date=date;
		this.analysis_avg=avg_num;
		this.analysis_min=min_num;
		this.analysis_max=max_num;
		
	}
	}
