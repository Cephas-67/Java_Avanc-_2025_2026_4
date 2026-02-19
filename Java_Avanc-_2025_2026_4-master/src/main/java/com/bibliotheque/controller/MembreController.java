package main.java.com.bibliotheque.controller;

import main.java.com.bibliotheque.model.Membre;
import main.java.com.bibliotheque.service.MembreService;
import main.java.com.bibliotheque.view.MembreDialog;
import main.java.com.bibliotheque.view.MembreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import main.java.com.bibliotheque.config.DatabaseConfig;

public class MembreController {

    private final MembreView view;
    private final MembreService service;
    
     int toInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
    
    double toDouble(String s, double def) {
        try { return Double.parseDouble(s); } catch (Exception e) { return def; }
    }
    

    public MembreController(MembreView view) {
        this.view = view;
        this.service = new MembreService();
        initController();
        loadMembres();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> showAddDialog());
        view.getEditButton().addActionListener(e -> handleEdit());
        view.getDeleteButton().addActionListener(e -> handleDelete());
        view.getRefreshButton().addActionListener(e -> loadMembres());

        view.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                handleSearch();
            }
        });

        view.getMembresTable().addMouseListener(new MouseAdapter() {
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
            MembreDialog dialog = new MembreDialog(
                    (Frame) SwingUtilities.getWindowAncestor(view),
                    false
            );

            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Membre membre = dialog.getMembre();
                if (membre != null) {
                    service.addMembre(membre);
                    loadMembres();
                     DatabaseConfig.AddMember(membre.getIdMembre(), membre.getNom(), membre.getPrenom(),
                    membre.getEmail(),membre.getTelephone(),
                    membre.getLieuResidence(), toDouble(String.format("%.2f €",
                            membre.getPenalite()),0));
                    showSuccess("Membre ajouté avec succès !");
                }
            }

        } catch (Exception ex) {
            showError("Erreur lors de l'ajout : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleEdit() {
        try {
            Membre selectedMembre = view.getSelectedMembre();

            if (selectedMembre == null) {
                showWarning("Veuillez sélectionner un membre à modifier.");
                return;
            }

            MembreDialog dialog = new MembreDialog(
                    (Frame) SwingUtilities.getWindowAncestor(view),
                    true
            );

            dialog.setMembre(selectedMembre);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                Membre membre = dialog.getMembre();
                if (membre != null) {
                    service.updateMembre(membre);
                    loadMembres();
                    DatabaseConfig.updateMember(membre.getIdMembre(),membre.getNom(),
                        membre.getPrenom(),membre.getEmail(),
                        membre.getTelephone(), membre.getLieuResidence(),
                        toDouble(String.format("%.2f €", membre.getPenalite()),0));
                    showSuccess("Membre modifié avec succès !");
                }
            }

        } catch (Exception ex) {
            showError("Erreur lors de la modification : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleDelete() {
        try {
            Membre selectedMembre = view.getSelectedMembre();

            if (selectedMembre == null) {
                showWarning("Veuillez sélectionner un membre à supprimer.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Êtes-vous sûr de vouloir supprimer le membre :\n" + selectedMembre.getNom() + " " + selectedMembre.getPrenom() + "?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteMembre(selectedMembre.getIdMembre());
                loadMembres();
                DatabaseConfig.deleteMember(selectedMembre.getIdMembre());
                showSuccess("Membre supprimé avec succès !");
            }

        } catch (Exception ex) {
            showError("Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    private void handleSearch() {
        String keyword = view.getSearchField().getText().trim();
        try {
            List<Membre> membres = service.searchMembres(keyword);
            displayMembres(membres);
        } catch (Exception ex) {
            showError("Erreur lors de la recherche : " + ex.getMessage());
        }
    }

    private void loadMembres() {
        try {
            List<Membre> membres = service.getAllMembres();
            displayMembres(membres);
        } catch (Exception ex) {
            showError("Erreur lors du chargement des membres : " + ex.getMessage());
        }
    }

    private void displayMembres(List<Membre> membres) {
        view.clearTable();
        for (Membre membre : membres) {
            view.addMembreToTable(membre);
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