package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.service.LivreService;
import main.java.com.bibliotheque.view.LivreDialog;
import main.java.com.bibliotheque.view.LivreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import main.java.com.bibliotheque.config.DatabaseConfig;

public class LivreController {

    private final LivreView view;
    private final LivreService service;

    public LivreController(LivreView view) {
        this.view = view;
        this.service = new LivreService();
        initController();
        loadLivres();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> showAddDialog());
        view.getEditButton().addActionListener(e -> handleEdit());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getRefreshButton().addActionListener(e -> loadLivres());

        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearch();
            }
        });

        view.getLivresTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleEdit();
                }
            }
        });
    }

    private void showAddDialog() {
        try {
            LivreDialog dialog = new LivreDialog(
                    (Frame) SwingUtilities.getWindowAncestor(view),
                    false
            );

            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Livre livre = dialog.getLivre();
                if (livre != null) {
                    service.addLivre(livre);
                    loadLivres();
                    DatabaseConfig.addBook(livre.getIdLivre(), livre.getTitre(), livre.getAnnee(),
                            livre.getCategorie(), livre.getAuteur(), livre.getMaisonEdition());
                    showSuccess("Livre ajouté avec succès !");
                }
            }

        } catch (Exception ex) {
            showError("Erreur lors de l'ajout : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleEdit() {
        try {
            Livre selectedLivre = view.getSelectedLivre();

            if (selectedLivre == null) {
                showWarning("Veuillez sélectionner un livre à modifier.");
                return;
            }

            LivreDialog dialog = new LivreDialog(
                    (Frame) SwingUtilities.getWindowAncestor(view),
                    true
            );

            dialog.setLivre(selectedLivre);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Livre livre = dialog.getLivre();
                if (livre != null) {
                    service.updateLivre(livre);
                    loadLivres();
                    DatabaseConfig.updateBook(livre.getIdLivre(), livre.getTitre(), livre.getAnnee(),
                            livre.getCategorie(), livre.getAuteur(), livre.getMaisonEdition());
                    showSuccess("Livre modifié avec succès !");
                }
            }

        } catch (Exception ex) {
            showError("Erreur lors de la modification : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleDelete() {
        try {
            Livre selectedLivre = view.getSelectedLivre();

            if (selectedLivre == null) {
                showWarning("Veuillez sélectionner un livre à supprimer.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Êtes-vous sûr de vouloir supprimer le livre :\n" + selectedLivre.getTitre() + "?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteLivre(selectedLivre.getIdLivre());
                loadLivres();
                DatabaseConfig.deleteBook(selectedLivre.getIdLivre());
                showSuccess("Livre supprimé avec succès !");
            }

        } catch (Exception ex) {
            showError("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    private void handleSearch() {
        String keyword = view.getSearchField().getText().trim();
        try {
            List<Livre> livres = service.searchLivres(keyword);
            displayLivres(livres);
        } catch (Exception ex) {
            showError("Erreur lors de la recherche : " + ex.getMessage());
        }
    }

    private void loadLivres() {
        try {
            List<Livre> livres = service.getAllLivres();
            displayLivres(livres);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des livres : " + ex.getMessage());
        }
    }

    private void displayLivres(List<Livre> livres) {
        view.clearTable();
        for (Livre livre : livres) {
            view.addLivreToTable(livre);
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