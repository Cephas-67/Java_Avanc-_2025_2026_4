package main.java.com.bibliotheque.app;

import main.java.com.bibliotheque.controller.EmpruntController;
import main.java.com.bibliotheque.controller.LivreController;
import main.java.com.bibliotheque.controller.MembreController;
import main.java.com.bibliotheque.view.MainFrame;

import javax.swing.*;

/**
 * Classe principale de l'application
 */
public class Main {

    public static void main(String[] args) {
        // Définir le Look and Feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du Look and Feel: " + e.getMessage());
        }

        // Lancer l'application sur le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Créer le frame principal
                MainFrame frame = new MainFrame();

                // Initialiser les contrôleurs
                new MembreController(frame.getMembreView());
                new LivreController(frame.getLivreView());
                new EmpruntController(frame.getEmpruntView());

                // Afficher la fenêtre
                frame.setVisible(true);

                System.out.println("Application de gestion de bibliothèque démarrée avec succès!");

            } catch (Exception e) {
                System.err.println("Erreur lors du démarrage de l'application: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Erreur lors du démarrage de l'application:\n" + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
