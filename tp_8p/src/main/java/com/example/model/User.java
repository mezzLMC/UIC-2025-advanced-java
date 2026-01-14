package com.example.model;

import java.io.Serializable;

public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nom;
    private String email;
    
    public User() {
    }
    
    public User(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }
    
    public User(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User [id=" + id + ", nom=" + nom + ", email=" + email + "]";
    }
}
