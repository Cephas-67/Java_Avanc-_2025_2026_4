package main.java.com.bibliotheque.view;

import main.java.com.bibliotheque.dao.impl.EmpruntDAOImpl;
import main.java.com.bibliotheque.dao.impl.LivreDAOImpl;
import main.java.com.bibliotheque.dao.impl.MembreDAOImpl;
import main.java.com.bibliotheque.service.LivreService;
import main.java.com.bibliotheque.service.MembreService;
import main.java.com.bibliotheque.service.EmpruntService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Fenêtre principale de l'application - Design Moderne Professionnel
 */
public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private LivreView livreView;
    private MembreView membreView;
    private EmpruntView empruntView;

    private JLabel livresCountLabel;
    private JLabel membresCountLabel;
    private JLabel empruntsCountLabel;

    private NavButton btnAccueil, btnLivres, btnMembres, btnEmprunts;

    // Couleurs du thème moderne
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246);      // Bleu moderne
    private static final Color PRIMARY_DARK = new Color(37, 99, 235);        // Bleu foncé
    private static final Color SECONDARY_COLOR = new Color(241, 245, 249);   // Gris clair
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);  // Fond très clair
    private static final Color TEXT_COLOR = new Color(15, 23, 42);           // Noir bleuté
    private static final Color TEXT_MUTED = new Color(100, 116, 139);        // Gris texte
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);      // Vert succès
    private static final Color WARNING_COLOR = new Color(245, 158, 11);      // Orange warning
    private static final Color DANGER_COLOR = new Color(239, 68, 68);        // Rouge danger
    private static final Color SIDEBAR_BG = new Color(30, 58, 138);          // Bleu sidebar
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(226, 232, 240);

    public MainFrame() {
        setTitle("BiblioTech - Système de Gestion de Bibliothèque");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));

        // Activer le rendu anti-aliasing
        UIManager.put("Label.font", new Font("Inter", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Inter", Font.PLAIN, 14));

        initializeViews();
        setupUI();
        updateStatistics();
    }

    private void initializeViews() {
        livreView = new LivreView();
        membreView = new MembreView();
        empruntView = new EmpruntView();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);

        JPanel homePanel = createHomePanel();
        contentPanel.add(homePanel, "HOME");
        contentPanel.add(livreView.getMainPanel(), "LIVRES");
        contentPanel.add(membreView.getMainPanel(), "MEMBRES");
        contentPanel.add(empruntView.getMainPanel(), "EMPRUNTS");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(280, getHeight()));
        panel.setBackground(SIDEBAR_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder());

        // Header avec logo
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(SIDEBAR_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));
        headerPanel.setMaximumSize(new Dimension(280, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel logoIcon = new JLabel(createLogoIcon());
        headerPanel.add(logoIcon, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 15, 0, 0);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("BiblioTech");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Gestion Bibliothèque");
        subtitleLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitleLabel);

        headerPanel.add(textPanel, gbc);
        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(20));

        // Séparateur
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setMaximumSize(new Dimension(280, 1));
        separator1.setBackground(new Color(51, 65, 85));
        panel.add(separator1);
        panel.add(Box.createVerticalStrut(25));

        // Titre Navigation
        JLabel navTitle = new JLabel("MENU PRINCIPAL");
        navTitle.setFont(new Font("Inter", Font.BOLD, 11));
        navTitle.setForeground(new Color(148, 163, 184));
        navTitle.setBorder(BorderFactory.createEmptyBorder(0, 25, 15, 0));
        navTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(navTitle);

        // Boutons de navigation
        btnAccueil = createNavButton("Tableau de bord", "HOME", createDashboardIcon(), true);
        btnLivres = createNavButton("Livres", "LIVRES", createBooksIcon(), false);
        btnMembres = createNavButton("Membres", "MEMBRES", createUsersIcon(), false);
        btnEmprunts = createNavButton("Emprunts", "EMPRUNTS", createLoanIcon(), false);

        panel.add(btnAccueil);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnLivres);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnMembres);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnEmprunts);

        panel.add(Box.createVerticalGlue());

        // Séparateur
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setMaximumSize(new Dimension(280, 1));
        separator2.setBackground(new Color(51, 65, 85));
        panel.add(separator2);
        panel.add(Box.createVerticalStrut(20));

        // Bouton Reset Global
        JButton resetButton = createResetButton();
        resetButton.setMaximumSize(new Dimension(240, 45));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resetButton);
        panel.add(Box.createVerticalStrut(15));

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(SIDEBAR_BG);
        footerPanel.setMaximumSize(new Dimension(280, 60));
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 20, 25));

        JLabel versionLabel = new JLabel("Version 2.0.0");
        versionLabel.setFont(new Font("Inter", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(148, 163, 184));
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        footerPanel.add(versionLabel);
        panel.add(footerPanel);

        return panel;
    }

    private JButton createResetButton() {
        JButton button = new JButton("🗑️ Réinitialiser Toutes Données", createResetIcon());
        button.setFont(new Font("Inter", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(DANGER_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setIconTextGap(12);

        button.addActionListener(e -> handleGlobalReset());

        return button;
    }

    private NavButton createNavButton(String text, String view, Icon icon, boolean active) {
        NavButton button = new NavButton(text, icon, active);

        button.setMaximumSize(new Dimension(280, 50));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Inter", Font.PLAIN, 14));
        button.setForeground(active ? Color.WHITE : new Color(203, 213, 225));
        button.setBackground(active ? PRIMARY_COLOR : SIDEBAR_BG);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setIconTextGap(15);

        button.addActionListener(e -> {
            showView(view);
            updateActiveButton(button);
            if (view.equals("HOME")) {
                updateStatistics();
            }
        });

        return button;
    }

    private void updateActiveButton(NavButton activeButton) {
        btnAccueil.setActive(false);
        btnLivres.setActive(false);
        btnMembres.setActive(false);
        btnEmprunts.setActive(false);
        activeButton.setActive(true);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(30, 30));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel welcomeLabel = new JLabel("Tableau de Bord");
        welcomeLabel.setFont(new Font("Inter", Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_COLOR);

        JLabel dateLabel = new JLabel(LocalDate.now().format(
                DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale.FRENCH)
        ));
        dateLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        dateLabel.setForeground(TEXT_MUTED);

        JPanel headerTextPanel = new JPanel();
        headerTextPanel.setLayout(new BoxLayout(headerTextPanel, BoxLayout.Y_AXIS));
        headerTextPanel.setBackground(BACKGROUND_COLOR);
        headerTextPanel.add(welcomeLabel);
        headerTextPanel.add(Box.createVerticalStrut(8));
        headerTextPanel.add(dateLabel);

        headerPanel.add(headerTextPanel, BorderLayout.WEST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Cartes statistiques modernes
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        livresCountLabel = new JLabel("0");
        JPanel livresCard = createModernStatCard("Livres Disponibles", livresCountLabel,
                PRIMARY_COLOR, createModernBookIcon(), "Total des livres dans la bibliothèque");
        statsPanel.add(livresCard);

        membresCountLabel = new JLabel("0");
        JPanel membresCard = createModernStatCard("Membres Inscrits", membresCountLabel,
                SUCCESS_COLOR, createModernUserIcon(), "Nombre total de membres actifs");
        statsPanel.add(membresCard);

        empruntsCountLabel = new JLabel("0");
        JPanel empruntsCard = createModernStatCard("Emprunts Actifs", empruntsCountLabel,
                WARNING_COLOR, createModernLoanIcon(), "Emprunts en cours actuellement");
        statsPanel.add(empruntsCard);

        panel.add(statsPanel, BorderLayout.CENTER);

        // Section d'information
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JLabel infoTitle = new JLabel("📚 Bienvenue sur BiblioTech");
        infoTitle.setFont(new Font("Inter", Font.BOLD, 18));
        infoTitle.setForeground(TEXT_COLOR);
        infoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoText = new JLabel(
                "<html><div style='line-height: 1.6; color: #64748b;'>" +
                        "Gérez efficacement votre bibliothèque avec notre système moderne et intuitif. " +
                        "Utilisez le menu de navigation pour accéder aux différentes fonctionnalités." +
                        "</div></html>"
        );
        infoText.setFont(new Font("Inter", Font.PLAIN, 14));
        infoText.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(infoTitle);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(infoText);

        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createModernStatCard(String title, JLabel valueLabel, Color color, Icon icon, String description) {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setLayout(new BorderLayout(20, 15));

        // Icône
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(iconLabel, BorderLayout.WEST);

        // Texte
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        titleLabel.setForeground(TEXT_MUTED);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        valueLabel.setFont(new Font("Inter", Font.BOLD, 42));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        descLabel.setForeground(TEXT_MUTED);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(valueLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(descLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private void showView(String viewName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, viewName);
    }

    public void updateStatistics() {
        SwingWorker<int[], Void> worker = new SwingWorker<>() {
            @Override
            protected int[] doInBackground() {
                try {
                    LivreService livreService = new LivreService();
                    MembreService membreService = new MembreService();
                    EmpruntService empruntService = new EmpruntService();

                    int livresCount = livreService.getAllLivres().size();
                    int membresCount = membreService.getAllMembres().size();
                    int empruntsCount = empruntService.getEmpruntsEnCours().size();

                    return new int[]{livresCount, membresCount, empruntsCount};
                } catch (Exception e) {
                    e.printStackTrace();
                    return new int[]{0, 0, 0};
                }
            }

            @Override
            protected void done() {
                try {
                    int[] stats = get();
                    livresCountLabel.setText(String.valueOf(stats[0]));
                    membresCountLabel.setText(String.valueOf(stats[1]));
                    empruntsCountLabel.setText(String.valueOf(stats[2]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void handleGlobalReset() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "<html><div style='width: 400px;'>" +
                        "<h3 style='color: #ef4444; margin-bottom: 15px;'>⚠️ Réinitialisation Complète</h3>" +
                        "<p>Êtes-vous ABSOLUMENT SÛR de vouloir supprimer :</p>" +
                        "<ul style='margin: 15px 0; padding-left: 20px;'>" +
                        "<li>Tous les livres</li>" +
                        "<li>Tous les membres</li>" +
                        "<li>Tous les emprunts</li>" +
                        "</ul>" +
                        "<p style='color: #ef4444; font-weight: bold;'>Cette action est IRREVERSIBLE !</p>" +
                        "</div></html>",
                "Confirmation de Réinitialisation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Tapez RESET pour confirmer :",
                    "Confirmation Finale",
                    JOptionPane.WARNING_MESSAGE
            );

            if ("RESET".equals(input)) {
                try {
                    LivreDAOImpl livreDAO = new LivreDAOImpl();
                    MembreDAOImpl membreDAO = new MembreDAOImpl();
                    EmpruntDAOImpl empruntDAO = new EmpruntDAOImpl();

                    livreDAO.reset();
                    membreDAO.reset();
                    empruntDAO.reset();

                    // Rafraîchir les vues
                    livreView.clearTable();
                    membreView.clearTable();
                    empruntView.clearTable();

                    updateStatistics();

                    JOptionPane.showMessageDialog(
                            null,
                            "✅ Toutes les données ont été réinitialisées avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Erreur : " + ex.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    // Getters pour les contrôleurs
    public LivreView getLivreView() { return livreView; }
    public MembreView getMembreView() { return membreView; }
    public EmpruntView getEmpruntView() { return empruntView; }

    // ==================== CLASSE INTERNE PERSONNALISÉE ====================
    private static class NavButton extends JButton {
        private boolean isActive = false;

        public NavButton(String text, Icon icon, boolean active) {
            super(text, icon);
            this.isActive = active;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isActive) {
                g2.setColor(PRIMARY_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(51, 65, 85));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
            }

            g2.dispose();
            super.paintComponent(g);
        }

        public void setActive(boolean active) {
            this.isActive = active;
            if (active) {
                setForeground(Color.WHITE);
                setBackground(PRIMARY_COLOR);
            } else {
                setForeground(new Color(203, 213, 225));
                setBackground(SIDEBAR_BG);
            }
            repaint();
        }

        public boolean isActive() {
            return isActive;
        }
    }

    // ==================== ICÔNES SVG PROFESSIONNELLES ====================

    private Icon createLogoIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);

                // Livre stylisé
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawRect(x + 5, y + 8, 28, 22);
                g2.drawLine(x + 12, y + 8, x + 12, y + 30);
                g2.drawLine(x + 5, y + 15, x + 18, y + 15);

                g2.dispose();
            }
            public int getIconWidth() { return 38; }
            public int getIconHeight() { return 38; }
        };
    }

    private Icon createDashboardIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnAccueil != null && btnAccueil.isActive ? Color.WHITE : new Color(148, 163, 184));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Tableau de bord
                g2.drawRect(x + 4, y + 6, 16, 12);
                g2.drawLine(x + 4, y + 12, x + 20, y + 12);
                g2.drawLine(x + 12, y + 6, x + 12, y + 18);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createBooksIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnLivres != null && btnLivres.isActive ? Color.WHITE : new Color(148, 163, 184));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Livres empilés
                g2.drawRect(x + 6, y + 4, 14, 16);
                g2.drawLine(x + 10, y + 4, x + 10, y + 20);
                g2.drawLine(x + 6, y + 10, x + 20, y + 10);
                g2.drawLine(x + 8, y + 6, x + 8, y + 18);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createUsersIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnMembres != null && btnMembres.isActive ? Color.WHITE : new Color(148, 163, 184));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Utilisateurs
                g2.drawOval(x + 8, y + 4, 8, 8);
                Path2D path = new Path2D.Double();
                path.moveTo(x + 5, y + 20);
                path.curveTo(x + 5, y + 16, x + 8, y + 14, x + 12, y + 14);
                path.curveTo(x + 16, y + 14, x + 19, y + 16, x + 19, y + 20);
                g2.draw(path);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createLoanIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btnEmprunts != null && btnEmprunts.isActive ? Color.WHITE : new Color(148, 163, 184));
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Emprunt (flèches circulaires)
                g2.drawArc(x + 4, y + 4, 16, 16, 45, 270);
                g2.drawLine(x + 18, y + 6, x + 20, y + 4);
                g2.drawLine(x + 18, y + 6, x + 20, y + 8);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createModernBookIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Livre moderne
                GradientPaint gradient = new GradientPaint(x, y, PRIMARY_COLOR, x + 40, y + 40, PRIMARY_DARK);
                g2.setPaint(gradient);
                g2.fillRoundRect(x + 8, y + 8, 24, 24, 6, 6);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x + 16, y + 12, x + 16, y + 28);
                g2.drawLine(x + 12, y + 18, x + 20, y + 18);

                g2.dispose();
            }
            public int getIconWidth() { return 40; }
            public int getIconHeight() { return 40; }
        };
    }

    private Icon createModernUserIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Utilisateur moderne
                GradientPaint gradient = new GradientPaint(x, y, SUCCESS_COLOR, x + 40, y + 40, new Color(5, 150, 105));
                g2.setPaint(gradient);

                g2.fillOval(x + 12, y + 8, 16, 16);
                Path2D path = new Path2D.Double();
                path.moveTo(x + 6, y + 32);
                path.curveTo(x + 6, y + 26, x + 10, y + 22, x + 20, y + 22);
                path.curveTo(x + 30, y + 22, x + 34, y + 26, x + 34, y + 32);
                g2.fill(path);

                g2.dispose();
            }
            public int getIconWidth() { return 40; }
            public int getIconHeight() { return 40; }
        };
    }

    private Icon createModernLoanIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Emprunt moderne
                GradientPaint gradient = new GradientPaint(x, y, WARNING_COLOR, x + 40, y + 40, new Color(217, 119, 6));
                g2.setPaint(gradient);

                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawArc(x + 8, y + 8, 24, 24, 45, 270);
                g2.drawLine(x + 28, y + 12, x + 32, y + 8);
                g2.drawLine(x + 28, y + 12, x + 32, y + 16);

                g2.dispose();
            }
            public int getIconWidth() { return 40; }
            public int getIconHeight() { return 40; }
        };
    }

    private Icon createResetIcon() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                // Poubelle
                g2.drawLine(x + 6, y + 4, x + 18, y + 4);
                g2.drawLine(x + 8, y + 4, x + 8, y + 20);
                g2.drawLine(x + 16, y + 4, x + 16, y + 20);
                g2.drawLine(x + 5, y + 7, x + 19, y + 7);
                g2.fillRect(x + 10, y + 10, 2, 8);
                g2.fillRect(x + 14, y + 10, 2, 8);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }
}