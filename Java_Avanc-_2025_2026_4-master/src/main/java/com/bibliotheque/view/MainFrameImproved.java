package main.java.com.bibliotheque.view;

import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.MembreService;
import com.bibliotheque.service.EmpruntService;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MainFrameImproved extends JFrame {

    private JPanel contentPanel;
    private LivreView livreView;
    private MembreView membreView;
    private EmpruntView empruntView;

    private JLabel livresCountLabel;
    private JLabel membresCountLabel;
    private JLabel empruntsCountLabel;

    // Couleurs du thème
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252);
    private static final Color ACCENT_COLOR = new Color(99, 102, 241);
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color HOVER_COLOR = new Color(219, 234, 254);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color WARNING_COLOR = new Color(245, 158, 11);

    public MainFrameImproved() {
        setTitle("Système de Gestion de Bibliothèque - BiblioTech");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        // Panneau de navigation latéral amélioré
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        // Zone de contenu principal
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(SECONDARY_COLOR);

        // Panneau d'accueil amélioré
        JPanel homePanel = createHomePanel();
        contentPanel.add(homePanel, "HOME");
        contentPanel.add(livreView, "LIVRES");
        contentPanel.add(membreView, "MEMBRES");
        contentPanel.add(empruntView, "EMPRUNTS");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(260, getHeight()));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(226, 232, 240)));

        // En-tête avec logo amélioré
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setMaximumSize(new Dimension(260, 120));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel logoLabel = new JLabel("📚");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("BiblioTech");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Gestion Moderne");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(219, 234, 254));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(30));

        // Section Navigation
        JLabel navLabel = new JLabel("NAVIGATION");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        navLabel.setForeground(new Color(100, 116, 139));
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 0));
        panel.add(navLabel);

        // Boutons de navigation
        panel.add(createNavButton("🏠  Tableau de bord", "HOME"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createNavButton("📖  Livres", "LIVRES"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createNavButton("👥  Membres", "MEMBRES"));
        panel.add(Box.createVerticalStrut(5));
        panel.add(createNavButton("🔄  Emprunts", "EMPRUNTS"));

        // Espacement flexible
        panel.add(Box.createVerticalGlue());

        // Pied de page
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setMaximumSize(new Dimension(260, 60));
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(new Color(148, 163, 184));
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        footerPanel.add(versionLabel);
        panel.add(footerPanel);

        return panel;
    }

    private JButton createNavButton(String text, String view) {
        JButton button = new JButton(text) {
            private boolean isActive = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isActive) {
                    g2.setColor(HOVER_COLOR);
                    g2.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 8, 8);
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(241, 245, 249));
                    g2.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 8, 8);
                }

                g2.dispose();
                super.paintComponent(g);
            }

            public void setActive(boolean active) {
                this.isActive = active;
                repaint();
            }
        };

        button.setMaximumSize(new Dimension(240, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.addActionListener(e -> {
            showView(view);
            if (view.equals("HOME")) {
                updateStatistics();
            }
        });

        return button;
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // En-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SECONDARY_COLOR);

        JLabel welcomeLabel = new JLabel("Tableau de Bord");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_COLOR);

        JLabel dateLabel = new JLabel(java.time.LocalDate.now().format(
                java.time.format.DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", java.util.Locale.FRENCH)
        ));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(100, 116, 139));

        JPanel headerTextPanel = new JPanel();
        headerTextPanel.setLayout(new BoxLayout(headerTextPanel, BoxLayout.Y_AXIS));
        headerTextPanel.setBackground(SECONDARY_COLOR);
        headerTextPanel.add(welcomeLabel);
        headerTextPanel.add(Box.createVerticalStrut(5));
        headerTextPanel.add(dateLabel);

        headerPanel.add(headerTextPanel, BorderLayout.WEST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Cartes statistiques
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBackground(SECONDARY_COLOR);

        // Carte Livres
        livresCountLabel = new JLabel("0");
        JPanel livresCard = createStatCard("Livres Disponibles", livresCountLabel,
                PRIMARY_COLOR, "📚");
        statsPanel.add(livresCard);

        // Carte Membres
        membresCountLabel = new JLabel("0");
        JPanel membresCard = createStatCard("Membres Inscrits", membresCountLabel,
                SUCCESS_COLOR, "👥");
        statsPanel.add(membresCard);

        // Carte Emprunts
        empruntsCountLabel = new JLabel("0");
        JPanel empruntsCard = createStatCard("Emprunts Actifs", empruntsCountLabel,
                WARNING_COLOR, "🔄");
        statsPanel.add(empruntsCard);

        panel.add(statsPanel, BorderLayout.CENTER);

        // Message de bienvenue
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        JLabel infoTitle = new JLabel("✨ Bienvenue dans BiblioTech");
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        infoTitle.setForeground(TEXT_COLOR);

        JLabel infoText = new JLabel(
                "<html>Gérez efficacement votre bibliothèque avec notre système moderne et intuitif.<br>" +
                        "Utilisez le menu de navigation pour accéder aux différentes fonctionnalités.</html>"
        );
        infoText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoText.setForeground(new Color(100, 116, 139));

        welcomePanel.add(infoTitle);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(infoText);

        panel.add(welcomePanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color, String emoji) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // Icône
        JLabel iconLabel = new JLabel(emoji);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        card.add(iconLabel, BorderLayout.WEST);

        // Texte
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 116, 139));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        valueLabel.setForeground(color);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private void showView(String viewName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, viewName);
    }

    /**
     * Mettre à jour les statistiques du dashboard
     */
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

    // Getters pour accéder aux vues depuis Main
    public LivreView getLivreView() {
        return livreView;
    }

    public MembreView getMembreView() {
        return membreView;
    }

    public EmpruntView getEmpruntView() {
        return empruntView;
    }
}
