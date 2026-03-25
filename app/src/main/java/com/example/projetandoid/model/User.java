package com.example.projetandoid.model;

public class User {
    private String uid;
    private String nom;
    private String email;
    private String role;

    public User() {
    }

    public User(String uid, String nom, String email, String role) {
        this.uid = uid;
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
