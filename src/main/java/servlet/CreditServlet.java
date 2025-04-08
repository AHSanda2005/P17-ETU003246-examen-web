package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Credit;

public class CreditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
    
        String libele = req.getParameter("libele");
        String  montant= req.getParameter("montant");
        String datedebut = req.getParameter("date");
        String datefin = req.getParameter("datefin");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (libele == null || libele.trim().isEmpty() || montant == null || montant.trim().isEmpty() || datedebut == null || datedebut.trim().isEmpty() || datefin == null || datefin.trim().isEmpty()) {
            res.getWriter().write("Erreur: les valeurs ne doivent pas etre nulle");
            res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
            res.getWriter().write("<p><a href=\"/ETU003246/views/formcredit.jsp\" >Formulaire de credit</a></p>");
        }else{
            int valeur=0;
            try {
                valeur = Integer.parseInt(montant);
            } catch (NumberFormatException e) {
                res.getWriter().write("Erreur: montant doit etre un nombre.");
                return;
            }
            Date date2=null;
            Date date3=null;
            try {
                date2 = formatter.parse(datedebut);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date3 = formatter.parse(datefin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Credit c = new Credit();
            c.setDatedebut(date2);
            c.setDatefin(date3);
            c.setLibele(libele);
            c.setMontant(valeur);
            try{
                c.save();
                res.sendRedirect("/ETU003246");
            } catch (SQLException e) {
                e.printStackTrace();
                res.getWriter().write("SQL Error: " + e.getMessage());
            } 
        }
    }
    
}


