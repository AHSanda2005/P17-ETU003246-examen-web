package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Credit;
import models.Depense;

public class DepenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
    
        String libele = req.getParameter("libele");
        String  montant= req.getParameter("montant");
        String date = req.getParameter("date");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (libele == null || libele.trim().isEmpty() || montant == null || montant.trim().isEmpty() || date == null || date.trim().isEmpty()) {
            res.getWriter().write("Erreur: les valeurs ne doivent pas etre nulle");
            res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
            res.getWriter().write("<p><a href=\"/ETU003246/views/formcredit.jsp\" >Formulaire de credit</a></p>");
        }else{
            int valeur=0;
            try {
                valeur = Integer.parseInt(montant);
            } catch (NumberFormatException e) {
                res.getWriter().write("Erreur: montant doit etre un nombre.");
                res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
                res.getWriter().write("<p><a href=\"/ETU003246/views/formcredit.jsp\" >Formulaire de credit</a></p>");
            }
            Date date2=null;
            try {
                date2 = formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Credit c= new Credit();
            List<Credit> credits= null;
            try{
                credits=c.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
                res.getWriter().write("SQL Error: " + e.getMessage());
            }
            for (Credit  cr : credits) { 
                if (libele.equals(cr.getLibele()) && cr.getDatedebut().before(date2) && cr.getDatefin().after(date2)){
                    c=cr;
                }
            }
            if (c.getMontant() <= 0){
                res.getWriter().write("Credit insufisant");
                res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
                res.getWriter().write("<p><a href=\"/ETU003246/Dashboard\" >Dashboard</a></p>");
            }else{
                int x=valeur;
            Depense d = new Depense();
            List<Depense> depenses =null;
            try{
                depenses=d.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
                res.getWriter().write("SQL Error: " + e.getMessage());
            }
            for (Depense  dp : depenses) { 
                if (libele.equals(dp.getLibele())){
                    x=x+dp.getMontant();
                }
            }
            if(x>c.getMontant()){
                res.getWriter().write("Credit insufisant");
                res.getWriter().write("<p><a href=\"/ETU003246/FormDepense\" >Formulaire de depense</a></p>");
                res.getWriter().write("<p><a href=\"/ETU003246/Dashboard\" >Dashboard</a></p>");
            }else{
                d.setDate(date2);
                d.setLibele(libele);
                d.setMontant(valeur);
                try{
                    d.save();
                    res.sendRedirect("/ETU003246/FormDepense");
                } catch (SQLException e) {
                    e.printStackTrace();
                    res.getWriter().write("SQL Error: " + e.getMessage());
                } 
            }
            }
            
        }
    }
    
}


