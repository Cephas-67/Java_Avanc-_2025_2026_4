package main.java.com.bibliotheque.model;

public class Livre {
    private int idLivre;
    private String titre;
    private int anneePublication;
    private String categorie;
    private String auteur;
    private String maisonEdition;

    public Livre() {}

    public Livre(int idLivre, String titre, int anneePublication, String categorie,
                 String auteur, String maisonEdition) {
        this.idLivre = idLivre;
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.categorie = categorie;
        this.auteur = auteur;
        this.maisonEdition = maisonEdition;
    }

    public Livre(int idLivre, String titre, int anneePublication, String categorie) {
        this(idLivre, titre, anneePublication, categorie, "", "");
    }

    public int getIdLivre() { return idLivre; }
    public void setIdLivre(int idLivre) { this.idLivre = idLivre; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getAnneePublication() { return anneePublication; }
    public void setAnneePublication(int anneePublication) { this.anneePublication = anneePublication; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getMaisonEdition() { return maisonEdition; }
    public void setMaisonEdition(String maisonEdition) { this.maisonEdition = maisonEdition; }

    public int getAnnee() { return anneePublication; }
}