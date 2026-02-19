package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.model.Membre;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDialog extends JDialog {
    private JTextField membreField, livreField;
    private JTextField dateEmpruntField, dateRetourPrevueField;
    private JButton btnSave, btnCancel;

    // Listes pour l'auto-complétion
    private JPopupMenu membrePopup, livrePopup;
    private JList<String> membreList, livreList;
    private JScrollPane membreScrollPane, livreScrollPane;
    private List<Membre> allMembres = new ArrayList<>();
    private List<Livre> allLivres = new ArrayList<>();

    private Membre selectedMembre = null;
    private Livre selectedLivre = null;
    private boolean saved = false;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EmpruntDialog(Frame parent) {
        super(parent, "📚 Nouvel Emprunt", true);
        setSize(650, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(248, 250, 252));

        // Titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(37, 99, 235));
        JLabel titleLabel = new JLabel("📚 Nouvel Emprunt");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(248, 250, 252));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Membre (avec auto-complétion)
        formPanel.add(createLabel("Membre :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        membreField = createAutoCompleteField("membre");
        formPanel.add(membreField, gbc);

        // Livre (avec auto-complétion)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Livre :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        livreField = createAutoCompleteField("livre");
        formPanel.add(livreField, gbc);

        // Date d'emprunt (MODIFIABLE)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Date d'emprunt :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        dateEmpruntField = createDateField(LocalDate.now());
        formPanel.add(dateEmpruntField, gbc);

        // Date de retour prévue (MODIFIABLE)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        formPanel.add(createLabel("Retour prévu :"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        dateRetourPrevueField = createDateField(LocalDate.now().plusWeeks(2));
        formPanel.add(dateRetourPrevueField, gbc);

        // Note d'aide
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel helpLabel = new JLabel("💡 Tapez pour rechercher un membre ou un livre");
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        helpLabel.setForeground(new Color(100, 116, 139));
        formPanel.add(helpLabel, gbc);

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

    private JTextField createDateField(LocalDate defaultDate) {
        JTextField field = new JTextField(defaultDate.format(dateFormatter));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 40));
        field.setEditable(true);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    private JTextField createAutoCompleteField(String type) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 40));
        field.setEditable(true);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Créer la liste de suggestions
        JList<String> list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setFixedCellHeight(30);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPopupMenu popup = new JPopupMenu();
        popup.add(scrollPane);

        if (type.equals("membre")) {
            membrePopup = popup;
            membreList = list;
            membreScrollPane = scrollPane;
        } else {
            livrePopup = popup;
            livreList = list;
            livreScrollPane = scrollPane;
        }

        // Listener pour l'auto-complétion
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions(field, list, popup, type);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions(field, list, popup, type);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions(field, list, popup, type);
            }
        });

        // Sélection avec la souris
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectSuggestion(list, popup, field, type);
                }
            }
        });

        // Sélection avec les flèches et Entrée
        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    selectSuggestion(list, popup, field, type);
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    popup.setVisible(false);
                }
            }
        });

        // Navigation avec les flèches dans le champ
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && popup.isVisible()) {
                    list.setSelectedIndex(0);
                    list.requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    popup.setVisible(false);
                }
            }
        });

        // Cacher le popup quand on perd le focus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    if (popup.isVisible()) {
                        Component opposite = e.getOppositeComponent();
                        if (opposite != popup && opposite != scrollPane && opposite != list) {
                            popup.setVisible(false);
                        }
                    }
                });
            }
        });

        return field;
    }

    private void updateSuggestions(JTextField field, JList<String> list, JPopupMenu popup, String type) {
        String text = field.getText().trim().toLowerCase();
        List<String> suggestions = new ArrayList<>();

        if (type.equals("membre")) {
            for (Membre m : allMembres) {
                String fullName = (m.getNom() + " " + m.getPrenom()).toLowerCase();
                if (fullName.contains(text)) {
                    suggestions.add(m.getNom() + " " + m.getPrenom());
                }
            }
        } else {
            for (Livre l : allLivres) {
                if (l.getTitre().toLowerCase().contains(text)) {
                    suggestions.add(l.getTitre());
                }
            }
        }

        if (!suggestions.isEmpty()) {
            list.setListData(suggestions.toArray(new String[0]));
            popup.show(field, 0, field.getHeight());
        } else {
            popup.setVisible(false);
        }
    }

    private void selectSuggestion(JList<String> list, JPopupMenu popup, JTextField field, String type) {
        String selected = list.getSelectedValue();
        if (selected != null) {
            field.setText(selected);
            popup.setVisible(false);

            if (type.equals("membre")) {
                selectedMembre = allMembres.stream()
                        .filter(m -> (m.getNom() + " " + m.getPrenom()).equals(selected))
                        .findFirst()
                        .orElse(null);
            } else {
                selectedLivre = allLivres.stream()
                        .filter(l -> l.getTitre().equals(selected))
                        .findFirst()
                        .orElse(null);
            }
        }
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void setMembres(List<Membre> membres) {
        this.allMembres = membres;
    }

    public void setLivres(List<Livre> livres) {
        this.allLivres = livres;
    }

    private void saveAndClose() {
        // Validation Membre
        if (selectedMembre == null) {
            // Essayer de trouver un membre correspondant au texte saisi
            String text = membreField.getText().trim();
            if (!text.isEmpty()) {
                selectedMembre = allMembres.stream()
                        .filter(m -> (m.getNom() + " " + m.getPrenom()).equalsIgnoreCase(text))
                        .findFirst()
                        .orElse(null);
            }

            if (selectedMembre == null) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un membre\n\n" +
                                "Tapez dans le champ et cliquez sur une suggestion.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Validation Livre
        if (selectedLivre == null) {
            // Essayer de trouver un livre correspondant au texte saisi
            String text = livreField.getText().trim();
            if (!text.isEmpty()) {
                selectedLivre = allLivres.stream()
                        .filter(l -> l.getTitre().equalsIgnoreCase(text))
                        .findFirst()
                        .orElse(null);
            }

            if (selectedLivre == null) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez sélectionner un livre\n\n" +
                                "Tapez dans le champ et cliquez sur une suggestion.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Validation Date d'emprunt
        LocalDate dateEmprunt;
        try {
            dateEmprunt = LocalDate.parse(dateEmpruntField.getText().trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Date d'emprunt invalide !\n" +
                            "Utilisez le format : JJ/MM/AAAA\n" +
                            "Exemple : 19/02/2026",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation Date de retour
        LocalDate dateRetour;
        try {
            dateRetour = LocalDate.parse(dateRetourPrevueField.getText().trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Date de retour invalide !\n" +
                            "Utilisez le format : JJ/MM/AAAA\n" +
                            "Exemple : 05/03/2026",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier que la date de retour est après la date d'emprunt
        if (dateRetour.isBefore(dateEmprunt)) {
            JOptionPane.showMessageDialog(this,
                    "La date de retour doit être après la date d'emprunt !",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saved = true;
        dispose();
    }

    public Membre getSelectedMembre() {
        return selectedMembre;
    }

    public Livre getSelectedLivre() {
        return selectedLivre;
    }

    public LocalDate getDateEmprunt() {
        try {
            return LocalDate.parse(dateEmpruntField.getText().trim(), dateFormatter);
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public LocalDate getDateRetourPrevue() {
        try {
            return LocalDate.parse(dateRetourPrevueField.getText().trim(), dateFormatter);
        } catch (Exception e) {
            return LocalDate.now().plusWeeks(2);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}