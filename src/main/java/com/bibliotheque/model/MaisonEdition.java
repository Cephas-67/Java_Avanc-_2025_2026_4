package com.bibliotheque.model;

public class MaisonEdition {

    private int idMaisonEdition;
    private String nom;
    private String adresse;

    public MaisonEdition() {
    }

    public MaisonEdition(int idMaisonEdition, String nom, String adresse) {
        this.idMaisonEdition = idMaisonEdition;
        this.nom = nom;
        this.adresse = adresse;
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
}
