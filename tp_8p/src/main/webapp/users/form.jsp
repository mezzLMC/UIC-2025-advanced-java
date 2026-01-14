<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.User" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <%
        User user = (User) request.getAttribute("user");
        boolean isEdit = (user != null);
        String pageTitle = isEdit ? "Modifier Utilisateur" : "Nouvel Utilisateur";
        String action = isEdit ? "update" : "insert";
        
        String nom = "";
        String email = "";
        int id = 0;
        
        if (isEdit) {
            id = user.getId();
            nom = user.getNom() != null ? user.getNom() : "";
            email = user.getEmail() != null ? user.getEmail() : "";
        } else {
            nom = request.getAttribute("nom") != null ? (String) request.getAttribute("nom") : "";
            email = request.getAttribute("email") != null ? (String) request.getAttribute("email") : "";
        }
    %>
    <title><%= pageTitle %></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { margin-bottom: 20px; }
        .error { background-color: #f2dede; color: #a94442; padding: 10px; margin-bottom: 15px; }
        label { display: block; margin-top: 10px; }
        input[type="text"], input[type="email"] { width: 300px; padding: 5px; margin-top: 5px; }
        .buttons { margin-top: 20px; }
        a { color: #337ab7; }
    </style>
</head>
<body>
    <h1><%= pageTitle %></h1>
    
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <div class="error"><%= errorMessage %></div>
    <% } %>
    
    <form action="users" method="post">
        <input type="hidden" name="action" value="<%= action %>">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= id %>">
        <% } %>
        
        <label>Nom *</label>
        <input type="text" name="nom" value="<%= nom %>" required>
        
        <label>Email *</label>
        <input type="email" name="email" value="<%= email %>" required>
        
        <div class="buttons">
            <button type="submit"><%= isEdit ? "Enregistrer" : "Creer" %></button>
            <a href="users">Annuler</a>
        </div>
    </form>
</body>
</html>
