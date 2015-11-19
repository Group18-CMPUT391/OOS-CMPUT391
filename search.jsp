<!--
Search page allows to user to enter a search query 
-->

<%@	page import="util.Db" %>
<%@ page import="util.User" %> 

<% 
	User user = null;
	Db database = new Db();
	database.connect_db();
		
	String status = null;
	
	try {
	   status = (String) session.getAttribute("status");
	   user = (User) session.getAttribute("user");
	   //username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
	   e.printStackTrace();
	}
    
    if (user == null) {
        status = "Please log in before searching";
        session.setAttribute("status", status);
        response.sendRedirect("/oos-cmput391/login.jsp");
    }
%>

<html>
  <head>
    <title>Search</title>
    <center>
    	<jsp:include page="includes/header.jsp"/>
    </center>
  </head>
  <body>
	<div style="text-align:center">
  		<div style="display:inline-block">
		    <form name="searchform" action="searchservlet" method="get">
		      <table>
		        <tr>
		          <th>Keyword(s): </th>
		          <td>
		            <input name="query" placeholder="Enter search query.."></input>
		          </td>
		        </tr>
		        <tr>
		          <th>Date: </th>
		          <td>
		            <input name="fromdate" type="date" placeholder="DD-MM-YYYY" required="required" />
		            to 
		            <input name="todate" type="date" placeholder="DD-MM-YYYY" required="required" />
		          </td>
		        </tr>
		        <tr>
		          <th> </th>
		          <td>
		            <input type="submit" value="Search" name="search"/>
		          </td>
		        </tr>
		      </table>
		    </form>
		</div>
	</div>
  </body>
</html>
