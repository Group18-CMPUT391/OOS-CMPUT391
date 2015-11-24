package util;

import java.sql.ResultSet;
import java.text.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.*;


public class OLAPCommands {
    private String timeframe;
    private Db database;

    public OLAPCommands() {
		database = new Db();
    	database.connect_db();
    }
	public int checkFact(){
		int ret=-1;
		String check = "SELECT * FROM fact";
		try{
			ResultSet rs=database.execute_stmt(check);
			if (!rs.isBeforeFirst()) {    
 				ret=0; 
				}
			else{
				ret=1;}}
		catch (Exception e) {
			e.printStackTrace();
			}
		return ret;
	}
//used to setup the fact table, populating rows with each time dimension being a column
    public void setup_Fact(long person_id){
    		String fact_table = "CREATE TABLE fact AS "
    			+ "SELECT S1.sensor_id, TO_CHAR(S2.date_created,'YYYY') as yyyy, "
    			+ "TO_CHAR(s2.date_created,'YYYY/Q') as q, "
    			+ "TO_CHAR(s2.date_created,'YYYY/Q/MM') as mm, "
    			+ "TO_CHAR(s2.date_created,'YYYY/Q/MM/IW') as ww, " 
    			+ "TO_CHAR(s2.date_created,'YYYY/Q/MM/IW,DD') as dd, " 
    			+ "s2.value "
    			+ "FROM subscriptions S1, scalar_data S2 "
    			+ "WHERE S1.person_id = " + person_id + " " 
    			+ "AND S1.sensor_id = S2.sensor_id "
    			+ "ORDER BY S1.sensor_id, TO_CHAR(S2.date_created,'YYYY/Q/MM/IW/DD'), S2.value ";
    		
    		try{
    			database.execute_stmt(fact_table);
    		}
    		catch (Exception e){
    			e.printStackTrace();
    		}
    }
//drops the fact table
    public void drop_Fact(){
    	try {
    		database.execute_stmt("DROP TABLE fact");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
//returns rows in which average, min, and max for each year is returned
    public ArrayList<Analysis_Date> getAnalysisYearly(long person_id, String sensor_id) {
    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();

    	
    	
    	String year_query = "SELECT sensor_id, yyyy as year, "
    			+ "AVG(value) average, MIN(value) min, MAX(value) max "
    			+ "FROM fact "
    			+ "WHERE sensor_id = " + sensor_id + " "
    					+ "GROUP BY yyyy,sensor_id ";
    	

    	
    	try{
    		ResultSet rs = database.execute_stmt(year_query);
    		while (rs!=null && rs.next()){
    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("year"),rs.getFloat("average"),rs.getFloat("min"),
    					rs.getFloat("max")));
    		}
    	return dates;
    	
    }
    	catch (Exception e){
    	    		e.printStackTrace();
    	    	}
    	return dates;
    	}
//    public ArrayList<Analysis_Date> getAnalysisMonthly(long person_id, String sensor_id) {
//    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
//    	String month_query = "SELECT sensor_id, mm \"monthly\" "
//				+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
//				+ "FROM fact "
//				+ "WHERE sensor_id= "+ sensor_id+ " "
//				+ "GROUP BY mm,sensor_id";
//    	try{
//    		ResultSet rs = database.execute_stmt(month_query);
//    		while (rs.next()){
//    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("monthly"),rs.getFloat("average"),rs.getFloat("min"),
//    					rs.getFloat("max")));
//    		}
//    	return dates;
//    	
//    }
//    	catch (Exception e){
//    	    		e.printStackTrace();
//    	    	}
//    	}
//
//    	
//
//    public ResultSet getAnalysisQuarterly(long person_id, String sensor_id) {
//    	
//    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
//    	String quarter_query = "SELECT sensor_id, q \"quarter\" "
//				+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
//				+ "FROM fact "
//				+ "WHERE sensor_id= "+ sensor_id+ " "
//				+ "GROUP BY mm,sensor_id";
//    	try{
//    		ResultSet rs = database.execute_stmt(month_query);
//    		while (rs.next()){
//    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("quarter"),rs.getFloat("average"),rs.getFloat("min"),
//    					rs.getFloat("max")));
//    		}
//    	return dates;
//    	
//    }
//    	catch (Exception e){
//    	    		e.printStackTrace();
//    	    	}
//    	}
//    	
//
//
//    public ResultSet getAnalysisWeekly(long person_id, String sensor_id) {
//    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
//    	String month_query = "SELECT sensor_id, ww \"weekly\" "
//				+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
//				+ "FROM fact "
//				+ "WHERE sensor_id= "+ sensor_id+ " "
//				+ "GROUP BY ww,sensor_id";
//    	try{
//    		ResultSet rs = database.execute_stmt(month_query);
//    		while (rs.next()){
//    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("weekly"),rs.getFloat("average"),rs.getFloat("min"),
//    					rs.getFloat("max")));
//    		}
//    	return dates;
//    	
//    }
//    	catch (Exception e){
//    	    		e.printStackTrace();
//    	    	}
//    	}
//    	
//    
//        public ResultSet getAnalysisDaily(long person_id, String sensor_id) {
//    	
//        	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
//        	String month_query = "SELECT sensor_id, dd \"days\" "
//    				+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
//    				+ "FROM fact "
//    				+ "WHERE sensor_id= "+ sensor_id+ " "
//    				+ "GROUP BY ww,sensor_id";
//        	try{
//        		ResultSet rs = database.execute_stmt(month_query);
//        		while (rs.next()){
//        			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("days"),rs.getFloat("average"),rs.getFloat("min"),
//        					rs.getFloat("max")));
//        		}
//        	return dates;
//        	
//        }
//        	catch (Exception e){
//        	    		e.printStackTrace();
//        	    	}
//    	
 //   }

//checks to see which scalar id you are subscribed to
    public ResultSet getSIDScalar(long person_id) {
    	String query = "SELECT su.sensor_id, se.sensor_type "
    			+ "FROM subscriptions su, sensors se "
    			+ "WHERE su.sensor_id = se.sensor_id "
    			+ "AND se.sensor_type ='s' "
    			+ "AND su.person_id = " + person_id;
    	ResultSet rs = database.execute_stmt(query);
    	return rs;
    }
  //returns list of distinct years of data for sensor
    public ArrayList<String> getYearsScalar(String sensor_id) {
    	String query = "SELECT distinct yyyy \"year\" FROM fact WHERE sensor_id= " +sensor_id;
    	
	    ArrayList<String> years = new ArrayList<String>();
	    try {
	    	ResultSet rs = database.execute_stmt(query);
	    	while (rs != null && rs.next()) {
		    	years.add(rs.getString("year"));
		    	
			
		    }	
	    } catch (Exception e) {
	    	
	    }
    	return years;
    }
	//returns list of distinct quarters of data for sensor
    public ArrayList<String> getQuarterlyScalar(String sensor_id) {
    	String query = "SELECT distinct q \"quarter\" FROM fact WHERE sensor_id= " +sensor_id;
    	ResultSet rs = database.execute_stmt(query);
    	
	    ArrayList<String> quarters = new ArrayList<String>();
	    
	    try {
	    	while (rs != null && rs.next()) {
		    	quarters.add(rs.getString("quarter"));
		    }	
	    } catch (Exception e) {
	    	
	    }
    	return quarters;
    }
// returns list of distinct weeks of data for sensor
    public ArrayList<String> getWeeklyScalar(String sensor_id) {
    	String query = "SELECT distinct ww \"week\" FROM fact WHERE sensor_id= " +sensor_id;
    	ResultSet rs = database.execute_stmt(query);
    	
	    ArrayList<String> weeks = new ArrayList<String>();
	    
	    try {
	    	while (rs != null && rs.next()) {
		    	weeks.add(rs.getString("week"));
		    }	
	    } catch (Exception e) {
	    	
	    }
    	return weeks;
    }
// returns list of distinct months of data for sensor
    public ArrayList<String> getMonthlyScalar(String sensor_id) {
    	String query = "SELECT distinct mm \"month\" FROM fact WHERE sensor_id= " +sensor_id;
    	ResultSet rs = database.execute_stmt(query);
    	
	    ArrayList<String> months = new ArrayList<String>();
	    
	    try {
	    	while (rs != null && rs.next()) {
		    	months.add(rs.getString("month"));
		    }	
	    } catch (Exception e) {
	    	
	    }
    	return months;
    }
// returns list of distinct dates of data for sensor
    public ArrayList<String> getDailyScalar(String sensor_id) {
    	String query = "SELECT distinct dd \"day\" FROM fact WHERE sensor_id= " +sensor_id;
    	ResultSet rs = database.execute_stmt(query);
    	
	    ArrayList<String> days = new ArrayList<String>();
	    
	    try {
	    	while (rs != null && rs.next()) {
		    	days.add(rs.getString("day"));
		    }	
	    } catch (Exception e) {
	    	
	    }
    	return days;
    }
    //returns a list of rows in which data shows drilldown from year to month
    public ArrayList<Analysis_Date> drillDowntoMonth(String selected_year,long sensor_id){
    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
    	String month_query = "SELECT sensor_id, mm monthly, "
				+ "AVG(value) average, MIN(value) min, MAX(value) max "
				+ "FROM fact "
				+ "WHERE sensor_id= "+ sensor_id+ " "
						+ "AND yyyy= "+ selected_year+ " "
								+ "GROUP BY mm,sensor_id";
    	try{
    		ResultSet rs = database.execute_stmt(month_query);
    		while (rs!= null && rs.next()){
    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("monthly"),rs.getFloat("average"),rs.getFloat("min"),
    					rs.getFloat("max")));
    		}
    	
    	
    }
    	catch (Exception e){
    	    		e.printStackTrace();
    	    	}
    	return dates;
    	}
    		
    //returns a list of rows in which data shows drilldown from year to quarter
    public ArrayList<Analysis_Date> drillDowntoQuarter(String selected_year,long sensor_id){


    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
    	String quarter_query = "SELECT sensor_id, q quarter, "
    			+ "AVG(value) average, MIN(value) min, MAX(value) max "
    			+ "FROM fact "
				+ "WHERE sensor_id= "+ sensor_id+ " "
				+ "AND yyyy= "+ selected_year+ " "
				+ "GROUP BY q,sensor_id";
    	try{
    		ResultSet rs = database.execute_stmt(quarter_query);
    		while (rs != null && rs.next()){
    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("quarter"),rs.getFloat("average"),rs.getFloat("min"),
    					rs.getFloat("max")));
    		}
    	
    	
    }
    	catch (Exception e){
    	    		e.printStackTrace();
    	    	}
    	return dates;
    	}
//returns a list of rows in which data shows drilldown from year to week
    public ArrayList<Analysis_Date> drillDowntoWeek(String selected_year,long sensor_id){


    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
    	String month_query = "SELECT sensor_id, ww week, "
				+ "AVG(value) average, MIN(value) min, MAX(value) max " 
				+ "FROM fact "
				+ "WHERE sensor_id= "+ sensor_id+ " "
				+ "AND yyyy= "+ selected_year+ " "
				+ "GROUP BY ww,sensor_id";
    	try{
    		ResultSet rs = database.execute_stmt(month_query);
    		while (rs != null && rs.next()){
    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("week"),rs.getFloat("average"),rs.getFloat("min"),
    					rs.getFloat("max")));
    		}
    	
    	
    }
    	catch (Exception e){
    	    		e.printStackTrace();
    	    	}
    	return dates;
    }
 //returns a list of rows in which data shows drilldown from year to day
    public ArrayList<Analysis_Date> drillDowntoDay(String selected_year,long sensor_id){



    	ArrayList<Analysis_Date> dates=new ArrayList<Analysis_Date>();
    	String month_query = "SELECT sensor_id, dd days, "
				+ "AVG(value) average, MIN(value) min, MAX(value) max " 
				+ "FROM fact "
				+ "WHERE sensor_id= "+ sensor_id+ " "
				+ "AND yyyy= "+ selected_year+ " "
				+ "GROUP BY dd,sensor_id";
    	try{
    		ResultSet rs = database.execute_stmt(month_query);
    		while (rs != null && rs.next()){
    			dates.add(new Analysis_Date(rs.getFloat("sensor_id"),rs.getString("days"),rs.getFloat("average"),rs.getFloat("min"),
    					rs.getFloat("max")));
    		}
    	
    	
    }
    	catch (Exception e){
    	    		e.printStackTrace();
    	    	}
    	return dates;
    	}
    
    public void close_OLAP() {
    	database.close_db();
    }
}
