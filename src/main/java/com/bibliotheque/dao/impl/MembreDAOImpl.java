import   src.main.java.com.bibliotheque.dao.interfaces.MembreDAO.java;
import src.main.java.com.bibliotheque.model.Membre.java; 
 
 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class MembreDAOImpl implements MembreDAO { 
 
    
    public void add(Membre membre) { 
        String sql = "INSERT INTO Membre(nom, email) VALUES(?, ?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, membre.getNom()); 
            stmt.setString(2, membre.getEmail()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur ajout membre : " + e.getMessage()); 
        } 
    } 
 
    
    public void update(Membre membre) { 
        String sql = "UPDATE Membre SET nom=?, email=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, membre.getNom()); 
            stmt.setString(2, membre.getEmail()); 
            stmt.setInt(3, membre.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur update membre : " + e.getMessage()); 
        } 
    } 
 
    
    public void delete(int idMembre) { 
        String sql = "DELETE FROM Membre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idMembre); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur delete membre : " + e.getMessage()); 
        } 
    } 
 
    
    public Membre findById(int idMembre) { 
        String sql = "SELECT * FROM Membre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idMembre); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("email")); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur find membre : " + e.getMessage()); 
        } 
        return null; 
    } 
 
    
    public List<Membre> findAll() { 
        List<Membre> membres = new ArrayList<>(); 
        String sql = "SELECT * FROM Membre"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                membres.add(new Membre(rs.getInt("id"), rs.getString("nom"), 
rs.getString("email"))); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur findAll membres : " + e.getMessage()); 
        } 
        return membres; 
    } 
}




//GESTION DES EXCEPTIONS





public Membre findById(int idMembre) { 
    String sql = "SELECT * FROM Membre WHERE id=?"; 
    try (Connection conn = SQLiteConnection.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) { 
        stmt.setInt(1, idMembre); 
        ResultSet rs = stmt.executeQuery(); 
        if (rs.next()) { 
            return new Membre(rs.getInt("id"), rs.getString("nom"), rs.getString("email")); 
        } 
    } catch (SQLException e) { 
        System.err.println("Erreur recherche membre : " + e.getMessage()); 
        throw new RuntimeException("Impossible de trouver le membre", e); 
    } 
    return null; 
} 
