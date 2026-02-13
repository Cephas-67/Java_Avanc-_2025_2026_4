package main.java.com.bibliotheque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public static class MainFrame extends JFrame {

    private JPanel contentPanel;
    private LivreView livreView;
    private MembreView membreView;
    private EmpruntView empruntView;

    // Couleurs du thème
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235); // Bleu
    private static final Color SECONDARY_COLOR = new Color(248, 250, 252); // Gris clair
    private static final Color ACCENT_COLOR = new Color(99, 102, 241); // Indigo
    private static final Color TEXT_COLOR = new Color(51, 65, 85);
    private static final Color HOVER_COLOR = new Color(219, 234, 254);

    public MainFrame() {
        setTitle("Système de Gestion de Bibliothèque");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeViews();
        setupUI();
    }

    private void initializeViews() {
        livreView = new LivreView();
        membreView = new MembreView();
        empruntView = new EmpruntView();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Panneau de navigation latéral
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        // Zone de contenu principal
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(SECONDARY_COLOR);

        // Panneau d'accueil
        JPanel homePanel = createHomePanel();
        contentPanel.add(homePanel, "HOME");
        contentPanel.add(livreView, "LIVRES");
        contentPanel.add(membreView, "MEMBRES");
        contentPanel.add(empruntView, "EMPRUNTS");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, getHeight()));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(226, 232, 240)));

        // En-tête avec logo
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setMaximumSize(new Dimension(250, 100));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("📚 Bibliothèque");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel);

        panel.add(headerPanel);
        panel.add(Box.createVerticalStrut(20));

        // Boutons de navigation
        panel.add(createNavButton("Accueil", "HOME", createHomeSVG()));
        panel.add(createNavButton("Livres", "LIVRES", createBookSVG()));
        panel.add(createNavButton("Membres", "MEMBRES", createUserSVG()));
        panel.add(createNavButton("Emprunts", "EMPRUNTS", createBorrowSVG()));

        // Espacement flexible
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton createNavButton(String text, String view, Icon icon) {
        JButton button = new JButton(text, icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(HOVER_COLOR);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setMaximumSize(new Dimension(230, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIconTextGap(15);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.addActionListener(e -> showView(view));

        return button;
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECONDARY_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans le Système de Gestion");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(welcomeLabel, gbc);

        // Cartes statistiques
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        panel.add(createStatCard("Livres", "0", PRIMARY_COLOR, createBookSVG()), gbc);

        gbc.gridx = 1;
        panel.add(createStatCard("Membres", "0", new Color(16, 185, 129), createUserSVG()), gbc);

        gbc.gridx = 2;
        panel.add(createStatCard("Emprunts Actifs", "0", new Color(245, 158, 11), createBorrowSVG()), gbc);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color, Icon icon) {
        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(250, 150));

        // Icône
        JLabel iconLabel = new JLabel(icon);
        card.add(iconLabel, BorderLayout.WEST);

        // Texte
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 116, 139));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
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

    // Méthodes pour créer des icônes SVG
    private Icon createHomeSVG() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2));

                // Maison
                int[] xPoints = {x + 12, x + 24, x + 18, x + 6};
                int[] yPoints = {y + 6, y + 12, y + 12, y + 12};
                g2.drawPolygon(xPoints, yPoints, 4);
                g2.drawLine(x + 6, y + 12, x + 6, y + 22);
                g2.drawLine(x + 18, y + 12, x + 18, y + 22);
                g2.drawLine(x + 6, y + 22, x + 18, y + 22);
                g2.drawRect(x + 10, y + 16, 4, 6);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createBookSVG() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2));

                // Livre
                g2.drawRect(x + 6, y + 4, 12, 16);
                g2.drawLine(x + 10, y + 4, x + 10, y + 20);
                g2.drawLine(x + 6, y + 8, x + 18, y + 8);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    private Icon createUserSVG() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2));

                // Utilisateur
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

    private Icon createBorrowSVG() {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2));

                // Échange/Emprunt (flèches circulaires)
                g2.drawArc(x + 4, y + 4, 16, 16, 45, 270);
                g2.drawLine(x + 18, y + 6, x + 20, y + 4);
                g2.drawLine(x + 18, y + 6, x + 20, y + 8);

                g2.dispose();
            }
            public int getIconWidth() { return 24; }
            public int getIconHeight() { return 24; }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
// Getters pour accéder aux vues
public LivreView getLivreView() {
    LivreView livreView = new LivreView();
    return livreView;
}

public MembreView getMembreView() {
    MembreView membreView = new MembreView();
    return membreView;
}

public EmpruntView getEmpruntView() {
    EmpruntView empruntView = new EmpruntView();
    return empruntView;
}
