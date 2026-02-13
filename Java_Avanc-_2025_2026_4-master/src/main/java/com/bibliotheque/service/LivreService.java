package com.bibliotheque.service;

import com.bibliotheque.dao.impl.LivreDAOImpl;
import com.bibliotheque.dao.interfaces.LivreDAO;
import com.bibliotheque.model.Livre;

import java.util.List;

/**
 * Service pour la gestion des livres avec logique métier
 */
public class LivreService {

    private final LivreDAOImpl livreDAO;

    public LivreService() {
        this.livreDAO = new LivreDAOImpl();
    }

    /**
     * Ajouter un nouveau livre avec validation
     */
    public void addLivre(Livre livre) throws Exception {
        validateLivre(livre);
        livreDAO.add(livre);
    }

    /**
     * Modifier un livre existant
     */
    public void updateLivre(Livre livre) throws Exception {
        if (livre.getIdLivre() <= 0) {
            throw new Exception("ID du livre invalide");
        }
        validateLivre(livre);
        livreDAO.update(livre);
    }

    /**
     * Supprimer un livre
     */
    public void deleteLivre(int idLivre) throws Exception {
        if (idLivre <= 0) {
            throw new Exception("ID du livre invalide");
        }
        livreDAO.delete(idLivre);
    }

    /**
     * Rechercher un livre par ID
     */
    public Livre findById(int idLivre) {
        return livreDAO.findById(idLivre);
    }

    /**
     * Récupérer tous les livres
     */
    public List<Livre> getAllLivres() {
        return livreDAO.findAll();
    }

    /**
     * Rechercher des livres par mot-clé
     */
    public List<Livre> searchLivres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllLivres();
        }
        return livreDAO.search(keyword.trim());
    }

    /**
     * Valider les données d'un livre
     */
    private void validateLivre(Livre livre) throws Exception {
        if (livre == null) {
            throw new Exception("Le livre ne peut pas être null");
        }

        if (livre.getTitre() == null || livre.getTitre().trim().isEmpty()) {
            throw new Exception("Le titre est obligatoire");
        }

        if (livre.getAnnee() < 1000 || livre.getAnnee() > 2100) {
            throw new Exception("L'année de publication doit être entre 1000 et 2100");
        }
    }
}
