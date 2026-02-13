package com.bibliotheque.dao.impl;

import com.bibliotheque.config.DatabaseConfig;
import com.bibliotheque.dao.interfaces.LivreDAO;
import com.bibliotheque.model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour la gestion des livres
 */
public class LivreDAOImpl implements LivreDAO {

    @Override
    public void add(Livre livre) {
        String sql = "INSERT INTO Livre (titre, annee_publication, isbn, quantite_disponible) " +
                "VALUES (?, ?, ?, 1)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, livre.getTitre());
            pstmt.setInt(2, livre.getAnnee());
            pstmt.setString(3, generateISBN()); // Génération automatique pour simplifier

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        livre.setIdLivre(generatedKeys.getInt(1));
                    }
                }
            }

            System.out.println("Livre ajouté avec succès: " + livre.getTitre());

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout du livre", e);
        }
    }

    @Override
    public void update(Livre livre) {
        String sql = "UPDATE Livre SET titre = ?, annee_publication = ? WHERE id_livre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, livre.getTitre());
            pstmt.setInt(2, livre.getAnnee());
            pstmt.setInt(3, livre.getIdLivre());

            pstmt.executeUpdate();
            System.out.println("Livre modifié avec succès: " + livre.getTitre());

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du livre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la modification du livre", e);
        }
    }

    @Override
    public void delete(int idLivre) {
        String sql = "DELETE FROM Livre WHERE id_livre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idLivre);
            pstmt.executeUpdate();
            System.out.println("Livre supprimé avec succès (ID: " + idLivre + ")");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du livre", e);
        }
    }

    @Override
    public Livre findById(int idLivre) {
        String sql = "SELECT * FROM Livre WHERE id_livre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idLivre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractLivreFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du livre: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Livre> findAll() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre ORDER BY titre";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                livres.add(extractLivreFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres: " + e.getMessage());
            e.printStackTrace();
        }

        return livres;
    }

    public List<Livre> search(String keyword) {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livre WHERE titre LIKE ? ORDER BY titre";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    livres.add(extractLivreFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de livres: " + e.getMessage());
            e.printStackTrace();
        }

        return livres;
    }

    /**
     * Extraire un objet Livre depuis un ResultSet
     */
    private Livre extractLivreFromResultSet(ResultSet rs) throws SQLException {
        return new Livre(
                rs.getInt("id_livre"),
                rs.getString("titre"),
                rs.getInt("annee_publication"),
                "Général" // Catégorie par défaut
        );
    }

    /**
     * Générer un ISBN simplifié
     */
    private String generateISBN() {
        return "ISBN-" + System.currentTimeMillis();
    }
}
