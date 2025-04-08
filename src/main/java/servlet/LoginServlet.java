package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import models.Utilisateur;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        String userName = req.getParameter("user");
        String password = req.getParameter("pwd");
        String Use ="1";
        String pass ="2";
        if (userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty() ) {
            res.getWriter().write("Error: les valeur ne doivent pas etre vide.");
            res.sendRedirect("/dept");
        }else{
            Utilisateur U = new Utilisateur();
        try {
            List<Utilisateur> Users=U.findAll();
            for (Utilisateur User: Users) {
                Use=User.getNom();
                pass= User.getPassword();
                if (Use.equals(userName) && pass.equals(password)){
                    HttpSession session = req.getSession();
                    session.setAttribute("id", User.getId());
                    res.sendRedirect("/ETU003246");
                }
            }
            res.getWriter().write("probleme de mot de passe ou de nom");
            res.getWriter().write("<p><a href=\"/ETU003246/\" >Reesayer</a></p>");
        } catch (SQLException e) {
            e.printStackTrace();
            res.getWriter().write("SQL Error: " + e.getMessage());
        } 
        }
    }
    
}


