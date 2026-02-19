package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.MembreDAO;
import main.java.com.bibliotheque.model.Membre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MembreDAOImpl implements MembreDAO {

    private final List<Membre> membres = new ArrayList<>();
    private int nextId = 1;
    // Méthode pour réinitialiser tous les membres
    public void reset() {
        membres.clear();
        nextId = 1;
        System.out.println("Tous les membres ont été réinitialisés.");
    }
    public MembreDAOImpl() {
        initializeNextId();
    }

    private void initializeNextId() {
        nextId = membres.stream()
                .mapToInt(Membre::getIdMembre)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void add(Membre membre) {
        if (membre == null) {
            throw new IllegalArgumentException("Le membre ne peut pas être null");
        }

        if (membre.getIdMembre() <= 0) {
            membre.setIdMembre(nextId);
        } else if (membre.getIdMembre() >= nextId) {
            nextId = membre.getIdMembre() + 1;
        }

        membres.add(membre);
        nextId++;
        System.out.println("Membre ajouté avec succès: " + membre.getNom() + " (ID: " + membre.getIdMembre() + ")");
    }

    @Override
    public void update(Membre membre) {
        Membre existing = findById(membre.getIdMembre());
        if (existing != null) {
            existing.setNom(membre.getNom());
            existing.setPrenom(membre.getPrenom());
            existing.setEmail(membre.getEmail());
            existing.setTelephone(membre.getTelephone());
            existing.setLieuResidence(membre.getLieuResidence());
            existing.setPenalite(membre.getPenalite());
        } else {
            throw new RuntimeException("Membre non trouvé avec l'ID: " + membre.getIdMembre());
        }
    }

    @Override
    public void delete(int idMembre) {
        Membre membre = findById(idMembre);
        if (membre != null) {
            membres.remove(membre);
        } else {
            throw new RuntimeException("Membre non trouvé avec l'ID: " + idMembre);
        }
    }

    @Override
    public Membre findById(int idMembre) {
        return membres.stream()
                .filter(m -> m.getIdMembre() == idMembre)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Membre> findAll() {
        return new ArrayList<>(membres);
    }

    @Override
    public List<Membre> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lower = keyword.toLowerCase().trim();
        return membres.stream()
                .filter(m -> m.getNom().toLowerCase().contains(lower) ||
                        m.getPrenom().toLowerCase().contains(lower) ||
                        m.getEmail().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
