package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.service.LivreService;
import main.java.com.bibliotheque.view.LivreView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Contrôleur pour gérer les interactions de la vue Livre
 */
public class LivreController {

    private final LivreView view;
    private final LivreService service;
    private boolean isEditMode = false;

    public LivreController(LivreView view) {
        this.view = view;
        this.service = new LivreService();
        initController();
        loadLivres();
    }

    /**
     * Initialiser les écouteurs d'événements
     */
    private void initController() {
        // Bouton Ajouter
        view.getAddButton().addActionListener(e -> handleAdd());

        // Bouton Modifier
        view.getEditButton().addActionListener(e -> handleEdit());

        // Bouton Supprimer
        view.getDeleteButton().addActionListener(e -> handleDelete());

        // Bouton Actualiser
        view.getRefreshButton().addActionListener(e -> loadLivres());

        // Recherche en temps réel
        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearch();
            }
        });

        // Double-clic sur une ligne du tableau
        view.getLivresTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleTableDoubleClick();
                }
            }
        });
    }

    /**
     * Gérer l'ajout d'un livre
     */
    private void handleAdd() {
        try {
            // Si en mode édition, enregistrer les modifications
            if (isEditMode) {
                saveEdit();
                return;
            }

            // Sinon, ajouter un nouveau livre
            Livre livre = view.getLivreFromForm();

            if (livre == null) {
                return; // Erreur de format déjà gérée dans la vue
            }

            service.addLivre(livre);
            view.clearForm();
            loadLivres();
            showSuccess("Livre ajouté avec succès!");

        } catch (Exception ex) {
            showError("Erreur lors de l'ajout: " + ex.getMessage());
        }
    }

    /**
     * Gérer la modification d'un livre
     */
    private void handleEdit() {
        try {
            Livre selectedLivre = view.getSelectedLivre();

            if (selectedLivre == null) {
                showWarning("Veuillez sélectionner un livre à modifier.");
                return;
            }

            if (!isEditMode) {
                // Mode édition activé
                isEditMode = true;
                view.fillFormWithLivre(selectedLivre);
                view.getAddButton().setText("Enregistrer");
                view.getEditButton().setText("Annuler");
                view.getAddButton().setEnabled(true);
                view.getDeleteButton().setEnabled(false);
            } else {
                // Mode édition annulé
                isEditMode = false;
                view.clearForm();
                view.getAddButton().setText("Ajouter");
                view.getEditButton().setText("Modifier");
                view.getDeleteButton().setEnabled(true);
            }

        } catch (Exception ex) {
            showError("Erreur lors de la préparation de la modification: " + ex.getMessage());
        }
    }

    /**
     * Enregistrer les modifications
     */
    private void saveEdit() {
        try {
            Livre livre = view.getLivreFromForm();

            if (livre == null) {
                return;
            }

            Livre selectedLivre = view.getSelectedLivre();
            if (selectedLivre != null) {
                livre.setIdLivre(selectedLivre.getIdLivre());
            }

            service.updateLivre(livre);

            isEditMode = false;
            view.clearForm();
            view.getAddButton().setText("Ajouter");
            view.getEditButton().setText("Modifier");
            view.getDeleteButton().setEnabled(true);

            loadLivres();
            showSuccess("Livre modifié avec succès!");

        } catch (Exception ex) {
            showError("Erreur lors de la modification: " + ex.getMessage());
        }
    }

    /**
     * Gérer la suppression d'un livre
     */
    private void handleDelete() {
        try {
            Livre selectedLivre = view.getSelectedLivre();

            if (selectedLivre == null) {
                showWarning("Veuillez sélectionner un livre à supprimer.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Êtes-vous sûr de vouloir supprimer le livre:\n" + selectedLivre.getTitre() + "?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteLivre(selectedLivre.getIdLivre());
                view.clearForm();
                loadLivres();
                showSuccess("Livre supprimé avec succès!");
            }

        } catch (Exception ex) {
            showError("Erreur lors de la suppression: " + ex.getMessage());
        }
    }

    /**
     * Gérer la recherche
     */
    private void handleSearch() {
        String keyword = view.getSearchField().getText().trim();

        try {
            List<Livre> livres = service.searchLivres(keyword);
            displayLivres(livres);
        } catch (Exception ex) {
            showError("Erreur lors de la recherche: " + ex.getMessage());
        }
    }

    /**
     * Gérer le double-clic sur le tableau
     */
    private void handleTableDoubleClick() {
        Livre selectedLivre = view.getSelectedLivre();
        if (selectedLivre != null) {
            view.fillFormWithLivre(selectedLivre);
        }
    }

    /**
     * Charger tous les livres
     */
    private void loadLivres() {
        try {
            List<Livre> livres = service.getAllLivres();
            displayLivres(livres);

            // Réinitialiser le mode édition si actif
            if (isEditMode) {
                isEditMode = false;
                view.getAddButton().setText("Ajouter");
                view.getEditButton().setText("Modifier");
                view.getDeleteButton().setEnabled(true);
            }

        } catch (Exception ex) {
            showError("Erreur lors du chargement des livres: " + ex.getMessage());
        }
    }

    /**
     * Afficher la liste des livres dans le tableau
     */
    private void displayLivres(List<Livre> livres) {
        view.clearTable();
        for (Livre livre : livres) {
            view.addLivreToTable(livre);
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
