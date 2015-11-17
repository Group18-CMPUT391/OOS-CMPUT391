package util;

import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import util.Db;

public class OLAPCommands {
    Db db;
    String timeframe;

    public OLAPCommands(String tframe) {
	this.db = new Db();
	this.timeframe = tframe;
    }
    /**
     * Tries to parse out a column title for the row's results
     * in the statement execution
     */
    public String getColTitle(Calendar mydate) {
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
    }
    
    
    /**
     * Returns the total details of users registered 
     * for the specified timeframe format
     */
    public String getSensorsScalar() {
	String query = "SELECT S1.sensor_id, date_created, AVG(value), MIN(value), MAX(value) "
			+ "INTO sensor_values "
			+ "FROM sensors S1, scalar_date S2 ORDER BY date_created "		
			+ "WHERE S1.sensor_id = S2.sensor_id";
			
	//String query = "SELECT date_registered, user_name from users order by date_registered";


	String result = "";
	Calendar mydate = Calendar.getInstance();
	String title = "";
	String oldTitle = "";
	ResultSet rset;
	db.connect_db();
	try {
	    rset = db.execute_stmt(query);
	    result = result + "<table border='1'>";
	    while(rset.next()) {
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
	    }
	    result = result + "</table>";
	} catch (Exception e) {
	    e.printStackTrace();
	}
	db.close_db();
	return result;
    }
}
