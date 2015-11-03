<!DOCTYPE html>

<!--
Handles loging out by destroying the session variable.
-->

<%@ page import="javax.servlet.http.*" %>

<%  
   String username = null;
   try{  
      username = (String) session.getAttribute("username");
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   if (username != null) {
      session.removeAttribute("username");
      session.removeAttribute("email");
      session.removeAttribute("firstname");
      session.removeAttribute("lastname");
      session.removeAttribute("address");
      session.removeAttribute("emailphone");
      session.removeAttribute("isPopulated");
      session.setAttribute("err", "You have sucessfully been logged out.");
   } else {
      session.setAttribute("err", "Please login first.");
   }
   response.sendRedirect("/c391proj/login.jsp");
%>  
<html>
  <head>
    <title>Logout</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
  </body>
</html>
