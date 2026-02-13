package com.bibliotheque.model;

public class Livre {

    private int idLivre;
    private String titre;
    private int annee;
    private String categorie;

    public Livre() {
    }

    public Livre(int idLivre, String titre, int annee, String categorie) {
        this.idLivre = idLivre;
        this.titre = titre;
        this.annee = annee;
        this.categorie = categorie;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return titre + " (" + annee + ")";
    }
}
