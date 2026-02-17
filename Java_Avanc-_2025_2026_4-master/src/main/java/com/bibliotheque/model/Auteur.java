package main.java.com.bibliotheque.model;

public class Auteur {

    private int idAuteur;
    private String nom;
    private String prenom;
    private String biographie;
    private String nationalite;

    public Auteur() {
    }

    public Auteur(int idAuteur, String nom, String prenom, String biographie, String nationalite) {
        this.idAuteur = idAuteur;
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;
        this.nationalite = nationalite;
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

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
}