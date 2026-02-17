package main.java.com.bibliotheque.model;

public class MaisonEdition {

    private int idMaisonEdition;
    private String nom;
    private String adresse;
    private String email;
    private String telephone;

    public MaisonEdition() {
    }

    public MaisonEdition(int idMaisonEdition, String nom, String adresse, String email, String telephone) {
        this.idMaisonEdition = idMaisonEdition;
        this.nom = nom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }

    public int getIdMaisonEdition() {
        return idMaisonEdition;
    }

    public void setIdMaisonEdition(int idMaisonEdition) {
        this.idMaisonEdition = idMaisonEdition;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
}