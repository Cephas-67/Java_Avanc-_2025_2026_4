package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Emprunt;
import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.model.Membre;
import main.java.com.bibliotheque.service.EmpruntService;
import main.java.com.bibliotheque.view.EmpruntView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Contrôleur pour gérer les interactions de la vue Emprunt
 */
public class EmpruntController {

    private final EmpruntView view;
    private final EmpruntService service;

    public EmpruntController(EmpruntView view) {
        this.view = view;
        this.service = new EmpruntService();
        initController();
        loadData();
    }

    /**
     * Initialiser les écouteurs d'événements
     */
    private void initController() {
        // Bouton Enregistrer Emprunt
        view.getAddButton().addActionListener(e -> handleAddEmprunt());

        // Bouton Enregistrer Retour
        view.getReturnButton().addActionListener(e -> handleReturn());

        // Bouton Supprimer
        view.getDeleteButton().addActionListener(e -> handleDelete());

        // Bouton Actualiser
        view.getRefreshButton().addActionListener(e -> loadEmprunts());

        // Recherche (optionnelle pour les emprunts)
        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // La recherche peut être implémentée plus tard si nécessaire
            }
        });
    }

    /**
     * Charger les données initiales
     */
    private void loadData() {
        loadMembres();
        loadLivres();
        loadEmprunts();
    }

    /**
     * Charger la liste des membres dans le combo
     */
    private void loadMembres() {
        try {
            List<Membre> membres = service.getAllMembres();
            view.updateMembreCombo(membres);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des membres: " + ex.getMessage());
        }
    }

    /**
     * Charger la liste des livres dans le combo
     */
    private void loadLivres() {
        try {
            List<Livre> livres = service.getAllLivres();
            view.updateLivreCombo(livres);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des livres: " + ex.getMessage());
        }
    }

    /**
     * Charger tous les emprunts
     */
    private void loadEmprunts() {
        try {
            List<Emprunt> emprunts = service.getAllEmprunts();
            displayEmprunts(emprunts);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des emprunts: " + ex.getMessage());
        }
    }

    /**
     * Gérer l'ajout d'un emprunt
     */
    private void handleAddEmprunt() {
        try {
            int idMembre = view.getSelectedMembreId();
            int idLivre = view.getSelectedLivreId();

            if (idMembre == -1) {
                showWarning("Veuillez sélectionner un membre.");
                return;
            }

            if (idLivre == -1) {
                showWarning("Veuillez sélectionner un livre.");
                return;
            }

            service.enregistrerEmprunt(idMembre, idLivre);
            view.clearForm();
            loadEmprunts();
            showSuccess("Emprunt enregistré avec succès!");

        } catch (Exception ex) {
            showError("Erreur lors de l'enregistrement de l'emprunt: " + ex.getMessage());
        }
    }

    /**
     * Gérer le retour d'un emprunt
     */
    private void handleReturn() {
        try {
            int idEmprunt = view.getSelectedEmpruntId();

            if (idEmprunt == -1) {
                showWarning("Veuillez sélectionner un emprunt dans le tableau.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirmer le retour de ce livre?",
                    "Confirmation de retour",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.enregistrerRetour(idEmprunt);
                loadEmprunts();
                showSuccess("Retour enregistré avec succès!");
            }

        } catch (Exception ex) {
            showError("Erreur lors de l'enregistrement du retour: " + ex.getMessage());
        }
    }

    /**
     * Gérer la suppression d'un emprunt (non implémentée car généralement on ne supprime pas les emprunts)
     */
    private void handleDelete() {
        showWarning("La suppression des emprunts n'est pas autorisée pour préserver l'historique.");
    }

    /**
     * Afficher la liste des emprunts dans le tableau
     */
    private void displayEmprunts(List<Emprunt> emprunts) {
        view.clearTable();
        for (Emprunt emprunt : emprunts) {
            view.addEmpruntToTable(emprunt);
        }
    }

    /**
     * Afficher un message de succès
     */
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Succès",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Afficher un message d'erreur
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Afficher un message d'avertissement
     */
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Attention",
                JOptionPane.WARNING_MESSAGE
        );
    }
}
