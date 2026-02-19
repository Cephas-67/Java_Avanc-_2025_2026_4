package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Membre;

import javax.swing.*;
import java.awt.*;

public class MembreDialog extends JDialog {
    private JTextField nomField, prenomField, emailField, telephoneField, lieuField, penaliteField;
    private JButton btnSave, btnCancel;
    private Membre membreToEdit;
    private boolean saved = false;

    public MembreDialog(Frame parent, boolean isEdit) {
        super(parent, isEdit ? "Modifier un Membre" : "Ajouter un Membre", true);
        setSize(600, 600);
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
        JLabel titleLabel = new JLabel(isEdit ? "✏️ Modifier le Membre" : "👥 Nouveau Membre");
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

        // Nom
        formPanel.add(createLabel("Nom :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        nomField = createTextField();
        formPanel.add(nomField, gbc);

        // Prénom
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        prenomField = createTextField();
        formPanel.add(prenomField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Email :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        emailField = createTextField();
        formPanel.add(emailField, gbc);

        // Téléphone (String) - ✅ CORRECTION ICI
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Téléphone :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        telephoneField = createTextField();
        // ❌ SUPPRIMER : telephoneField.setPlaceholder("Ex: 0612345678");
        // ✅ À LA PLACE : Utiliser un texte d'aide dans le label
        formPanel.add(telephoneField, gbc);

        // Lieu de résidence
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Lieu de résidence :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        lieuField = createTextField();
        formPanel.add(lieuField, gbc);

        // Pénalité
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Pénalité (€) :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        penaliteField = createTextField();
        penaliteField.setText("0.0");
        formPanel.add(penaliteField, gbc);

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

    public void setMembre(Membre membre) {
        this.membreToEdit = membre;
        nomField.setText(membre.getNom());
        prenomField.setText(membre.getPrenom());
        emailField.setText(membre.getEmail());
        telephoneField.setText(membre.getTelephone() != null ? membre.getTelephone() : "");
        lieuField.setText(membre.getLieuResidence());
        penaliteField.setText(String.valueOf(membre.getPenalite()));
    }

    private void saveAndClose() {
        try {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String email = emailField.getText().trim();
            String telephone = telephoneField.getText().trim();
            String lieu = lieuField.getText().trim();
            String penaliteStr = penaliteField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom et le prénom sont obligatoires",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double penalite = Double.parseDouble(penaliteStr);
            if (penalite < 0) {
                JOptionPane.showMessageDialog(this, "La pénalité ne peut pas être négative",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saved = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une pénalité valide",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Membre getMembre() {
        if (!saved) return null;

        double penalite = Double.parseDouble(penaliteField.getText().trim());

        if (membreToEdit != null) {
            membreToEdit.setNom(nomField.getText().trim());
            membreToEdit.setPrenom(prenomField.getText().trim());
            membreToEdit.setEmail(emailField.getText().trim());
            membreToEdit.setTelephone(telephoneField.getText().trim());
            membreToEdit.setLieuResidence(lieuField.getText().trim());
            membreToEdit.setPenalite(penalite);
            return membreToEdit;
        }

        return new Membre(0,
                nomField.getText().trim(),
                prenomField.getText().trim(),
                emailField.getText().trim(),
                telephoneField.getText().trim(),
                lieuField.getText().trim(),
                penalite);
    }

    public boolean isSaved() {
        return saved;
    }
}