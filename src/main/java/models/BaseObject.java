package models;

import connexion.Connexion;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseObject<T> {
    private int id;
    private String nom;
    private final Class<T> type;

    public BaseObject(Class<T> type) {
        this.type = type;
    }

    public BaseObject(Class<T> type, String nom) {
        this.type = type;
        this.nom = nom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public void save() throws SQLException {
        Connection connection = Connexion.getConnection();
        String tableName = type.getSimpleName().toLowerCase();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : this.getClass().getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                field.setAccessible(true);
                fields.append(field.getName()).append(",");
                values.append("?,");
            }
        }
        if (fields.length() > 0) {
            fields.setLength(fields.length() - 1);
            values.setLength(values.length() - 1);
        } else {
            throw new IllegalStateException("No fields to insert for table: " + tableName);
        }
        

        String query = "INSERT INTO " + tableName + " (" + fields + ") VALUES (" + values + ")";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            for (Field field : this.getClass().getDeclaredFields()) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    statement.setObject(index++, field.get(this));
                }
            }
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void remove() throws SQLException {
        Connection connection = Connexion.getConnection();
        String tableName = type.getSimpleName().toLowerCase();
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.getId());
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    public List<T> findPagination(int limit, int offset) throws SQLException {
        List<T> results = new ArrayList<>();
        Connection connection = Connexion.getConnection();
        String tableName = type.getSimpleName().toLowerCase();

        String query = "SELECT * FROM " + tableName + " LIMIT ? OFFSET ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                try {
                    T obj = type.getDeclaredConstructor().newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        field.set(obj, rs.getObject(field.getName()));
                    }
                    results.add(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            connection.close();
        }
        return results;
    }

    public T getById(int id) throws SQLException {
        Connection connection = Connexion.getConnection();
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                T obj = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return null; 
    }
    
    public void update(Object... params) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Connexion.getConnection();
            StringBuilder setClause = new StringBuilder();
            Field[] fields = type.getDeclaredFields();
            
            for (Field field : fields) {
                if (!field.getName().equals("id")) { 
                    setClause.append(field.getName()).append(" = ?,");
                }
            }
            setClause.setLength(setClause.length() - 1);
    
            String query = "UPDATE " + type.getSimpleName().toLowerCase() + " SET " + setClause + " WHERE id = ?";
            statement = connection.prepareStatement(query);
            
            int paramIndex = 1;
            for (Field field : fields) {
                if (!field.getName().equals("id")) { 
                    statement.setObject(paramIndex, params[paramIndex - 1]);
                    paramIndex++;
                }
            }
            statement.setInt(paramIndex, this.getId());
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows Updated: " + rowsUpdated);
    
            if (rowsUpdated == 0) {
                System.out.println("No rows updated! Check if ID exists or if values are the same.");
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    
    
    public List<T> findAll() throws SQLException {
        List<T> results = new ArrayList<>();
        Connection connection = Connexion.getConnection();
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() ;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                T obj = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }
                results.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return results;
    }
    
}

