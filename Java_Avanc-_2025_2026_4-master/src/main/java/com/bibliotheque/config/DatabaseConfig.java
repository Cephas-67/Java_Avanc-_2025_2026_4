package main.java.com.bibliotheque.config;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import main.java.com.bibliotheque.model.Membre;
import main.java.com.bibliotheque.model.Livre;

public class DatabaseConfig {
    
    private static final String URL = "jdbc:mysql://localhost:3307/library";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }

    public static boolean AddMember(final int id, final String name ,final String firstName,
            final String email,final String telephone,final String address, 
            final double penality){
    String query = "INSERT INTO members (memberId, lastName, firstName, email, telephone, address, penality) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1,id);
        pstmt.setString(2, name);
        pstmt.setString(3, firstName);
        pstmt.setString(4, email);
        pstmt.setString(5,telephone);
        pstmt.setString(6, address);
        pstmt.setDouble(7, penality);
        
        int resultat = pstmt.executeUpdate();
        return resultat > 0; 
        
    } catch (SQLException e) {
        System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    }
    
    public static boolean updateMember(int id, String name, String firstName,
            String email, String telephone, String address, double penality) {
    String query = "UPDATE members SET lastName = ?, firstName = ?, email = ?,"
            + " telephone = ?, address = ?, penality = ? WHERE memberId = ?";
    
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setString(1, name);
        pstmt.setString(2, firstName);
        pstmt.setString(3, email);
        pstmt.setString(4, telephone);
        pstmt.setString(5, address);
        pstmt.setDouble(6, penality);
        pstmt.setInt(7, id);
        
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Erreur mise à jour membre : " + e.getMessage());
        return false;
    }
}
    
public static boolean deleteMember(int id) {
    String query = "DELETE FROM members WHERE memberId = ?";
    
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Erreur suppression membre : " + e.getMessage());
        return false;
    }
}

     public static List<Membre> getAllMembers() {
        List<Membre> members = new ArrayList<>();
        String query = "SELECT * FROM members";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Membre member = new Membre(
                    rs.getInt("memberId"),
                    rs.getString("lastName"),
                    rs.getString("firstName"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("address"),
                    rs.getDouble("penality")
                );
                members.add(member);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des membres : " + e.getMessage());
            e.printStackTrace();
        }
        return members;
    }

    public static boolean addBook(final int id, final String title ,final int year,
            final String genre, final String author, final String editionHouse){
    String query = "INSERT INTO books (idBook, title, editionYear, genre, author, editionHouse)"
            + "VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1,id);
        pstmt.setString(2, title);
        pstmt.setInt(3, year);
        pstmt.setString(4, genre);
        pstmt.setString(5, author);
        pstmt.setString(6, editionHouse);

      
        
        int resultat = pstmt.executeUpdate();
        return resultat > 0; 
        
    } catch (SQLException e) {
        System.err.println("Erreur lors de l'insertion : " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    }
    
    public static boolean updateBook(final int id, final String title ,final int year,
            final String genre, final String author, final String editionHouse) {
    String query = "UPDATE books SET title = ?, editionYear = ?, genre = ?,"
            + " author = ?, editionHouse = ? WHERE idBook = ?";
    
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setString(1, title);
        pstmt.setInt(2, year);
        pstmt.setString(3, genre);
        pstmt.setString(4, author);
        pstmt.setString(5, editionHouse);
        pstmt.setInt(6, id);
        
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Erreur mise à jour livre : " + e.getMessage());
        return false;
    }
}
    
    public static boolean deleteBook(int id) {
    String query = "DELETE FROM books WHERE idBook = ?";
    
    try (Connection conn = DatabaseConfig.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.err.println("Erreur suppression livre : " + e.getMessage());
        return false;
    }
}
    
    public static List<Livre> getAllBooks() {
        List<Livre> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Livre book = new Livre(
                    rs.getInt("idBook"),
                    rs.getString("title"),
                    rs.getInt("editionYear"),
                    rs.getString("genre"),
                    rs.getString("author"),
                    rs.getString("editionHouse")
                );
                books.add(book);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    
    public static boolean resetAllTables() {
        // Liste des tables dans l'ordre de suppression (enfants d'abord, parents ensuite)
        // Exemple : les tables avec clés étrangères doivent être vidées avant celles qu'elles référencent
        List<String> tables = new ArrayList<>();
        tables.add("checkingOut");     
        tables.add("books");        
        tables.add("members");
        

        String query;
        boolean success = true;

        try (Connection conn = DatabaseConfig.getConnection()) {
            // Je désactive temporairement les vérifications des clés étrangères pour éviter les erreurs de contraintes lors des DELETE
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            }

            for (String table : tables) {
                query = "DELETE FROM " + table;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(query);
                    System.out.println("Table '" + table + "' vidée.");
                } catch (SQLException e) {
                    System.err.println("Erreur sur la table " + table + " : " + e.getMessage());
                    success = false;
                }
            }

            // Je réactive ici les clés étrangères.
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }

        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            return false;
        }

        return success;
    }
    
     public static boolean addEmprunt(final int memberId, final int bookId,
             final LocalDate dateEmprunt, final LocalDate dateRetour ) {
        String query = "INSERT INTO checkingOut (memberId, bookId, lendingDate, backDate) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1,memberId );
            pstmt.setInt(2,bookId );
            pstmt.setDate(3,java.sql.Date.valueOf(dateEmprunt) );
            pstmt.setDate(4, java.sql.Date.valueOf(dateRetour) );
            
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur ajout emprunt : " + e.getMessage());
            return false;
        }
    }
     
     public static boolean deleteEmprunt(final int id) {
        String query = "DELETE FROM checkingOut WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur suppression emprunt : " + e.getMessage());
            return false;
        }
    }
}
