package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Credit;
import models.Depense;

public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        Credit cr = new Credit();
        Depense dep = new Depense();
        try {
            List<Credit> credits = cr.findAll();
            req.setAttribute("credits", credits);
            List<Depense> depenses = dep.findAll();
            req.setAttribute("depenses", depenses);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/Dashboard.jsp");
            dispatcher.forward(req, res);
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().write("SQL Error: " + e.getMessage());
            res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
            res.getWriter().write("<p><a href=\"/ETU003246/views/formcredit.jsp\" >Formulaire de credit</a></p>");
        } 
    }
}


