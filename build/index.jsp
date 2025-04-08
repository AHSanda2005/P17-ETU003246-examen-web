<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %> 

<html>
<head><title>Login</title></head>
<body>
<%
    HttpSession existingSession = request.getSession(false);
    if (existingSession != null && existingSession.getAttribute("id") != null) {
        response.sendRedirect("/ETU003246/views/formcredit.jsp");
    }
%>
</body>
</html>
<body>
    <p>login
    <form name="form1" method="post" action="login">
    <p>nom:
    <input type="text" name="user" value="sanda">
    </p>
    <p>mdp:
        <input type="password" name="pwd" value="20192020">
    </p>
    <input type="submit" name="Submit" rows="5" value="Soumettre">
    </form>
</body>