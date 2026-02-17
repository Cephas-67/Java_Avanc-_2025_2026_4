package main.java.com.bibliotheque.model;

public class Genre {

    private int idGenre;
    private String nom;

    public Genre() {
    }

    public Genre(int idGenre, String nom) {
        this.idGenre = idGenre;
        this.nom = nom;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Object getDescription() {
        return null;
    }

    public void setDescription(Object description) {
    }
}
