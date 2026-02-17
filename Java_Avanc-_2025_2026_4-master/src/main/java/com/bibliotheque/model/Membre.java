package main.java.com.bibliotheque.model;

public class Membre {

    private int idMembre;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String lieuResidence;
    private double penalite;

    public Membre() {
    }

    public Membre(int idMembre, String nom, String prenom, String email,
                  String telephone, String lieuResidence, double penalite) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.lieuResidence = lieuResidence;
        this.penalite = penalite;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLieuResidence() {
        return lieuResidence;
    }

    public void setLieuResidence(String lieuResidence) {
        this.lieuResidence = lieuResidence;
    }

    public double getPenalite() {
        return penalite;
    }

    public void setPenalite(double penalite) {
        this.penalite = penalite;
    }
}
