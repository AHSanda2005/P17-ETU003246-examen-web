package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    //private static final String URL = "jdbc:postgresql://localhost:5432/departement";
    //private static final String USER = "postgres";
    // private static final String PASSWORD = "postgres";

    private static final String URL = "jdbc:mysql://172.80.237.53:3306/db_s2_ETU003246?useSSL=false&serverTimezone=UTC";
    //private static final String USER = "root"; 
    //private static final String PASSWORD = "";
    private static final String USER = "ETU003246"; 
    private static final String PASSWORD = "VM0SevbB"; 


    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            //Class.forName("org.postgresql.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connection established successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found: " + e.getMessage());
        }
        return connection;
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée avec succès.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}
