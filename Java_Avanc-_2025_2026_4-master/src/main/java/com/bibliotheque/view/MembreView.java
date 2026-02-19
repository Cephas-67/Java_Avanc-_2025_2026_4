package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Membre;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class MembreView extends JPanel {

    private JTable membresTable;
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

    public MembreView() {
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

        JLabel titleLabel = new JLabel("Gestion des Membres", createUserIcon(), JLabel.LEFT);
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
        String[] columnNames = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Résidence", "Pénalité"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        membresTable = new JTable(tableModel);

        membresTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        membresTable.setRowHeight(50);
        membresTable.setSelectionBackground(new Color(219, 234, 254));
        membresTable.setSelectionForeground(TEXT_COLOR);
        membresTable.setGridColor(new Color(226, 232, 240));
        membresTable.setShowVerticalLines(false);
        membresTable.setShowHorizontalLines(true);
        membresTable.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = membresTable.getTableHeader();
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

        membresTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        membresTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        membresTable.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        membresTable.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
        membresTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        membresTable.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);
        membresTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        membresTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        membresTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        membresTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        membresTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        membresTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        membresTable.getColumnModel().getColumn(5).setPreferredWidth(180);
        membresTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(membresTable);
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

    public void addMembreToTable(Membre membre) {
        Object[] row = {
                membre.getIdMembre(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getEmail(),
                membre.getTelephone() != null ? membre.getTelephone() : "-",
                membre.getLieuResidence() != null ? membre.getLieuResidence() : "-",
                String.format("%.2f €", membre.getPenalite())
        };
        tableModel.addRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
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

    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getMembresTable() { return membresTable; }

    public JPanel getMainPanel() {
        return this;
    }

    private Icon createUserIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawOval(x + 8, y + 2, 16, 16);
                g2.drawRect(x + 4, y + 20, 24, 12);
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