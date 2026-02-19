package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.EmpruntDAO;
import main.java.com.bibliotheque.model.Emprunt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpruntDAOImpl implements EmpruntDAO {

    private final List<Emprunt> emprunts = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void enregistrerEmprunt(Emprunt emprunt) {
        if (emprunt == null) {
            throw new IllegalArgumentException("L'emprunt ne peut pas être null");
        }
        if (emprunt.getIdEmprunt() <= 0) {
            emprunt.setIdEmprunt(nextId);
        } else if (emprunt.getIdEmprunt() >= nextId) {
            nextId = emprunt.getIdEmprunt() + 1;
        }
        emprunts.add(emprunt);
        nextId++;
        System.out.println("Emprunt enregistré avec succès (ID: " + emprunt.getIdEmprunt() + ")");
    }

    @Override
    public void enregistrerRetour(int idEmprunt) {
        Emprunt emprunt = findById(idEmprunt);
        if (emprunt != null) {
            emprunt.setDateRetourEffective(LocalDate.now());
            emprunt.setStatut("TERMINE");
            System.out.println("Retour enregistré avec succès pour l'emprunt ID: " + idEmprunt);
        } else {
            throw new RuntimeException("Emprunt non trouvé avec l'ID: " + idEmprunt);
        }
    }

    @Override
    public List<Emprunt> findByMembre(int idMembre) {
        return emprunts.stream()
                .filter(e -> e.getMembre() != null && e.getMembre().getIdMembre() == idMembre)
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findEnCours() {
        return emprunts.stream()
                .filter(e -> "EN_COURS".equals(e.getStatut()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Emprunt> findAll() {
        return new ArrayList<>(emprunts);
    }

    // ✅ CORRECTION : Rendre findById PUBLIC
    public Emprunt findById(int idEmprunt) {
        return emprunts.stream()
                .filter(e -> e.getIdEmprunt() == idEmprunt)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Emprunt> search(String keyword) {
        return List.of();
    }

    // ✅ NOUVELLE MÉTHODE - Réinitialiser tous les emprunts
    public void reset() {
        emprunts.clear();
        nextId = 1;
        System.out.println("Tous les emprunts ont été réinitialisés.");
    }
}