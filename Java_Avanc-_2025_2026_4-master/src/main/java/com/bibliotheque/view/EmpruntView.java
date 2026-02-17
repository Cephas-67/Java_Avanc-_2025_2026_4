package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Emprunt;
import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.model.Membre;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmpruntView extends JPanel {

    private JTable empruntsTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> membreCombo, livreCombo;
    private JTextField dateEmpruntField, dateRetourPrevueField, dateRetourEffectiveField, searchField;
    private JButton addButton, returnButton, deleteButton, refreshButton;

    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color WARNING_COLOR = new Color(245, 158, 11);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmpruntView() {
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

        JLabel titleLabel = new JLabel("Gestion des Emprunts", createBorrowIcon(), JLabel.LEFT);
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
        String[] columnNames = {"ID", "Membre", "Livre", "Date Emprunt", "Retour Prévu", "Retour Effectif", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        empruntsTable = new JTable(tableModel);
        empruntsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        empruntsTable.setRowHeight(40);
        empruntsTable.setGridColor(new Color(226, 232, 240));
        empruntsTable.setSelectionBackground(new Color(219, 234, 254));
        empruntsTable.setSelectionForeground(TEXT_COLOR);
        empruntsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        empruntsTable.getTableHeader().setBackground(Color.WHITE);
        empruntsTable.getTableHeader().setForeground(TEXT_COLOR);
        empruntsTable.setShowVerticalLines(false);

        // Renderer personnalisé pour le statut
        empruntsTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    String status = value.toString();
                    if (status.equals("En cours")) {
                        c.setForeground(PRIMARY_COLOR);
                    } else if (status.equals("Retourné")) {
                        c.setForeground(SUCCESS_COLOR);
                    } else if (status.equals("En retard")) {
                        c.setForeground(DANGER_COLOR);
                    }
                }
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 13));

                return c;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        empruntsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        empruntsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        empruntsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        empruntsTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        empruntsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(empruntsTable);
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

        // Ligne 1: Membre et Livre
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createLabel("Membre:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        membreCombo = createComboBox();
        formPanel.add(membreCombo, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Livre:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        livreCombo = createComboBox();
        formPanel.add(livreCombo, gbc);

        // Ligne 2: Dates
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(createLabel("Date Emprunt:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        dateEmpruntField = createTextField();
        dateEmpruntField.setText(LocalDate.now().format(dateFormatter));
        formPanel.add(dateEmpruntField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Retour Prévu:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.7;
        dateRetourPrevueField = createTextField();
        dateRetourPrevueField.setText(LocalDate.now().plusDays(14).format(dateFormatter));
        formPanel.add(dateRetourPrevueField, gbc);

        // Ligne 3: Date de retour effectif
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(createLabel("Retour Effectif:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        dateRetourEffectiveField = createTextField();
        dateRetourEffectiveField.setText("");
        dateRetourEffectiveField.setEnabled(false);
        formPanel.add(dateRetourEffectiveField, gbc);

        // Note d'aide
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        JLabel noteLabel = createLabel("Sélectionnez un emprunt et cliquez sur 'Retourner' pour enregistrer le retour");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        noteLabel.setForeground(new Color(100, 116, 139));
        formPanel.add(noteLabel, gbc);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        addButton = createStyledButton("Nouvel Emprunt", SUCCESS_COLOR, createAddIcon());
        returnButton = createStyledButton("Retourner", WARNING_COLOR, createReturnIcon());
        deleteButton = createStyledButton("Supprimer", DANGER_COLOR, createDeleteIcon());
        refreshButton = createStyledButton("Actualiser", new Color(100, 116, 139), createRefreshIcon());

        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
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

    private JComboBox<String> createComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setPreferredSize(new Dimension(200, 35));
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        return combo;
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

        button.setPreferredSize(new Dimension(150, 38));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIconTextGap(8);

        return button;
    }

    // ==================== MÉTHODES PUBLIQUES POUR LE CONTRÔLEUR ====================

    public void addEmpruntToTable(Emprunt emprunt) {
        String membreNom = emprunt.getMembre().getNom() + " " + emprunt.getMembre().getPrenom();
        String livreTitre = emprunt.getLivre().getTitre();
        String dateEmprunt = emprunt.getDateEmprunt().format(dateFormatter);
        String dateRetourPrevue = emprunt.getDateRetourPrevue().format(dateFormatter);
        String dateRetourEffective = emprunt.getDateRetourEffective() != null ?
                emprunt.getDateRetourEffective().format(dateFormatter) : "-";

        String statut = getStatut(emprunt);

        Object[] row = {
                emprunt.getIdEmprunt(),
                membreNom,
                livreTitre,
                dateEmprunt,
                dateRetourPrevue,
                dateRetourEffective,
                statut
        };
        tableModel.addRow(row);
    }

    private String getStatut(Emprunt emprunt) {
        if (emprunt.getDateRetourEffective() != null) {
            return "Retourné";
        } else if (emprunt.getDateRetourPrevue().isBefore(LocalDate.now())) {
            return "En retard";
        } else {
            return "En cours";
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void clearForm() {
        membreCombo.setSelectedIndex(-1);
        livreCombo.setSelectedIndex(-1);
        dateEmpruntField.setText(LocalDate.now().format(dateFormatter));
        dateRetourPrevueField.setText(LocalDate.now().plusDays(14).format(dateFormatter));
        dateRetourEffectiveField.setText("");
    }

    public int getSelectedEmpruntId() {
        int selectedRow = empruntsTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    public void updateMembreCombo(List<Membre> membres) {
        membreCombo.removeAllItems();
        membreCombo.addItem("-- Sélectionner un membre --");
        for (Membre m : membres) {
            membreCombo.addItem(m.getIdMembre() + " - " + m.getNom() + " " + m.getPrenom());
        }
    }

    public void updateLivreCombo(List<Livre> livres) {
        livreCombo.removeAllItems();
        livreCombo.addItem("-- Sélectionner un livre --");
        for (Livre l : livres) {
            livreCombo.addItem(l.getIdLivre() + " - " + l.getTitre());
        }
    }

    public int getSelectedMembreId() {
        String selected = (String) membreCombo.getSelectedItem();
        if (selected == null || selected.startsWith("--")) return -1;
        try {
            return Integer.parseInt(selected.split(" - ")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getSelectedLivreId() {
        String selected = (String) livreCombo.getSelectedItem();
        if (selected == null || selected.startsWith("--")) return -1;
        try {
            return Integer.parseInt(selected.split(" - ")[0]);
        } catch (Exception e) {
            return -1;
        }
    }

    public LocalDate getDateEmprunt() {
        try {
            return LocalDate.parse(dateEmpruntField.getText(), dateFormatter);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public LocalDate getDateRetourPrevue() {
        try {
            return LocalDate.parse(dateRetourPrevueField.getText(), dateFormatter);
        } catch (Exception e) {
            return LocalDate.now().plusDays(14);
        }
    }

    // Getters pour les boutons
    public JButton getAddButton() { return addButton; }
    public JButton getReturnButton() { return returnButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getEmpruntsTable() { return empruntsTable; }

    // Méthode pour obtenir le panel principal (utilisé par MainFrame)
    public JPanel getMainPanel() {
        return this;
    }

    // ==================== ICÔNES SVG ====================

    private Icon createBorrowIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2.5f));

                g2.drawArc(x + 2, y + 2, 28, 28, 45, 270);
                g2.drawLine(x + 26, y + 6, x + 30, y + 2);
                g2.drawLine(x + 26, y + 6, x + 30, y + 10);

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

    private Icon createReturnIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));

                g2.drawLine(x + 12, y + 8, x + 4, y + 8);
                g2.drawLine(x + 4, y + 8, x + 7, y + 5);
                g2.drawLine(x + 4, y + 8, x + 7, y + 11);

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