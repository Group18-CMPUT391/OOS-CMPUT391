<!-- 
Webpage for updating image descriptions
-->
<!DOCTYPE html>
<%@ page import="util.Db,util.Group,util.Photo,java.util.ArrayList"%>
<%
	String error = "";
        String username = "";
	Photo photo;
        ArrayList<Group> all_groups = new ArrayList<Group>();
	try{
                session.setAttribute("err", "");
                username = (String) session.getAttribute("username");
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	String picid  = request.getQueryString();
	int photo_id = Integer.parseInt(picid);

	Db database = new Db();
        database.connect_db();

	photo = database.getPhotoDesc(photo_id);
	session.setAttribute("photo_id", photo_id);
        all_groups = database.get_groups(username);

        database.close_db();
%>

<html>
  <head>
    <title>Image Desciptions</title>
      <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="uploadimage" action="imageDesc" enctype="multipart/form-data" 
	  method="GET">
      <table>
        <tr>
           <th>Date: </th>
             <td>
                <input name="date" type="date" 
		value=<%out.println(photo.getDate());%>>
		</input>
             </td>
        </tr>

        <tr>
           <th>Location: </th>
             <td>
                <input name="location" type="text" 
		value=<%out.println("'" + photo.getLocation() + "'");%>>
		</input>
             </td>
        </tr>

        <tr>
           <th>Subject: </th>
             <td>
                <input name="subject" type="text" 
		value=<%out.println("'" + photo.getSubject() + "'");%>>
		</input>
             </td>
        </tr>

        <tr>
           <th>Description: </th>
             <td>
		<textarea name="description" cols="25" rows="5">
			  <%out.println(photo.getDescription().trim());%>
		</textarea>
             </td>
        </tr>

        <tr>
           <th>Security: </th>
             <td>
		<p><input type="radio" name="security" value="2"
		<%if(photo.getPermitted() == 2){out.println("checked=true");}%>>
		Private</input></p>
		<p><input type="radio" name="security" value="1"
		<%if(photo.getPermitted() == 1){out.println("checked=true");}%>
		>Public</input></p>
		<% for (Group group: all_groups) {
		     out.println("<p><input type='radio' name='security' value='" 
		     + group.getId() + "'");
		     if(photo.getPermitted() == 1){out.println("checked=true");}
		     out.println(">" + group.getName() + "</input></p>");
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
