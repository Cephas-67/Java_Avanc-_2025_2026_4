
 
import   src.main.java.com.bibliotheque.dao.interfaces.AuteurDAO.java;
import src.main.java.com.bibliotheque.model.Auteur.java; 
 
 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class AuteurDAOImpl implements AuteurDAO.java { 
 
     
    public void add(Auteur auteur) { 
        String sql = "INSERT INTO Auteur(nom, prenom) VALUES(?, ?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, auteur.getNom()); 
            stmt.setString(2, auteur.getPrenom()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur ajout auteur : " + e.getMessage()); 
        } 
    } 
 
    
    public void update(Auteur auteur) { 
        String sql = "UPDATE Auteur SET nom=?, prenom=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, auteur.getNom()); 
            stmt.setString(2, auteur.getPrenom()); 
            stmt.setInt(3, auteur.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur update auteur : " + e.getMessage()); 
        } 
    } 
 
    
    public void delete(int idAuteur) { 
        String sql = "DELETE FROM Auteur WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idAuteur); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur delete auteur : " + e.getMessage()); 
        } 
    } 
 
    @Override 
    public Auteur findById(int idAuteur) { 
        String sql = "SELECT * FROM Auteur WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idAuteur); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new Auteur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur find auteur : " + e.getMessage()); 
        } 
        return null; 
    } 
 
    
    public List<Auteur> findAll() { 
        List<Auteur> auteurs = new ArrayList<>(); 
        String sql = "SELECT * FROM Auteur"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                auteurs.add(new Auteur(rs.getInt("id"), rs.getString("nom"), 
rs.getString("prenom"))); 
            } 
        } catch (SQLException e) { 
                   } 
        return auteurs; 
    } 
}











//GESTION DES EXCEPTION





public void add(Auteur auteur) { 
    String sql = "INSERT INTO Auteur(nom, prenom) VALUES(?, ?)"; 
    try (Connection conn = SQLiteConnection.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) { 
        stmt.setString(1, auteur.getNom()); 
        stmt.setString(2, auteur.getPrenom()); 
        stmt.executeUpdate(); 
    } catch (SQLException e) { 
        System.err.println("Erreur ajout auteur : " + e.getMessage()); 
        throw new RuntimeException("Impossible d'ajouter l'auteur", e); 
    } 
}