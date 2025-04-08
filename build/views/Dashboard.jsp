<%@ page import="models.Credit" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Depense" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste Credit</title>
</head>
<body>
    <% 
        List<Credit> credits = (List<Credit>) request.getAttribute("credits");
        List<Depense> depenses = (List<Depense>) request.getAttribute("depenses");
        int x=0;
        int y=0;
    %>
    <p>
        Liste Credits
    </p>
    <table border="1"> 
        <tr>
            <th>ID</th>
            <th>libele</th>
            <th>montant</th>
            <th>Debut</th>
            <th>Fin</th>
        </tr>
        <% for (Credit  cr : credits) {  %>
        <% x=x+cr.getMontant(); %>
            <tr>
                <td><%= cr.getId() %></td>
                <td><%= cr.getLibele() %></td>
                <td><%= cr.getMontant() %></td>
                <td><%= cr.getDatedebut() %></>
                <td><%= cr.getDatefin() %></>
            </tr>
        <% } %>
    </table>
    <p>
    credits total: <%= x %>
    </p>
    <p>
        Liste Depenses
    </p>
    <table border="1"> 
        <tr>
            <th>ID</th>
            <th>libele</th>
            <th>montant</th>
            <th>Date</th>
        </tr>
        <% for (Depense  dp : depenses) {  %>
        <% y=y+dp.getMontant(); %>
            <tr>
                <td><%= dp.getId() %></td>
                <td><%= dp.getLibele() %></td>
                <td><%= dp.getMontant() %></td>
                <td><%= dp.getDate() %></>
            </tr>
        <% } %>
    </table>
    <p>
    depenses total: <%= y %>
    </p>
     <p>
        comparaison
    </p>
    <table border="1"> 
        <tr>
            <th>libele</th>
            <th>montant credit</th>
            <th>montant depense</th>
            <th>reste</th>
        </tr>
        <% for (Credit  cr : credits) {  %>
            <tr>
                <% String Libele= cr.getLibele(); %>
                <td><%= cr.getLibele() %></td>
                <td><%= cr.getMontant() %></td>
                <% int z=0; %>
                <% for (Depense  dp : depenses) {  %>
                    <% if (Libele.equals(dp.getLibele()) && cr.getDatefin().after(dp.getDate()) && cr.getDatedebut().before(dp.getDate())){ %>
                        <% z=z+dp.getMontant(); %>
                    <% } %>
                <% } %>
                <td><%= z %></td>
                <td><%= cr.getMontant()-z %>
            </tr>
        <% } %>
    </table>
    <i>
    reste total :<%= x-y %>
    </i>
    <p>
        <a href="/ETU003246/FormDepense" >Insertion Depense</a>
    </p>
    <p>
        <a href="/ETU003246" >Insertion Credit</a>
    </p>
</body>
</html>
