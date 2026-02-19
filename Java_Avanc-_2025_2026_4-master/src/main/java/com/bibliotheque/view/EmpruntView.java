package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Emprunt;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpruntView extends JPanel {

    private JTable empruntsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton addButton, returnButton, deleteButton, refreshButton;

    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color DANGER_COLOR = new Color(239, 68, 68);
    private static final Color WARNING_COLOR = new Color(245, 158, 11);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color HEADER_COLOR = new Color(241, 245, 249);
    private static final Color ROW_ALT_COLOR = new Color(251, 253, 255);

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmpruntView() {
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

        JLabel titleLabel = new JLabel("Gestion des Emprunts", createBorrowIcon(), JLabel.LEFT);
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
        String[] columnNames = {"ID", "Membre", "Livre", "Date Emprunt", "Retour Prévu", "Retour Effectif", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        empruntsTable = new JTable(tableModel);

        empruntsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        empruntsTable.setRowHeight(50);
        empruntsTable.setSelectionBackground(new Color(219, 234, 254));
        empruntsTable.setSelectionForeground(TEXT_COLOR);
        empruntsTable.setGridColor(new Color(226, 232, 240));
        empruntsTable.setShowVerticalLines(false);
        empruntsTable.setShowHorizontalLines(true);
        empruntsTable.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = empruntsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(HEADER_COLOR);
        header.setForeground(new Color(100, 116, 139));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(226, 232, 240)));
        header.setPreferredSize(new Dimension(0, 45));

        // Renderer pour le statut
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

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        empruntsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        empruntsTable.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        empruntsTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        empruntsTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        empruntsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        empruntsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        empruntsTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        empruntsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        empruntsTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(empruntsTable);
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

        addButton = createStyledButton("+ Nouvel Emprunt", SUCCESS_COLOR, createAddIcon());
        returnButton = createStyledButton("⬅️ Retourner", WARNING_COLOR, createReturnIcon());
        deleteButton = createStyledButton("🗑️ Supprimer", DANGER_COLOR, createDeleteIcon());
        refreshButton = createStyledButton("🔄 Actualiser", new Color(100, 116, 139), createRefreshIcon());

        panel.add(addButton);
        panel.add(returnButton);
        panel.add(deleteButton);
        panel.add(refreshButton);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Icon icon) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(160, 42));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIconTextGap(8);
        return button;
    }

    public void addEmpruntToTable(Emprunt emprunt) {
        String membreNom = emprunt.getMembre() != null ?
                emprunt.getMembre().getNom() + " " + emprunt.getMembre().getPrenom() : "-";
        String livreTitre = emprunt.getLivre() != null ?
                emprunt.getLivre().getTitre() : "-";
        String dateEmprunt = emprunt.getDateEmprunt() != null ?
                emprunt.getDateEmprunt().format(dateFormatter) : "-";
        String dateRetourPrevue = emprunt.getDateRetourPrevue() != null ?
                emprunt.getDateRetourPrevue().format(dateFormatter) : "-";
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
        } else if (emprunt.getDateRetourPrevue() != null &&
                emprunt.getDateRetourPrevue().isBefore(LocalDate.now())) {
            return "En retard";
        } else {
            return "En cours";
        }
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public int getSelectedEmpruntId() {
        int selectedRow = empruntsTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    public JButton getAddButton() { return addButton; }
    public JButton getReturnButton() { return returnButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getRefreshButton() { return refreshButton; }
    public JTextField getSearchField() { return searchField; }
    public JTable getEmpruntsTable() { return empruntsTable; }

    public JPanel getMainPanel() {
        return this;
    }

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