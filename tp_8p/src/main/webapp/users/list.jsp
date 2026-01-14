<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.User" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { margin-bottom: 20px; }
        .message { padding: 10px; margin-bottom: 15px; }
        .success { background-color: #dff0d8; color: #3c763d; }
        .error { background-color: #f2dede; color: #a94442; }
        .info { background-color: #d9edf7; color: #31708f; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f5f5f5; }
        a { color: #337ab7; }
        .actions { margin-bottom: 15px; }
        input[type="text"] { padding: 5px; }
    </style>
</head>
<body>
    <h1>Gestion des Utilisateurs</h1>
    
    <% String successMessage = (String) request.getAttribute("successMessage"); %>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% String infoMessage = (String) request.getAttribute("infoMessage"); %>
    
    <% if (successMessage != null) { %>
        <div class="message success"><%= successMessage %></div>
    <% } %>
    <% if (errorMessage != null) { %>
        <div class="message error"><%= errorMessage %></div>
    <% } %>
    <% if (infoMessage != null) { %>
        <div class="message info"><%= infoMessage %></div>
    <% } %>
    
    <div class="actions">
        <a href="users?action=new">Nouvel Utilisateur</a> | 
        <form action="users" method="get" style="display: inline;">
            <input type="hidden" name="action" value="search">
            <input type="text" name="searchName" placeholder="Rechercher..." 
                   value="<%= request.getAttribute("searchName") != null ? request.getAttribute("searchName") : "" %>">
            <button type="submit">Rechercher</button>
            <a href="users">Reset</a>
        </form>
    </div>
    
    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getNom() %></td>
                <td><%= user.getEmail() %></td>
                <td>
                    <a href="users?action=edit&id=<%= user.getId() %>">Modifier</a>
                    <a href="users?action=delete&id=<%= user.getId() %>" 
                       onclick="return confirm('Supprimer cet utilisateur ?');">Supprimer</a>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="4">Aucun utilisateur</td></tr>
        <% } %>
    </table>
</body>
</html>
