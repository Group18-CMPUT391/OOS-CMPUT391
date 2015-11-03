<!--
Webpage for choosing which group of photos to view
-->
<!DOCTYPE html>
<%@ page import="util.Db, util.Group, java.util.ArrayList"%>
<%
   String username = "";
   ArrayList<Group> mygroups = new ArrayList<Group>();
   try {
       username = (String) session.getAttribute("username");
   } catch (Exception e) {
       e.printStackTrace();
   }
   
   if (username == null) {
   session.setAttribute("err", "Please login first.");
   response.sendRedirect("/c391proj/login.jsp");
   }
   
   Db database = new Db();
   database.connect_db();
   mygroups = database.getParticipantGroups(username);
   database.close_db();
%>
<html>
  <head>
    <title>Choose Photo Group</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="photoGroup" action="browsePictures" method="PUT">
      <table>
	<tr>
	  <th>Group: </th>
	  <td>
	    <input type="radio" name="group" value="1" checked="checked">
	      Public</input>
	    <br>
	    <input type="radio" name="group" value="-1">Public Top 5</input>
	    <br>
	    <input type="radio" name="group" value="2">Private</input>
	    <% 
	       if (username.equals("admin")) {
	       out.println("<br>");
	       out.println("<input type='radio' name='group' "+
	       "value='0'>All Photos</input>");
	       }
	       for(Group group: mygroups) {
	       out.println("<br>");
	       out.println("<input type='radio' name='group' "+
	       "value='"+group.getId()+"'>"+group.getName()+"</input>");
	       }
	       %>
	  </td>
	</tr>
	<tr>
	  <td>
	    <input type="submit"> </input>
	  </td>
	</tr>
      </table>
