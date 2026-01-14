package com.tp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String SELECT_ALL = "SELECT id, nom, email FROM user ORDER BY id";
    private static final String INSERT = "INSERT INTO user (nom, email) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE user SET nom = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("email")
                );
                users.add(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
        }
        
        return users;
    }
    
    
    public boolean insertUser(User user) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public boolean updateUser(User user) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE)) {

            pstmt.setString(1, user.getNom());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getId());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return true;
            } else {
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    public boolean deleteUser(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0)
                return true;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
