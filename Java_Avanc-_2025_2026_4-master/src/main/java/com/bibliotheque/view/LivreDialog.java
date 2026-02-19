package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Livre;

import javax.swing.*;
import java.awt.*;

public class LivreDialog extends JDialog {
    private JTextField titreField, anneeField, categorieField, auteurField, maisonEditionField;
    private JButton btnSave, btnCancel;
    private Livre livreToEdit;
    private boolean saved = false;

    public LivreDialog(Frame parent, boolean isEdit) {
        super(parent, isEdit ? "Modifier un Livre" : "Ajouter un Livre", true);
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents(isEdit);
    }

    private void initComponents(boolean isEdit) {
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(248, 250, 252));

        // Titre du dialogue
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(37, 99, 235));
        JLabel titleLabel = new JLabel(isEdit ? "✏️ Modifier le Livre" : "📚 Nouveau Livre");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(248, 250, 252));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Titre
        formPanel.add(createLabel("Titre :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        titreField = createTextField();
        formPanel.add(titreField, gbc);

        // Année
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Année de publication :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        anneeField = createTextField();
        formPanel.add(anneeField, gbc);

        // Catégorie
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Catégorie :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        categorieField = createTextField();
        formPanel.add(categorieField, gbc);

        // Auteur - CHAMP TEXTE (saisie manuelle)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Auteur :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        auteurField = createTextField();
        formPanel.add(auteurField, gbc);

        // Maison d'édition - CHAMP TEXTE (saisie manuelle)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Maison d'édition :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        maisonEditionField = createTextField();
        formPanel.add(maisonEditionField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(248, 250, 252));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)));

        btnSave = createButton("💾 Enregistrer", new Color(16, 185, 129));
        btnCancel = createButton("❌ Annuler", new Color(100, 116, 139));

        btnSave.addActionListener(e -> saveAndClose());
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(51, 65, 85));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void setLivre(Livre livre) {
        this.livreToEdit = livre;
        titreField.setText(livre.getTitre());
        anneeField.setText(String.valueOf(livre.getAnneePublication()));
        categorieField.setText(livre.getCategorie());
        auteurField.setText(livre.getAuteur() != null ? livre.getAuteur() : "");
        maisonEditionField.setText(livre.getMaisonEdition() != null ? livre.getMaisonEdition() : "");
    }

    private void saveAndClose() {
        try {
            String titre = titreField.getText().trim();
            String anneeStr = anneeField.getText().trim();
            String categorie = categorieField.getText().trim();

            if (titre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le titre est obligatoire",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int annee = Integer.parseInt(anneeStr);
            if (annee <= 0) {
                JOptionPane.showMessageDialog(this, "L'année doit être positive",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saved = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une année valide",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Livre getLivre() {
        if (!saved) return null;

        String auteur = auteurField.getText().trim();
        String maisonEdition = maisonEditionField.getText().trim();

        if (livreToEdit != null) {
            livreToEdit.setTitre(titreField.getText().trim());
            livreToEdit.setAnneePublication(Integer.parseInt(anneeField.getText().trim()));
            livreToEdit.setCategorie(categorieField.getText().trim());
            livreToEdit.setAuteur(auteur);
            livreToEdit.setMaisonEdition(maisonEdition);
            return livreToEdit;
        }

        return new Livre(0,
                titreField.getText().trim(),
                Integer.parseInt(anneeField.getText().trim()),
                categorieField.getText().trim(),
                auteur,
                maisonEdition);
    }

    public boolean isSaved() {
        return saved;
    }
}