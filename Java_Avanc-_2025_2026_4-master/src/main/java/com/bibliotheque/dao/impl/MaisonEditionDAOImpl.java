package main.java.com.bibliotheque.dao.impl;

import main.java.com.bibliotheque.dao.interfaces.MaisonEditionDAO;
import main.java.com.bibliotheque.model.MaisonEdition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du DAO pour la gestion des maisons d'édition (SANS BASE DE DONNÉES)
 */
public class MaisonEditionDAOImpl implements MaisonEditionDAO {

    // Stockage en mémoire
    private final List<MaisonEdition> maisonsEdition = new ArrayList<>();
    private int nextId = 1;

    @Override
    public void add(MaisonEdition maisonEdition) {
        if (maisonEdition == null) {
            throw new IllegalArgumentException("La maison d'édition ne peut pas être null");
        }
        maisonEdition.setIdMaisonEdition(nextId++);
        maisonsEdition.add(maisonEdition);
        System.out.println("Maison d'édition ajoutée avec succès: " + maisonEdition.getNom() + " (ID: " + maisonEdition.getIdMaisonEdition() + ")");
    }

    @Override
    public void update(MaisonEdition maisonEdition) {
        MaisonEdition existing = findById(maisonEdition.getIdMaisonEdition());
        if (existing != null) {
            existing.setNom(maisonEdition.getNom());
            existing.setAdresse(maisonEdition.getAdresse());
            existing.setEmail(maisonEdition.getEmail());
            existing.setTelephone(maisonEdition.getTelephone());
            System.out.println("Maison d'édition modifiée avec succès: " + maisonEdition.getNom());
        } else {
            throw new RuntimeException("Maison d'édition non trouvée avec l'ID: " + maisonEdition.getIdMaisonEdition());
        }
    }

    @Override
    public void delete(int idMaisonEdition) {
        MaisonEdition maisonEdition = findById(idMaisonEdition);
        if (maisonEdition != null) {
            maisonsEdition.remove(maisonEdition);
            System.out.println("Maison d'édition supprimée avec succès (ID: " + idMaisonEdition + ")");
        } else {
            throw new RuntimeException("Maison d'édition non trouvée avec l'ID: " + idMaisonEdition);
        }
    }

    @Override
    public MaisonEdition findById(int idMaisonEdition) {
        return maisonsEdition.stream()
                .filter(m -> m.getIdMaisonEdition() == idMaisonEdition)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MaisonEdition> findAll() {
        return new ArrayList<>(maisonsEdition);
    }
}