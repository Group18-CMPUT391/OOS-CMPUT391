package util;

import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import util.Db;

public class OLAPCommands {
    private Db db;
    private String timeframe;
	private Calendar right_now = Calendar.getInstance();
	private int year = right_now.get(Calendar.YEAR);
	private int month = right_now.get(Calendar.MONTH);
	private int week = right_now.get(Calendar.WEEK_OF_YEAR);
	private int day = right_now.get(Calendar.DAY_OF_YEAR);

    public OLAPCommands(String tframe) {
		this.db = new Db();
		this.timeframe = tframe;
    }
    
    /**
     * Tries to parse out a column title for the row's results
     * in the statement execution
     */
    /*public String getColTitle(Calendar mydate) {
		String title = "";
		if (timeframe.equals("monthly")) {
			    title = title +" Month: " + Integer.
				toString(mydate.get(Calendar.MONTH)+1);
		}
		if (timeframe.equals("weekly")) {
		    title = title + " Week: " + Integer.
			toString(mydate.get(Calendar.WEEK_OF_YEAR));
		}
		if (timeframe.equals("daily")) {
		    title = title + " Day: " + 
			Integer.toString(mydate.get(Calendar.DATE)) + " " +
			Integer.toString(mydate.get(Calendar.MONTH));
		}
		title = title+" "+Integer.toString(mydate.get(Calendar.YEAR));
		return title;
    }*/
    
    
    /**
     * Returns the total details of sensors registered 
     * for the specified timeframe format
     */
    public String getSensorsScalarDaily() {
    	String drop_query = "DROP TABLE data_cube";
    			
		db.connect_db();
    	try {
    		db.execute_stmt(drop_query);
    	} catch (Exception e) {
    		// Do nothing
    	}
    	
    	String cube_query = "CREATE TABLE data_cube AS "
    						+ "SELECT S1.sensor_id, S2.date_created, S3.location, AVG(value) 'average', MIN(value) 'min', MAX(value) 'max' " 
    						+ "FROM subscriptions S1, scalar_data S2, sensors S3, persons P "
    						+ "WHERE S1.person_id = P.person_id "
    						+ "AND S1.sensor_id = S2.sensor_id "
    						+ "AND S1.sensor_id = S3.sensor_id "
    						+ "GROUP BY CUBE (S2.sensor_id, S2.date_created, S3.location) ";
    						
		String query = "SELECT S1.sensor_id, TRUNC(date_created, 'DD'), location, AVG(value), MIN(value), MAX(value) "
				+ "INTO sensor_values "
				+ "FROM sensors S1, scalar_data S2 ORDER BY date_created "		
				+ "WHERE S1.sensor_id = S2.sensor_id "
				+ "GROUP BY TRUNC(date_created, 'DD')";
				//+ "AND date_created < SYSDATE AND date_created > SYSDATE - 7 "
		Sting query2 = "SELECT S1.sensor_id, TRUNC(date_created, 'WW'), location, AVG(value), MIN(value), MAX(value) "
				+ "FROM sensor_values "
				+ "GROUP BY TRUNC(date_created, 'WW')";
		Sting query3 = "SELECT S1.sensor_id, TRUNC(date_created, 'MM'), location, AVG(value), MIN(value), MAX(value) "
				+ "FROM sensor_values "
				+ "GROUP BY TRUNC(date_created, 'MM')";
		Sting query4 = "SELECT S1.sensor_id, S2.date_created, S3.date_created, TRUNC(S2.date_created, 'MM'), location, AVG(value), MIN(value), MAX(value) "
				+ "FROM sensor_values "
				+ "WHERE ABS(TRUNC(S2.date_created) - TRUNC(S3.date_created)) < 90 "
				+ "GROUP BY TRUNC(S2.date_created, 'MM')";
		Sting query5 = "SELECT S1.sensor_id, TRUNC(date_created, 'YYYY'), location, AVG(value), MIN(value), MAX(value) "
				+ "FROM sensor_values "
				+ "GROUP BY TRUNC(date_created, 'YYYY')";
		
		String result = "";
		String title = "";
		String oldTitle = "";
		ResultSet rset;
		db.connect_db();
		
		try {
		    rset = db.execute_stmt(query);
		    result = result + "<table border='1'>";
		    /*while(rset.next()) {
			// Table Row Section Title Stuff Here
			mydate.setTime(rset.getDate(2));
			// Set the title stuff
			title = getColTitle(mydate);
			// Check if we should add the title in
			if (!title.equals(oldTitle)) {
			    oldTitle = title;
			    result = result+"<tr><th>"+title+"</th></tr>";
			}
			// Add in the sensors
			result = result +"<tr><td>"+rset.getInt(1)+"</td></tr>";
		    }*/
		    result = result + "</table>";
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		db.close_db();
		return result;
    }
}
