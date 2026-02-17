import   src.main.java.com.bibliotheque.dao.interfaces.Livre.java;
import src.main.java.com.bibliotheque.model.Livre.java; 
  
 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class LivreDAOImpl implements LivreDAO { 
 
    
    public void add(Livre livre) { 
        String sql = "INSERT INTO Livre(titre, auteur_id, maison_id) VALUES(?, ?, ?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, livre.getTitre()); 
            stmt.setInt(2, livre.getAuteurId()); 
            stmt.setInt(3, livre.getMaisonId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage()); 
        } 
    } 
 
    
    public void update(Livre livre) { 
        String sql = "UPDATE Livre SET titre=?, auteur_id=?, maison_id=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, livre.getTitre()); 
            stmt.setInt(2, livre.getAuteurId()); 
            stmt.setInt(3, livre.getMaisonId()); 
            stmt.setInt(4, livre.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur lors de la mise à jour : " + e.getMessage()); 
        } 
    } 
 
    
    public void delete(int idLivre) { 
        String sql = "DELETE FROM Livre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idLivre); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur lors de la suppression : " + e.getMessage()); 
        } 
    } 
 
    
    public Livre findById(int idLivre) { 
        String sql = "SELECT * FROM Livre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idLivre); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new Livre(rs.getInt("id"), rs.getString("titre"), 
                                 rs.getInt("auteur_id"), rs.getInt("maison_id")); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur lors de la recherche : " + e.getMessage()); 
        } 
        return null; 
    } 
 
    @
    public List<Livre> findAll() { 
        List<Livre> livres = new ArrayList<>(); 
        String sql = "SELECT * FROM Livre"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                livres.add(new Livre(rs.getInt("id"), rs.getString("titre"), 
                                     rs.getInt("auteur_id"), rs.getInt("maison_id"))); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur lors de la récupération : " + e.getMessage()); 
        } 
        return livres; 
    } 
}





//GESTION DES EXCEPTIONS





 public void add(Livre livre) { 
    String sql = "INSERT INTO Livre(titre, auteur_id, maison_id) VALUES(?, ?, ?)"; 
    try (Connection conn = SQLiteConnection.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) { 
        stmt.setString(1, livre.getTitre()); 
        stmt.setInt(2, livre.getAuteurId()); 
        stmt.setInt(3, livre.getMaisonId()); 
        stmt.executeUpdate(); 
    } catch (SQLException e) { 
        System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage()); 
        // Ici tu gères l’exception : log, relance, etc. 
    } 
}