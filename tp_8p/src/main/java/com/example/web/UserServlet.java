package com.example.web;

import com.example.dao.UserDAO;
import com.example.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;


@WebServlet("/users")
public class UserServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "search":
                searchUsers(request, response);
                break;
            case "list":
            default:
                listUsers(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "";
        }
        
        switch (action) {
            case "insert":
                insertUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/users/list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/users/form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUserById(id);
            
            if (user != null) {
                request.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/users/form.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Utilisateur non trouvé (ID: " + id + ")");
                listUsers(request, response);
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID utilisateur invalide");
            listUsers(request, response);
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        
        String errorMessage = validateUserData(nom, email);
        
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("nom", nom);
            request.setAttribute("email", email);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/users/form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        User user = new User(nom, email);
        
        if (userDAO.insertUser(user)) {
            request.setAttribute("successMessage", "Utilisateur créé avec succès !");
        } else {
            request.setAttribute("errorMessage", "Erreur lors de la création de l'utilisateur");
        }
        
        listUsers(request, response);
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        int id;
        
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID utilisateur invalide");
            listUsers(request, response);
            return;
        }
        
        String errorMessage = validateUserData(nom, email);
        
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            User user = new User(id, nom, email);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/users/form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        User user = new User(id, nom, email);
        
        if (userDAO.updateUser(user)) {
            request.setAttribute("successMessage", "Utilisateur mis à jour avec succès !");
        } else {
            request.setAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur");
        }
        
        listUsers(request, response);
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            
            if (userDAO.deleteUser(id)) {
                request.setAttribute("successMessage", "Utilisateur supprimé avec succès !");
            } else {
                request.setAttribute("errorMessage", "Erreur lors de la suppression de l'utilisateur");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID utilisateur invalide");
        }
        
        listUsers(request, response);
    }
    
    private void searchUsers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchName = request.getParameter("searchName");
        
        if (searchName == null || searchName.trim().isEmpty()) {
            listUsers(request, response);
            return;
        }
        
        List<User> users = userDAO.searchByName(searchName.trim());
        request.setAttribute("users", users);
        request.setAttribute("searchName", searchName);
        
        if (users.isEmpty()) {
            request.setAttribute("infoMessage", "Aucun utilisateur trouvé pour : " + searchName);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/users/list.jsp");
        dispatcher.forward(request, response);
    }
    
    private String validateUserData(String nom, String email) {
        if (nom == null || nom.trim().isEmpty()) {
            return "Le nom est obligatoire";
        }
        
        if (nom.trim().length() < 2) {
            return "Le nom doit contenir au moins 2 caractères";
        }
        
        if (nom.trim().length() > 100) {
            return "Le nom ne doit pas dépasser 100 caractères";
        }
        
        if (email == null || email.trim().isEmpty()) {
            return "L'email est obligatoire";
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Format d'email invalide";
        }
        
        if (email.trim().length() > 150) {
            return "L'email ne doit pas dépasser 150 caractères";
        }
        
        return null;
    }
}
