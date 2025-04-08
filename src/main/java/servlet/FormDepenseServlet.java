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

public class FormDepenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        Credit cr =new Credit();
        try {
            List<Credit> credits = cr.findAll();
            req.setAttribute("credits", credits);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/formdepense.jsp");
            dispatcher.forward(req, res);
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().write("SQL Error: " + e.getMessage());
            res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
            res.getWriter().write("<p><a href=\"/ETU003246/views/formcredit.jsp\" >Formulaire de credit</a></p>");
        } 
    }
    
}                                                               


