package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Livre;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class LivreView extends JPanel {

    private JTable livresTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, editButton, deleteButton, refreshButton;

    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color HEADER_COLOR = new Color(241, 245, 249);
    private static final Color ROW_ALT_COLOR = new Color(251, 253, 255);

    public LivreView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(SECONDARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(SECONDARY_COLOR);

        JLabel titleLabel = new JLabel("Gestion des Livres", createBookIcon(), JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setIconTextGap(10);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(SECONDARY_COLOR);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JLabel searchIcon = new JLabel(createSearchIcon());
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(searchPanel, BorderLayout.EAST);

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {"ID", "Titre", "Année", "Catégorie", "Auteur", "Maison d'édition"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        livresTable = new JTable(tableModel);

        livresTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        livresTable.setRowHeight(50);
        livresTable.setSelectionBackground(new Color(219, 234, 254));
        livresTable.setSelectionForeground(TEXT_COLOR);
        livresTable.setGridColor(new Color(226, 232, 240));
        livresTable.setShowVerticalLines(false);
        livresTable.setShowHorizontalLines(true);
        livresTable.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = livresTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(HEADER_COLOR);
        header.setForeground(new Color(100, 116, 139));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(226, 232, 240)));
        header.setPreferredSize(new Dimension(0, 45));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        livresTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        livresTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        livresTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        livresTable.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
        livresTable.getColumnModel().getColumn(4).setCellRenderer(leftRenderer);
        livresTable.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);

        livresTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        livresTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        livresTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        livresTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        livresTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        livresTable.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(livresTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(0, 400));

        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        addButton = createStyledButton("+ Ajouter", SUCCESS_COLOR, createAddIcon());
        editButton = createStyledButton("✏️ Modifier", PRIMARY_COLOR, createEditIcon());
        deleteButton = createStyledButton("🗑️ Supprimer", DANGER_COLOR, createDeleteIcon());
        refreshButton = createStyledButton("🔄 Actualiser", new Color(100, 116, 139), createRefreshIcon());

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(refreshButton);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Icon icon) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(150, 42));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIconTextGap(8);
        return button;
    }

    public void addLivreToTable(Livre livre) {
        String auteurNom = livre.getAuteur() != null && !livre.getAuteur().isEmpty() ?
                livre.getAuteur() : "-";
        String maisonNom = livre.getMaisonEdition() != null && !livre.getMaisonEdition().isEmpty() ?
                livre.getMaisonEdition() : "-";

        Object[] row = {
                livre.getIdLivre(),
                livre.getTitre(),
                livre.getAnneePublication(),
                livre.getCategorie(),
                auteurNom,
                maisonNom
        };
        tableModel.addRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
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
        String auteur = (String) tableModel.getValueAt(selectedRow, 4);
        String maisonEdition = (String) tableModel.getValueAt(selectedRow, 5);

        return new Livre(id, titre, annee, categorie, auteur, maisonEdition);
    }

    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getLivresTable() { return livresTable; }

    public JPanel getMainPanel() {
        return this;
    }

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
}