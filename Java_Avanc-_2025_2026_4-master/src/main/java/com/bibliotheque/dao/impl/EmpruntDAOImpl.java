package com.bibliotheque.dao.impl;

import com.bibliotheque.config.DatabaseConfig;
import com.bibliotheque.dao.interfaces.EmpruntDAO;
import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Membre;
import com.bibliotheque.model.Livre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du DAO pour la gestion des emprunts
 */
public class EmpruntDAOImpl implements EmpruntDAO {

    @Override
    public void enregistrerEmprunt(Emprunt emprunt) {
        String sql = "INSERT INTO Emprunt (id_membre, id_livre, date_emprunt, date_retour_prevue, statut) " +
                "VALUES (?, ?, ?, ?, 'EN_COURS')";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, emprunt.getMembre().getIdMembre());
            pstmt.setInt(2, emprunt.getLivre().getIdLivre());
            pstmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            pstmt.setDate(4, Date.valueOf(emprunt.getDateRetourPrevue()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        emprunt.setIdEmprunt(generatedKeys.getInt(1));
                    }
                }
            }

            System.out.println("Emprunt enregistré avec succès");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de l'emprunt: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement de l'emprunt", e);
        }
    }

    @Override
    public void enregistrerRetour(int idEmprunt) {
        String sql = "UPDATE Emprunt SET date_retour_effective = ?, statut = 'TERMINE' WHERE id_emprunt = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.setInt(2, idEmprunt);

            pstmt.executeUpdate();
            System.out.println("Retour enregistré avec succès");

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du retour: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement du retour", e);
        }
    }

    @Override
    public List<Emprunt> findByMembre(int idMembre) {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, m.nom, m.prenom, m.email, m.telephone, m.lieu_residence, m.penalite, " +
                "l.titre, l.annee_publication " +
                "FROM Emprunt e " +
                "JOIN Membre m ON e.id_membre = m.id_membre " +
                "JOIN Livre l ON e.id_livre = l.id_livre " +
                "WHERE e.id_membre = ? " +
                "ORDER BY e.date_emprunt DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idMembre);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    emprunts.add(extractEmpruntFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des emprunts: " + e.getMessage());
            e.printStackTrace();
        }

        return emprunts;
    }

    @Override
    public List<Emprunt> findEnCours() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, m.nom, m.prenom, m.email, m.telephone, m.lieu_residence, m.penalite, " +
                "l.titre, l.annee_publication " +
                "FROM Emprunt e " +
                "JOIN Membre m ON e.id_membre = m.id_membre " +
                "JOIN Livre l ON e.id_livre = l.id_livre " +
                "WHERE e.statut = 'EN_COURS' " +
                "ORDER BY e.date_emprunt DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                emprunts.add(extractEmpruntFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des emprunts en cours: " + e.getMessage());
            e.printStackTrace();
        }

        return emprunts;
    }

    public List<Emprunt> findAll() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.*, m.nom, m.prenom, m.email, m.telephone, m.lieu_residence, m.penalite, " +
                "l.titre, l.annee_publication " +
                "FROM Emprunt e " +
                "JOIN Membre m ON e.id_membre = m.id_membre " +
                "JOIN Livre l ON e.id_livre = l.id_livre " +
                "ORDER BY e.date_emprunt DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                emprunts.add(extractEmpruntFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les emprunts: " + e.getMessage());
            e.printStackTrace();
        }

        return emprunts;
    }

    /**
     * Extraire un objet Emprunt depuis un ResultSet
     */
    private Emprunt extractEmpruntFromResultSet(ResultSet rs) throws SQLException {
        Membre membre = new Membre(
                rs.getInt("id_membre"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getString("lieu_residence"),
                rs.getDouble("penalite")
        );

        Livre livre = new Livre(
                rs.getInt("id_livre"),
                rs.getString("titre"),
                rs.getInt("annee_publication"),
                "Général"
        );

        Date dateRetourEffective = rs.getDate("date_retour_effective");

        return new Emprunt(
                rs.getInt("id_emprunt"),
                membre,
                livre,
                rs.getDate("date_emprunt").toLocalDate(),
                rs.getDate("date_retour_prevue").toLocalDate(),
                dateRetourEffective != null ? dateRetourEffective.toLocalDate() : null
        );
    }
}
