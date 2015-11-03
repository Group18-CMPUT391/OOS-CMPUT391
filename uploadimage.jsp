<!-- 
Webpage for uploading images
-->
<!DOCTYPE html>
<%@ page import="util.Db,util.Group,java.util.ArrayList"%>
<%
	String error = "";
        String username = "";
        ArrayList<Group> all_groups = new ArrayList<Group>();

	try{
		error = (String) session.getAttribute("err");
                session.setAttribute("err", null);
                username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
		e.printStackTrace();
	}

        Db database = new Db();
        database.connect_db();
        all_groups = database.get_groups(username);
        database.close_db();
%>

<html>
  <head>
    <title>Upload Page</title>
      <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="uploadimage" action="uploadImage" enctype="multipart/form-data" 
	  method="POST">
      <table>
	<% if (!(error == null)) {
	   out.println("<tr>" + error + "</tr>");
	   }
	   %>
        <tr>
	  <th>File path: </th>
	  <td>
	    <input name="filepath" type="file" size="30" ></input>
	  </td>
	</tr>
	
        <tr>
          <th>Date: </th>
          <td>
            <input name="date" type="date" placeholder="YYYY-MM-DD"></input>
          </td>
        </tr>
	
        <tr>
          <th>Location: </th>
          <td>
            <input name="location" type="text" placeholder="location"></input>
          </td>
        </tr>
	
        <tr>
          <th>Subject: </th>
          <td>
            <input name="subject" type="text" placeholder="subject"></input>
          </td>
        </tr>
	
        <tr>
          <th>Description: </th>
          <td>
	    <textarea name="description" cols="25" rows="5" 
		      placeholder="Description"></textarea>
          </td>
        </tr>
	
        <tr>
          <th>Security: </th>
          <td>
	    <p><input type="radio" name="security" value="2" checked="checked">
		Private</input></p>
	    <p><input type="radio" name="security" value="1">Public</input></p>
	    <% for (Group group: all_groups) {
	       out.println("<p><input type='radio' name='security' value='" 
	       + group.getId()
	       + "'>" + group.getName() + "</input></p>");
	       }
	       %>
          </td>
        </tr>
	
	
	<tr>
	  <td ALIGN=CENTER COLSPAN="2"><input type="submit" name=".submit"
					      value="Upload">
	  </td>
	</tr>
      </table>
    </form>
  </body>
</html>



