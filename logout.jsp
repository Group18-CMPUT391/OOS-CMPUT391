<!DOCTYPE html>

<!--
Handles loging out by destroying the session variable.
-->

<%@ page import="javax.servlet.http.*" %>
<%@ page import="util.User" %>

<%  
   User user = null;
   try{  
      user = (User) session.getAttribute("user");
   } catch(NullPointerException e) {
      e.printStackTrace();
   }
   if (user != null) {
      session.removeAttribute("user");
      //session.removeAttribute("email");
      /* session.removeAttribute("firstname");
      session.removeAttribute("lastname");
      session.removeAttribute("address");
      session.removeAttribute("emailphone");
      session.removeAttribute("isPopulated"); */
      session.setAttribute("status", "You have sucessfully been logged out.");
   } else {
      session.setAttribute("status", "Please login first.");
   }
   response.sendRedirect("/oos-cmput391/login.jsp");
%>  

<html>
  <head>
    <title>Logout</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
  </body>
</html>
