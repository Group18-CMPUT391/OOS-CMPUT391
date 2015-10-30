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
    <title>Main Page</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <% 
       if (error != null) {
       out.println(error);
       session.removeAttribute("err");
       }
       %>
    <p>Filler home page</p>
  </body>
</html>
