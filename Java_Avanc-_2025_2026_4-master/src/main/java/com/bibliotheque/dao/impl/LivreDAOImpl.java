package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.LivreDAO;
import main.java.com.bibliotheque.model.Livre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LivreDAOImpl implements LivreDAO {

    private final List<Livre> livres = new ArrayList<>();
    private int nextId = 1;

    public LivreDAOImpl() {
        initializeNextId();
    }
    // Méthode pour réinitialiser tous les livres
    public void reset() {
        livres.clear();
        nextId = 1;
        System.out.println("Tous les livres ont été réinitialisés.");
    }

    private void initializeNextId() {
        nextId = livres.stream()
                .mapToInt(Livre::getIdLivre)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void add(Livre livre) {
        if (livre == null) {
            throw new IllegalArgumentException("Le livre ne peut pas être null");
        }

        if (livre.getIdLivre() <= 0) {
            livre.setIdLivre(nextId);
        } else if (livre.getIdLivre() >= nextId) {
            nextId = livre.getIdLivre() + 1;
        }

        livres.add(livre);
        nextId++;
        System.out.println("Livre ajouté avec succès: " + livre.getTitre() + " (ID: " + livre.getIdLivre() + ")");
    }

    @Override
    public void update(Livre livre) {
        Livre existing = findById(livre.getIdLivre());
        if (existing != null) {
            existing.setTitre(livre.getTitre());
            existing.setAnneePublication(livre.getAnneePublication());
            existing.setCategorie(livre.getCategorie());
        } else {
            throw new RuntimeException("Livre non trouvé avec l'ID: " + livre.getIdLivre());
        }
    }

    @Override
    public void delete(int idLivre) {
        Livre livre = findById(idLivre);
        if (livre != null) {
            livres.remove(livre);
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

    @Override
    public List<Livre> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lower = keyword.toLowerCase().trim();
        return livres.stream()
                .filter(l -> l.getTitre().toLowerCase().contains(lower) ||
                        l.getCategorie().toLowerCase().contains(lower) ||
                        String.valueOf(l.getAnneePublication()).contains(lower))
                .collect(Collectors.toList());
    }
}