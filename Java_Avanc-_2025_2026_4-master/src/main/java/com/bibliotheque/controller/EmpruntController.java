package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Emprunt;
import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.model.Membre;
import main.java.com.bibliotheque.service.EmpruntService;
import main.java.com.bibliotheque.view.EmpruntDialog;
import main.java.com.bibliotheque.view.EmpruntView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class EmpruntController {

    private final EmpruntView view;
    private final EmpruntService service;

    public EmpruntController(EmpruntView view) {
        this.view = view;
        this.service = new EmpruntService();
        initController();
        loadEmprunts();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> showAddDialog());
        view.getReturnButton().addActionListener(e -> handleReturn());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getRefreshButton().addActionListener(e -> loadEmprunts());

        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearch();
            }
        });
    }

    private void showAddDialog() {
        try {
            EmpruntDialog dialog = new EmpruntDialog(
                    (Frame) SwingUtilities.getWindowAncestor(view)
            );

            List<Membre> membres = service.getAllMembres();
            List<Livre> livres = service.getAllLivres();

            dialog.setMembres(membres);
            dialog.setLivres(livres);

            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Membre membre = dialog.getSelectedMembre();
                Livre livre = dialog.getSelectedLivre();

                if (membre != null && livre != null) {
                    service.enregistrerEmprunt(membre.getIdMembre(), livre.getIdLivre());
                    loadEmprunts();
                    showSuccess("Emprunt enregistré avec succès !");
                }
            }

        } catch (Exception ex) {
            showError("Erreur lors de l'enregistrement de l'emprunt : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleReturn() {
        try {
            int idEmprunt = view.getSelectedEmpruntId();

            if (idEmprunt == -1) {
                showWarning("Veuillez sélectionner un emprunt dans le tableau.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirmer le retour de ce livre ?",
                    "Confirmation de retour",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.enregistrerRetour(idEmprunt);
                loadEmprunts();
                showSuccess("Retour enregistré avec succès !");
            }

        } catch (Exception ex) {
            showError("Erreur lors de l'enregistrement du retour : " + ex.getMessage());
        }
    }

    private void handleDelete() {
        showWarning("La suppression des emprunts n'est pas autorisée pour préserver l'historique.");
    }

    private void handleSearch() {
        String keyword = view.getSearchField().getText().trim();
        try {
            List<Emprunt> emprunts = service.searchEmprunts(keyword);
            displayEmprunts(emprunts);
        } catch (Exception ex) {
            showError("Erreur lors de la recherche : " + ex.getMessage());
        }
    }

    private void loadEmprunts() {
        try {
            List<Emprunt> emprunts = service.getAllEmprunts();
            displayEmprunts(emprunts);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des emprunts : " + ex.getMessage());
        }
    }

    private void displayEmprunts(List<Emprunt> emprunts) {
        view.clearTable();
        for (Emprunt emprunt : emprunts) {
            view.addEmpruntToTable(emprunt);
        }
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "Attention", JOptionPane.WARNING_MESSAGE);
    }
}