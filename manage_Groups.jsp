<!DOCTYPE html>

<%
    String error;  
    try{  
        error=(String)request.getAttribute("err");  
    }catch(NullPointerException e){error=" ";}        
%>  

<html>
  <head>
    <title>Manage Groups Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="manage_Groups" action="manage_Groups" method="POST">
      <table>
	<tbody>  
          <tr >  
            <th align="left">My Groups</th>  
          </tr>   
          <tr>  
            <td>Friends: </td>  
          </tr>
	  <tr>
	    <td><input type="submit" value="X"/> my_besty</td>
	  </tr>
	  <tr>
	    <td><input type="submit" value="X"/>
	        <input type="text" value="New User"/></td>
	  </tr>
          <tr >                          
            <td><input type="text" value="New Group" size="20"/></td>
          </tr>
          <tr >                          
            <td align="center"><input type="submit" value="Submit" />  
              <input type="reset" value="Clear" /></td>  
          </tr>  
	</tbody> 
      </table>
    </form>
  </body>
</html>
