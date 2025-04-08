<%@ page import="models.Credit" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Depense</title>
</head>
<body>
<%
    List<Credit> credits = (List<Credit>) request.getAttribute("credits");
%>
<p>
</p>
    <form name="form1" method="post" action="Depense">
    <p>
    Libele:
    <select id="libele" name="libele" >
        <% for (Credit credit: credits) { %>
            <option value="<%= credit.getLibele() %>"><%= credit.getLibele() %></option>"
        <% } %>
    </select>
    </p>
    <p>montant:
    <input type="number" name="montant">
    </p>
    <p>date:
        <input type="date" name="date" >
    </p>
    <p>
    <input type="submit" name="Submit" rows="5" value="soumettre">
    </p>
    <p>
        <a href="/ETU003246/views/formcredit.jsp" >Formulaire de credit</a>
    </p>
    <p>
        <a href="/ETU003246/Dashboard" >Dashboard</a>
    </p>
</body>
</html>
