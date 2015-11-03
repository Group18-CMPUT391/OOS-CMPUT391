<!--
Search page allows to user to enter a search query 
-->

<!DOCTYPE html>
<%
    String username = null;
    String error;
    try {
        error = (String) session.getAttribute("err");
        username = (String) session.getAttribute("username");
    } catch (NullPointerException e) {
        e.printStackTrace();
    }
    
    if (username == null) {
        String err = "Please log in before searching for images";
        session.setAttribute("err", err);
        response.sendRedirect("/c391proj/login.jsp");
    }
%>

<html>
  <head>
    <title>Search</title>
    <jsp:include page="includes/header.jsp"/>
  </head>
  <body>
    <form name="searchform" action="searchResults" method="get">
      <table>
        <tr>
          <th>Keyword(s): </th>
          <td>
            <input name="query" placeholder="Enter search query.."></input>
          </td>
        </tr>
        <tr>
          <th>Date: </th>
          <td>
            <input name="fromdate" type="date" placeholder="YYYY-MM-DD">
            </input> to 
            <input name="todate" type="date" placeholder="YYYY-MM-DD"></input>
          </td>
        </tr>
        <tr>
          <th>Sort by: </th>
          <td>
            <input type="radio" name="sortby" value="1" checked="checked">
                    Decending Time</input>
            <input type="radio" name="sortby" value="2">Ascending Time</input>
            <input type="radio" name="sortby" value="3">Rank</input>
          </td>
        </tr>
        <tr>
          <th> </th>
          <td>
            <input type="submit" value="Search" name="search"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
