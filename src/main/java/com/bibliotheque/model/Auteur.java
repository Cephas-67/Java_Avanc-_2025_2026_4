package com.bibliotheque.model;

public class Auteur {

    private int idAuteur;
    private String nom;
    private String prenom;
    private String bibliographie;
    private String email;

    public Auteur() {
    }

    public Auteur(int idAuteur, String nom, String prenom, String bibliographie, String email) {
        this.idAuteur = idAuteur;
        this.nom = nom;
        this.prenom = prenom;
        this.bibliographie = bibliographie;
        this.email = email;
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getBibliographie() {
        return bibliographie;
    }

    public void setBibliographie(String bibliographie) {
        this.bibliographie = bibliographie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
