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
		
		
		response.setContentType("text/html");
		response.setHeader("Content-Disposition","attachment;filename= Scalar.csv");
		
		try{
			OutputStream out = response.getOutputStream();
			Writer w = new OutputStreamWriter(out, "UTF-8");
			String query = "SELECT sensor_id,date_created,value FROM scalar_data";
			ResultSet rs = database.execute_stmt(query);
			while (rs.next()) {
				String[] dateTime = String.valueOf(rs.getTimestamp(2)).split(" ");
				String[] date = dateTime[0].split("-");
				String time = dateTime[1];
				String dateFormated = date[2] + "/"+ date[1]+ "/"+ date[0] + " " + time;
				
				

				//response.setHeader("Content-Disposition","attachment;filename=" + String.valueOf(rs.getInt(1)) + "_" + 
						//String.valueOf(rs.getInt(2))+ "_" + String.valueOf(rs.getDate(3)) + ".wav" );
				
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