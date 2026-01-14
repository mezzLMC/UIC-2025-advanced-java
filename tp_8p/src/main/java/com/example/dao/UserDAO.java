package com.example.dao;

import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    
    private static final String DB_HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
    private static final String JDBC_URL = "jdbc:mysql://" + DB_HOST + ":3306/testdb?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";
    
    private static final String SELECT_ALL_USERS = "SELECT id, nom, email FROM user ORDER BY id";
    private static final String SELECT_USER_BY_ID = "SELECT id, nom, email FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user (nom, email) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET nom = ?, email = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SEARCH_USER_BY_NAME = "SELECT id, nom, email FROM user WHERE nom LIKE ? ORDER BY id";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erreur lors du chargement du driver MySQL", e);
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    public User getUserById(int id) {
        User user = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_ID)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setEmail(rs.getString("email"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
    
    
    public boolean insertUser(User user) {
        boolean result = false;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {
            
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            
            result = stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    public boolean updateUser(User user) {
        boolean result = false;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {
            
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());
            
            result = stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public boolean deleteUser(int id) {
        boolean result = false;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {
            
            stmt.setInt(1, id);
            
            result = stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_USER_BY_NAME)) {
            
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setEmail(rs.getString("email"));
                    users.add(user);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
}
