package main.java.com.bibliotheque.view;

import com.bibliotheque.model.Membre;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.*;

public class MembreView extends JPanel {

    private JTable membresTable;
    private DefaultTableModel tableModel;
    private JTextField nomField, prenomField, emailField, telephoneField, lieuField, penaliteField, searchField;
    private JButton addButton, editButton, deleteButton, refreshButton;

    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);

    public MembreView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFormPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(SECONDARY_COLOR);

        JLabel titleLabel = new JLabel("Gestion des Membres", createUserIcon(), JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setIconTextGap(10);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(SECONDARY_COLOR);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel searchIcon = new JLabel(createSearchIcon());

        searchPanel.add(searchIcon);
        searchPanel.add(searchField);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(searchPanel, BorderLayout.EAST);

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Résidence", "Pénalité"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        membresTable = new JTable(tableModel);
        membresTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        membresTable.setRowHeight(40);
        membresTable.setGridColor(new Color(226, 232, 240));
        membresTable.setSelectionBackground(new Color(219, 234, 254));
        membresTable.setSelectionForeground(TEXT_COLOR);
        membresTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        membresTable.getTableHeader().setBackground(Color.WHITE);
        membresTable.getTableHeader().setForeground(TEXT_COLOR);
        membresTable.setShowVerticalLines(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        membresTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        membresTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        membresTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        membresTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        membresTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        membresTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        membresTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        membresTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        membresTable.getColumnModel().getColumn(6).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(membresTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ligne 1: Nom et Prénom
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Nom:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        nomField = createTextField();
        formPanel.add(nomField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Prénom:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        prenomField = createTextField();
        formPanel.add(prenomField, gbc);

        // Ligne 2: Email et Téléphone
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        emailField = createTextField();
        formPanel.add(emailField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Téléphone:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        telephoneField = createTextField();
        formPanel.add(telephoneField, gbc);

        // Ligne 3: Résidence et Pénalité
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Résidence:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        lieuField = createTextField();
        formPanel.add(lieuField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Pénalité (€):"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        penaliteField = createTextField();
        penaliteField.setText("0.0");
        formPanel.add(penaliteField, gbc);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        addButton = createStyledButton("Ajouter", SUCCESS_COLOR, createAddIcon());
        editButton = createStyledButton("Modifier", PRIMARY_COLOR, createEditIcon());
        deleteButton = createStyledButton("Supprimer", DANGER_COLOR, createDeleteIcon());
        refreshButton = createStyledButton("Actualiser", new Color(100, 116, 139), createRefreshIcon());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Icon icon) {
        JButton button = new JButton(text, icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setPreferredSize(new Dimension(130, 38));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIconTextGap(8);

        return button;
    }

    // Méthodes publiques
    public void addMembreToTable(Membre membre) {
        Object[] row = {
                membre.getIdMembre(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getEmail(),
                membre.getTelephone(),
                membre.getLieuResidence(),
                String.format("%.2f €", membre.getPenalite())
        };
        tableModel.addRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void clearForm() {
        nomField.setText("");
        prenomField.setText("");
        emailField.setText("");
        telephoneField.setText("");
        lieuField.setText("");
        penaliteField.setText("0.0");
    }

    public Membre getMembreFromForm() {
        try {
            return new Membre(
                    0,
                    nomField.getText().trim(),
                    prenomField.getText().trim(),
                    emailField.getText().trim(),
                    telephoneField.getText().trim(),
                    lieuField.getText().trim(),
                    Double.parseDouble(penaliteField.getText().trim())
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer une pénalité valide.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Membre getSelectedMembre() {
        int selectedRow = membresTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nom = (String) tableModel.getValueAt(selectedRow, 1);
        String prenom = (String) tableModel.getValueAt(selectedRow, 2);
        String email = (String) tableModel.getValueAt(selectedRow, 3);
        String telephone = (String) tableModel.getValueAt(selectedRow, 4);
        String lieu = (String) tableModel.getValueAt(selectedRow, 5);
        String penaliteStr = (String) tableModel.getValueAt(selectedRow, 6);
        double penalite = Double.parseDouble(penaliteStr.replace(" €", "").replace(",", "."));

        return new Membre(id, nom, prenom, email, telephone, lieu, penalite);
    }

    public void fillFormWithMembre(Membre membre) {
        nomField.setText(membre.getNom());
        prenomField.setText(membre.getPrenom());
        emailField.setText(membre.getEmail());
        telephoneField.setText(membre.getTelephone());
        lieuField.setText(membre.getLieuResidence());
        penaliteField.setText(String.valueOf(membre.getPenalite()));
    }

    // Getters
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getMembresTable() { return membresTable; }

    // Icônes SVG
    private Icon createUserIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2.5f));

                g2.drawOval(x + 8, y + 2, 16, 16);
                Path2D path = new Path2D.Double();
                path.moveTo(x + 2, y + 30);
                path.curveTo(x + 2, y + 24, x + 8, y + 20, x + 16, y + 20);
                path.curveTo(x + 24, y + 20, x + 30, y + 24, x + 30, y + 30);
                g2.draw(path);

                g2.dispose();
            }
            public int getIconWidth() { return 32; }
            public int getIconHeight() { return 32; }
        };
    }

    private Icon createSearchIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(100, 116, 139));
                g2.setStroke(new BasicStroke(2));

                g2.drawOval(x + 2, y + 2, 14, 14);
                g2.drawLine(x + 14, y + 14, x + 20, y + 20);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createAddIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));

                g2.drawLine(x + 8, y + 4, x + 8, y + 12);
                g2.drawLine(x + 4, y + 8, x + 12, y + 8);

                g2.dispose();
            }
            public int getIconWidth() { return 16; }
            public int getIconHeight() { return 16; }
        };
    }

    private Icon createEditIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));

                g2.drawLine(x + 2, y + 12, x + 4, y + 14);
                g2.drawLine(x + 4, y + 14, x + 14, y + 4);
                g2.drawLine(x + 14, y + 4, x + 12, y + 2);
                g2.drawLine(x + 12, y + 2, x + 2, y + 12);

                g2.dispose();
            }
            public int getIconWidth() { return 16; }
            public int getIconHeight() { return 16; }
        };
    }

    private Icon createDeleteIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));

                g2.drawLine(x + 4, y + 2, x + 12, y + 2);
                g2.drawLine(x + 3, y + 4, x + 13, y + 4);
                g2.drawRect(x + 4, y + 5, 8, 9);
                g2.drawLine(x + 6, y + 7, x + 6, y + 11);
                g2.drawLine(x + 10, y + 7, x + 10, y + 11);

                g2.dispose();
            }
            public int getIconWidth() { return 16; }
            public int getIconHeight() { return 16; }
        };
    }

    private Icon createRefreshIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));

                g2.drawArc(x + 2, y + 2, 12, 12, 45, 270);
                g2.drawLine(x + 12, y + 3, x + 14, y + 1);
                g2.drawLine(x + 12, y + 3, x + 14, y + 5);

                g2.dispose();
            }
            public int getIconWidth() { return 16; }
            public int getIconHeight() { return 16; }
        };
    }
}
