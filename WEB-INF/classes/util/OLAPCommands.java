package util;

import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;


public class OLAPCommands {
    private String timeframe;
    private Db database;

    public OLAPCommands() {
		database = new Db();
    	database.connect_db();
    }
    
    public ResultSet getAnalysisYearly(long person_id, String sensor_id) {
    	
    	try {
    		database.execute_stmt("DROP TABLE fact");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	String fact_table = "CREATE TABLE fact AS "
    			+ "SELECT S1.sensor_id, S2.date_created, S2.value " 
    			+ "FROM subscriptions S1, scalar_data S2 "
    			+ "WHERE S1.person_id = " + person_id + " " 
    			+ "AND S1.sensor_id = S2.sensor_id "
    			+ "ORDER BY S1.sensor_id, S2.date_created, S2.value ";
    	database.execute_stmt(fact_table);
    	
    	String year_query = "SELECT sensor_id, EXTRACT(year FROM date_created) \"yearly\", "
							+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
							+ "FROM fact "
							+ "GROUP BY sensor_id, EXTRACT(year FROM date_created) ";
    	
    	
    	/*String month_query = "SELECT sensor_id, EXTRACT(month FROM date_created) \"monthly\" "
							+ "AVG(value) \"average\", MIN(value) \"min\", MAX(value) \"max\" " 
							+ "FROM fact "
							+ "WHERE TRUNC(date_created, 'YYYY') = yearly "
							+ "GROUP BY EXTRACT(month FROM date_created) ";
    	*/
    	
    	ResultSet rs = database.execute_stmt(year_query);
    	return rs;
    	
    }
    
    public ResultSet getSIDScalar(long person_id){
    	String query = "SELECT su.sensor_id, su.sensor_id, se.sensor_type "
    			+ "FROM subscriptions su, sensors se "
    			+ "WHERE su.sensor_id = se.sensor_id "
    			+ "AND se.sensor_type ='s' "
    			+ "AND su.person_id =" + person_id;
    	ResultSet rs = database.execute_stmt(query);
    	return rs;
    }
    
    public void close_OLAP() {
    	database.close_db();
    }
}
