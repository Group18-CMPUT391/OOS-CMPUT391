import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.io.OutputStreamWriter;

import oracle.sql.*;
import oracle.jdbc.*;
import javax.servlet.*;
import javax.servlet.http.*;

import util.Db;

public class ScalarServlet extends HttpServlet {

	private HttpSession session;
	public void doGet(HttpServletRequest request, 
		HttpServletResponse response)
		throws ServletException, IOException {
		
		Db database = new Db();
		database.connect_db();
		String id = request.getParameter( "id" );
		String user = request.getParameter( "user" );
		String fromdate = request.getParameter( "fromdate" );
		String todate = request.getParameter( "todate" );
		String location = request.getParameter( "location" );
		String value = request.getParameter( "value" );
		String query = null;
		response.setContentType("text/html");
		response.setHeader("Content-Disposition","attachment;filename= Scalar.csv");

		try{
			OutputStream out = response.getOutputStream();
			Writer w = new OutputStreamWriter(out, "UTF-8");
			
			if (user.equals("yes")) {
				//Searching for value by checking id the keyword/value field is a number 
				if(database.isNumber( value )){
					query = "SELECT s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + id
							+ "AND s.value ="+ Float.parseFloat(value)
									+ "AND se.location LIKE '%"+location+"' "
											+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
													+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
															+ "ORDER BY s.sensor_id";
				}
				//Searching without value
				else if(value.equals(" ")){
					query = "SELECT s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + id
							+ "AND se.location LIKE '%"+location+"' "
									+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
											+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
													+ "ORDER BY s.sensor_id";
				}
				//Searching for keyword instead of value to have to set value to null
				else {
					query = "SELECT s.sensor_id,s.date_created,s.value "
							+ "FROM scalar_data s "
							+ "JOIN subscriptions su on s.sensor_id = su.sensor_id "
							+ "JOIN sensors se on s.sensor_id = se.sensor_id "
							+ "WHERE su.person_id =" + id
							+ "AND s.value =null "
							+ "AND se.location LIKE '%"+location+"' "
									+ "AND date_created BETWEEN TO_DATE('"+fromdate+"', 'YYYY-MM-DD') "
											+ "AND TO_DATE('"+todate+"', 'YYYY-MM-DD') "
													+ "ORDER BY s.sensor_id";
				}
				
			}
			else {
				//Generate complete list of scalar data
				query = "SELECT sensor_id,date_created,value FROM scalar_data ORDER BY sensor_id";
			}
		
			ResultSet rs = database.execute_stmt(query);
			//Write the result set to a text file outputstram using write 
			while (rs.next()) {
				String[] dateTime = String.valueOf(rs.getTimestamp(2)).split(" ");
				String[] date = dateTime[0].split("-");
				String[] time = dateTime[1].split(".");
				String dateFormated = date[2] + "/"+ date[1]+ "/"+ date[0] + " " + time[0];

				
				w.write(rs.getString(1) +","+ dateFormated +","+ String.valueOf(rs.getDouble(3))+"\n");
			}
			w.flush();
			w.close();
			database.close_db();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			}
	}
	
	
}