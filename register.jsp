<!-- 
Web page for registering a new user
-->


<!DOCTYPE html>
<%  
   String error = null;  
   try{  
      error = (String) session.getAttribute("err");  
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
%>  
<html>
    <head>
        <title>Registration</title>
    </head>
    <body>
      <a href='new_user.jsp' target="_blank">This is a New User</a> <br>
      <a href='existing_user.jsp' target="_blank">This is an existing User</a>
    </body>
</html>
