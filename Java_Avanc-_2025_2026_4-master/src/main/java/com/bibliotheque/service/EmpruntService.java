package com.bibliotheque.service;

import com.bibliotheque.dao.impl.EmpruntDAOImpl;
import com.bibliotheque.dao.impl.LivreDAOImpl;
import com.bibliotheque.dao.impl.MembreDAOImpl;
import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Membre;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service pour la gestion des emprunts avec logique métier
 */
public class EmpruntService {

    private final EmpruntDAOImpl empruntDAO;
    private final MembreDAOImpl membreDAO;
    private final LivreDAOImpl livreDAO;

    private static final double PENALITE_PAR_JOUR = 100.0; // 100 FCFA par jour

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

        if (!livre.estDisponible()) {
            throw new Exception("Livre non disponible");
        }

        // Diminuer la quantité disponible
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreDAO.update(livre);

        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetourPrevue = dateEmprunt.plusWeeks(2);

        Emprunt emprunt = new Emprunt(
                0,
                membre,
                livre,
                dateEmprunt,
                dateRetourPrevue,
                null
        );

        empruntDAO.enregistrerEmprunt(emprunt);

        System.out.println("Emprunt enregistré avec succès.");
    }

    /**
     * Enregistrer le retour d'un emprunt
     */
    public void enregistrerRetour(int idEmprunt) throws Exception {

        if (idEmprunt <= 0) {
            throw new Exception("ID d'emprunt invalide");
        }

        Emprunt emprunt = empruntDAO.findById(idEmprunt);
        if (emprunt == null) {
            throw new Exception("Emprunt introuvable");
        }

        LocalDate dateRetourReelle = LocalDate.now();
        emprunt.setDateRetourReelle(dateRetourReelle);

        // Calcul pénalité
        long joursRetard = ChronoUnit.DAYS.between(
                emprunt.getDateRetourPrevue(),
                dateRetourReelle
        );

        if (joursRetard > 0) {
            double penalite = joursRetard * PENALITE_PAR_JOUR;

            Membre membre = emprunt.getMembre();
            membre.setPenalite(membre.getPenalite() + penalite);
            membreDAO.update(membre);

            System.out.println("Pénalité appliquée : " + penalite + " FCFA");
        }

        // Augmenter la quantité disponible
        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreDAO.update(livre);

        // Mettre à jour le retour en base
        empruntDAO.enregistrerRetour(idEmprunt);

        System.out.println("Retour enregistré avec succès.");
    }

    public List<Emprunt> getEmpruntsByMembre(int idMembre) {
        return empruntDAO.findByMembre(idMembre);
    }

    public List<Emprunt> getEmpruntsEnCours() {
        return empruntDAO.findEnCours();
    }

    public List<Emprunt> getAllEmprunts() {
        return empruntDAO.findAll();
    }

    public List<Membre> getAllMembres() {
        return membreDAO.findAll();
    }

    public List<Livre> getAllLivres() {
        return livreDAO.findAll();
    }
}
