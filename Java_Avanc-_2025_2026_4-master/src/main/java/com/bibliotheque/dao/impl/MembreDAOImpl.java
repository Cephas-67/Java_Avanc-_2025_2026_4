package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.MembreDAO;
import main.java.com.bibliotheque.model.Membre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public  class MembreDAOImpl implements MembreDAO {

    private final List<Membre> membres = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(Membre membre) {
        if (membre == null) {
            throw new IllegalArgumentException("Le membre ne peut pas être null");
        }
        membre.setIdMembre(nextId++);
        membres.add(membre);
        System.out.println("Membre ajouté avec succès: " + membre.getNom() + " (ID: " + membre.getIdMembre() + ")");
    }

    @Override
    public void update(Membre membre) {
        Membre existingMembre = findById(membre.getIdMembre());
        if (existingMembre != null) {
            existingMembre.setNom(membre.getNom());
            existingMembre.setPrenom(membre.getPrenom());
            existingMembre.setEmail(membre.getEmail());
            existingMembre.setTelephone(membre.getTelephone());
            existingMembre.setLieuResidence(membre.getLieuResidence());
            existingMembre.setPenalite(membre.getPenalite());
            System.out.println("Membre modifié avec succès: " + membre.getNom());
        } else {
            throw new RuntimeException("Membre non trouvé avec l'ID: " + membre.getIdMembre());
        }
    }

    @Override
    public void delete(int idMembre) {
        Membre membre = findById(idMembre);
        if (membre != null) {
            membres.remove(membre);
            System.out.println("Membre supprimé avec succès (ID: " + idMembre + ")");
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
    public List<Membre> search(String trim) {
        return List.of();
    }
}