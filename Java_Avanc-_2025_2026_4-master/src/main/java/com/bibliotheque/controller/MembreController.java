package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Membre;
import main.java.com.bibliotheque.service.MembreService;
import main.java.com.bibliotheque.view.MembreView;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Contrôleur pour gérer les interactions de la vue Membre
 */
public class MembreController {

    private final MembreView view;
    private final MembreService service;
    private boolean isEditMode = false;

    public MembreController(MembreView view) {
        this.view = view;
        this.service = new MembreService();
        initController();
        loadMembres();
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
        view.getRefreshButton().addActionListener(e -> loadMembres());

        // Recherche en temps réel
        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearch();
            }
        });

        // Double-clic sur une ligne du tableau pour modifier
        view.getMembresTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleTableDoubleClick();
                }
            }
        });
    }

    /**
     * Gérer l'ajout d'un membre
     */
    private void handleAdd() {
        try {
            // Si en mode édition, enregistrer les modifications
            if (isEditMode) {
                saveEdit();
                return;
            }

            // Sinon, ajouter un nouveau membre
            Membre membre = view.getMembreFromForm();

            if (membre == null) {
                return; // Erreur de format déjà gérée dans la vue
            }

            service.addMembre(membre);
            view.clearForm();
            loadMembres();
            showSuccess("Membre ajouté avec succès!");

        } catch (Exception ex) {
            showError("Erreur lors de l'ajout: " + ex.getMessage());
        }
    }

    /**
     * Gérer la modification d'un membre
     */
    private void handleEdit() {
        try {
            Membre selectedMembre = view.getSelectedMembre();

            if (selectedMembre == null) {
                showWarning("Veuillez sélectionner un membre à modifier.");
                return;
            }

            if (!isEditMode) {
                // Mode édition activé
                isEditMode = true;
                view.fillFormWithMembre(selectedMembre);
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
            Membre membre = view.getMembreFromForm();

            if (membre == null) {
                return;
            }

            Membre selectedMembre = view.getSelectedMembre();
            if (selectedMembre != null) {
                membre.setIdMembre(selectedMembre.getIdMembre());
            }

            service.updateMembre(membre);

            isEditMode = false;
            view.clearForm();
            view.getAddButton().setText("Ajouter");
            view.getEditButton().setText("Modifier");
            view.getDeleteButton().setEnabled(true);

            loadMembres();
            showSuccess("Membre modifié avec succès!");

        } catch (Exception ex) {
            showError("Erreur lors de la modification: " + ex.getMessage());
        }
    }

    /**
     * Gérer la suppression d'un membre
     */
    private void handleDelete() {
        try {
            Membre selectedMembre = view.getSelectedMembre();

            if (selectedMembre == null) {
                showWarning("Veuillez sélectionner un membre à supprimer.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Êtes-vous sûr de vouloir supprimer le membre:\n" +
                            selectedMembre.getNom() + " " + selectedMembre.getPrenom() + "?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteMembre(selectedMembre.getIdMembre());
                view.clearForm();
                loadMembres();
                showSuccess("Membre supprimé avec succès!");
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
            List<Membre> membres = service.searchMembres(keyword);
            displayMembres(membres);
        } catch (Exception ex) {
            showError("Erreur lors de la recherche: " + ex.getMessage());
        }
    }

    /**
     * Gérer le double-clic sur le tableau
     */
    private void handleTableDoubleClick() {
        Membre selectedMembre = view.getSelectedMembre();
        if (selectedMembre != null) {
            view.fillFormWithMembre(selectedMembre);
        }
    }

    /**
     * Charger tous les membres
     */
    private void loadMembres() {
        try {
            List<Membre> membres = service.getAllMembres();
            displayMembres(membres);

            // Réinitialiser le mode édition si actif
            if (isEditMode) {
                isEditMode = false;
                view.getAddButton().setText("Ajouter");
                view.getEditButton().setText("Modifier");
                view.getDeleteButton().setEnabled(true);
            }

        } catch (Exception ex) {
            showError("Erreur lors du chargement des membres: " + ex.getMessage());
        }
    }

    /**
     * Afficher la liste des membres dans le tableau
     */
    private void displayMembres(List<Membre> membres) {
        view.clearTable();
        for (Membre membre : membres) {
            view.addMembreToTable(membre);
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
