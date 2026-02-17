package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.LivreDAO;
import main.java.com.bibliotheque.model.Livre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du DAO pour la gestion des livres (SANS BASE DE DONNÉES)
 */
public class LivreDAOImpl implements LivreDAO {

    // Stockage en mémoire
    private final List<Livre> livres = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(Livre livre) {
        if (livre == null) {
            throw new IllegalArgumentException("Le livre ne peut pas être null");
        }
        livre.setIdLivre(nextId++);
        livres.add(livre);
        System.out.println("Livre ajouté avec succès: " + livre.getTitre() + " (ID: " + livre.getIdLivre() + ")");
    }

    @Override
    public void update(Livre livre) {
        Livre existingLivre = findById(livre.getIdLivre());
        if (existingLivre != null) {
            existingLivre.setTitre(livre.getTitre());
            existingLivre.setAnneePublication(livre.getAnneePublication());
            existingLivre.setCategorie(livre.getCategorie());
            System.out.println("Livre modifié avec succès: " + livre.getTitre());
        } else {
            throw new RuntimeException("Livre non trouvé avec l'ID: " + livre.getIdLivre());
        }
    }

    @Override
    public void delete(int idLivre) {
        Livre livre = findById(idLivre);
        if (livre != null) {
            livres.remove(livre);
            System.out.println("Livre supprimé avec succès (ID: " + idLivre + ")");
        } else {
            throw new RuntimeException("Livre non trouvé avec l'ID: " + idLivre);
        }
    }

    @Override
    public Livre findById(int idLivre) {
        return livres.stream()
                .filter(l -> l.getIdLivre() == idLivre)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Livre> findAll() {
        return new ArrayList<>(livres);
    }

    /**
     * Méthode de recherche (si elle existe dans l'interface)
     */
    public List<Livre> search(String keyword) {
        return livres.stream()
                .filter(l -> l.getTitre().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}