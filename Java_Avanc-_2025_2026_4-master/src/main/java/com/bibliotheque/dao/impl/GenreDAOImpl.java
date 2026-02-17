package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.GenreDAO;
import main.java.com.bibliotheque.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du DAO pour la gestion des genres (SANS BASE DE DONNÉES)
 */
public class GenreDAOImpl implements GenreDAO {

    // Stockage en mémoire
    private final List<Genre> genres = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Le genre ne peut pas être null");
        }
        genre.setIdGenre(nextId++);
        genres.add(genre);
        System.out.println("Genre ajouté avec succès: " + genre.getNom() + " (ID: " + genre.getIdGenre() + ")");
    }

    @Override
    public void update(Genre genre) {
        Genre existing = findById(genre.getIdGenre());
        if (existing != null) {
            existing.setNom(genre.getNom());
            existing.setDescription(genre.getDescription());
            System.out.println("Genre modifié avec succès: " + genre.getNom());
        } else {
            throw new RuntimeException("Genre non trouvé avec l'ID: " + genre.getIdGenre());
        }
    }

    @Override
    public void delete(int idGenre) {
        Genre genre = findById(idGenre);
        if (genre != null) {
            genres.remove(genre);
            System.out.println("Genre supprimé avec succès (ID: " + idGenre + ")");
        } else {
            throw new RuntimeException("Genre non trouvé avec l'ID: " + idGenre);
        }
    }

    @Override
    public Genre findById(int idGenre) {
        return genres.stream()
                .filter(g -> g.getIdGenre() == idGenre)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Genre> findAll() {
        return new ArrayList<>(genres);
    }
}