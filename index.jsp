<!DOCTYPE html>

<%  
   String status = null;  
   try{  
      status = (String) session.getAttribute("status");  
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
%>  

<html>
  <head>
    <title>HOME</title>
    <center><jsp:include page="includes/header.jsp"/></center>
  </head>
  <body>
    <% 
       if (status != null) {
	       out.println(status);
	       session.removeAttribute("status");
       }
       %>
    <h2>Ocean Obseravation System Home Page</h2>
  </body>
</html>
