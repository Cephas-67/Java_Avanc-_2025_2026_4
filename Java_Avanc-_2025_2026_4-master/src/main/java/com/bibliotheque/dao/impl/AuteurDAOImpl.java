package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.AuteurDAO;
import main.java.com.bibliotheque.model.Auteur;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du DAO pour la gestion des auteurs (SANS BASE DE DONNÉES)
 */
public class AuteurDAOImpl implements AuteurDAO {

    // Stockage en mémoire
    private final List<Auteur> auteurs = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(Auteur auteur) {
        if (auteur == null) {
            throw new IllegalArgumentException("L'auteur ne peut pas être null");
        }
        auteur.setIdAuteur(nextId++);
        auteurs.add(auteur);
        System.out.println("Auteur ajouté avec succès: " + auteur.getNom() + " (ID: " + auteur.getIdAuteur() + ")");
    }

    @Override
    public void update(Auteur auteur) {
        Auteur existing = findById(auteur.getIdAuteur());
        if (existing != null) {
            existing.setNom(auteur.getNom());
            existing.setPrenom(auteur.getPrenom());
            existing.setBiographie(auteur.getBiographie());
            existing.setNationalite(auteur.getNationalite());
            System.out.println("Auteur modifié avec succès: " + auteur.getNom());
        } else {
            throw new RuntimeException("Auteur non trouvé avec l'ID: " + auteur.getIdAuteur());
        }
    }

    @Override
    public void delete(int idAuteur) {
        Auteur auteur = findById(idAuteur);
        if (auteur != null) {
            auteurs.remove(auteur);
            System.out.println("Auteur supprimé avec succès (ID: " + idAuteur + ")");
        } else {
            throw new RuntimeException("Auteur non trouvé avec l'ID: " + idAuteur);
        }
    }

    @Override
    public Auteur findById(int idAuteur) {
        return auteurs.stream()
                .filter(a -> a.getIdAuteur() == idAuteur)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Auteur> findAll() {
        return new ArrayList<>(auteurs);
    }
}