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
    <center>
        <h2>Ocean Observation System Home Page</h2>
    </center>
  </body>
  <footer>
	<center><jsp:include page="includes/footer.jsp"/></center>
  </footer> 
</html>
