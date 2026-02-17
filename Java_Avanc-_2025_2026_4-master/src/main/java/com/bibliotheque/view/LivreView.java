package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Livre;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class LivreView extends JPanel {

    private JTable livresTable;
    private DefaultTableModel tableModel;
    private JTextField titreField, anneeField, categorieField, searchField;
    private JButton addButton, editButton, deleteButton, refreshButton;

    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);

    public LivreView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panneau supérieur avec titre et recherche
        add(createTopPanel(), BorderLayout.NORTH);

        // Tableau des livres
        add(createTablePanel(), BorderLayout.CENTER);

        // Formulaire d'ajout/modification
        add(createFormPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(SECONDARY_COLOR);

        // Titre
        JLabel titleLabel = new JLabel("Gestion des Livres", createBookIcon(), JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setIconTextGap(10);

        // Panneau de recherche
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
        // Modèle de table
        String[] columnNames = {"ID", "Titre", "Année", "Catégorie"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        livresTable = new JTable(tableModel);
        livresTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        livresTable.setRowHeight(40);
        livresTable.setGridColor(new Color(226, 232, 240));
        livresTable.setSelectionBackground(new Color(219, 234, 254));
        livresTable.setSelectionForeground(TEXT_COLOR);
        livresTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        livresTable.getTableHeader().setBackground(Color.WHITE);
        livresTable.getTableHeader().setForeground(TEXT_COLOR);
        livresTable.setShowVerticalLines(false);

        // Centrer les colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        livresTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        livresTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Largeur des colonnes
        livresTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        livresTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        livresTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        livresTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(livresTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Titre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        titreField = createTextField();
        formPanel.add(titreField, gbc);

        // Année
        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Année:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        anneeField = createTextField();
        formPanel.add(anneeField, gbc);

        // Catégorie
        gbc.gridx = 4;
        gbc.weightx = 0;
        formPanel.add(createLabel("Catégorie:"), gbc);

        gbc.gridx = 5;
        gbc.weightx = 0.7;
        categorieField = createTextField();
        formPanel.add(categorieField, gbc);

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

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
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

    // Méthodes publiques pour gérer les données
    public void addLivreToTable(Livre livre) {
        Object[] row = {
                livre.getIdLivre(),
                livre.getTitre(),
                livre.getAnneePublication(),  // ← CORRIGÉ
                livre.getCategorie()
        };
        tableModel.addRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void clearForm() {
        titreField.setText("");
        anneeField.setText("");
        categorieField.setText("");
    }

    public Livre getLivreFromForm() {
        try {
            return new Livre(
                    0, // ID sera généré automatiquement
                    titreField.getText().trim(),
                    Integer.parseInt(anneeField.getText().trim()),
                    categorieField.getText().trim()
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer une année valide.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Livre getSelectedLivre() {
        int selectedRow = livresTable.getSelectedRow();
        if (selectedRow == -1) {
            return null;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String titre = (String) tableModel.getValueAt(selectedRow, 1);
        int annee = (int) tableModel.getValueAt(selectedRow, 2);
        String categorie = (String) tableModel.getValueAt(selectedRow, 3);

        return new Livre(id, titre, annee, categorie);
    }

    public void fillFormWithLivre(Livre livre) {
        titreField.setText(livre.getTitre());
        anneeField.setText(String.valueOf(livre.getAnneePublication()));  // ← CORRIGÉ
        categorieField.setText(livre.getCategorie());
    }

    // Getters pour les boutons (pour ajouter des listeners dans le contrôleur)
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getLivresTable() { return livresTable; }

    // Icônes SVG
    private Icon createBookIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2.5f));

                g2.drawRect(x + 4, y + 2, 20, 28);
                g2.drawLine(x + 12, y + 2, x + 12, y + 30);
                g2.drawLine(x + 4, y + 10, x + 24, y + 10);

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

    // ✅ CORRECTION ICI
    public JPanel getMainPanel() {
        return this;  // ← Retourne le JPanel lui-même, PAS null
    }
}
