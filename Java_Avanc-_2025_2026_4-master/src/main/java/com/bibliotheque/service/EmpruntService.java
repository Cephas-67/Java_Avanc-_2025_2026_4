package com.bibliotheque.service;

import com.bibliotheque.dao.impl.EmpruntDAOImpl;
import com.bibliotheque.dao.impl.LivreDAOImpl;
import com.bibliotheque.dao.impl.MembreDAOImpl;
import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Membre;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des emprunts avec logique métier
 */
public class EmpruntService {

    private final EmpruntDAOImpl empruntDAO;
    private final MembreDAOImpl membreDAO;
    private final LivreDAOImpl livreDAO;

    public EmpruntService() {
        this.empruntDAO = new EmpruntDAOImpl();
        this.membreDAO = new MembreDAOImpl();
        this.livreDAO = new LivreDAOImpl();
    }

    /**
     * Enregistrer un nouvel emprunt
     */
    public void enregistrerEmprunt(int idMembre, int idLivre) throws Exception {
        Membre membre = membreDAO.findById(idMembre);
        if (membre == null) {
            throw new Exception("Membre introuvable");
        }

        Livre livre = livreDAO.findById(idLivre);
        if (livre == null) {
            throw new Exception("Livre introuvable");
        }

        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetourPrevue = dateEmprunt.plusWeeks(2); // 2 semaines

        Emprunt emprunt = new Emprunt(
                0,
                membre,
                livre,
                dateEmprunt,
                dateRetourPrevue,
                null
        );

        empruntDAO.enregistrerEmprunt(emprunt);
    }

    /**
     * Enregistrer le retour d'un emprunt
     */
    public void enregistrerRetour(int idEmprunt) throws Exception {
        if (idEmprunt <= 0) {
            throw new Exception("ID d'emprunt invalide");
        }
        empruntDAO.enregistrerRetour(idEmprunt);
    }

    /**
     * Récupérer les emprunts d'un membre
     */
    public List<Emprunt> getEmpruntsByMembre(int idMembre) {
        return empruntDAO.findByMembre(idMembre);
    }

    /**
     * Récupérer tous les emprunts en cours
     */
    public List<Emprunt> getEmpruntsEnCours() {
        return empruntDAO.findEnCours();
    }

    /**
     * Récupérer tous les emprunts
     */
    public List<Emprunt> getAllEmprunts() {
        return empruntDAO.findAll();
    }

    /**
     * Récupérer tous les membres (pour les listes déroulantes)
     */
    public List<Membre> getAllMembres() {
        return membreDAO.findAll();
    }

    /**
     * Récupérer tous les livres (pour les listes déroulantes)
     */
    public List<Livre> getAllLivres() {
        return livreDAO.findAll();
    }
}
