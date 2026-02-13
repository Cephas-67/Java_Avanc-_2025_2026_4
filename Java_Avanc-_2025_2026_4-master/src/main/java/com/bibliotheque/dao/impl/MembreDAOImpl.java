package com.bibliotheque.dao.impl;

import com.bibliotheque.config.DatabaseConfig;
import com.bibliotheque.dao.interfaces.MembreDAO;
import com.bibliotheque.model.Membre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour la gestion des membres
 */
public class MembreDAOImpl implements MembreDAO {

    @Override
    public void add(Membre membre) {
        String sql = "INSERT INTO Membre (nom, prenom, email, telephone, lieu_residence, penalite) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setString(4, membre.getTelephone());
            pstmt.setString(5, membre.getLieuResidence());
            pstmt.setDouble(6, membre.getPenalite());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        membre.setIdMembre(generatedKeys.getInt(1));
                    }
                }
            }

            System.out.println("Membre ajouté avec succès: " + membre.getNom() + " " + membre.getPrenom());

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du membre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout du membre", e);
        }
    }

    @Override
    public void update(Membre membre) {
        String sql = "UPDATE Membre SET nom = ?, prenom = ?, email = ?, " +
                "telephone = ?, lieu_residence = ?, penalite = ? WHERE id_membre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, membre.getNom());
            pstmt.setString(2, membre.getPrenom());
            pstmt.setString(3, membre.getEmail());
            pstmt.setString(4, membre.getTelephone());
            pstmt.setString(5, membre.getLieuResidence());
            pstmt.setDouble(6, membre.getPenalite());
            pstmt.setInt(7, membre.getIdMembre());

            pstmt.executeUpdate();
            System.out.println("Membre modifié avec succès: " + membre.getNom() + " " + membre.getPrenom());

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du membre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la modification du membre", e);
        }
    }

    @Override
    public void delete(int idMembre) {
        String sql = "DELETE FROM Membre WHERE id_membre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idMembre);
            pstmt.executeUpdate();
            System.out.println("Membre supprimé avec succès (ID: " + idMembre + ")");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du membre: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du membre", e);
        }
    }

    @Override
    public Membre findById(int idMembre) {
        String sql = "SELECT * FROM Membre WHERE id_membre = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idMembre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractMembreFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du membre: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Membre> findAll() {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM Membre ORDER BY nom, prenom";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                membres.add(extractMembreFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres: " + e.getMessage());
            e.printStackTrace();
        }

        return membres;
    }

    @Override
    public List<Membre> search(String keyword) {
        List<Membre> membres = new ArrayList<>();
        String sql = "SELECT * FROM Membre WHERE " +
                "nom LIKE ? OR prenom LIKE ? OR email LIKE ? OR telephone LIKE ? " +
                "ORDER BY nom, prenom";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    membres.add(extractMembreFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de membres: " + e.getMessage());
            e.printStackTrace();
        }

        return membres;
    }

    /**
     * Extraire un objet Membre depuis un ResultSet
     */
    private Membre extractMembreFromResultSet(ResultSet rs) throws SQLException {
        return new Membre(
                rs.getInt("id_membre"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("lieu_residence"),
                rs.getDouble("penalite")
        );
    }
}
