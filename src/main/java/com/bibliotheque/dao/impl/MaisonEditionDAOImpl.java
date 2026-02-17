import   src.main.java.com.bibliotheque.dao.interfaces.MaisonEditionDAO.java;
import src.main.java.com.bibliotheque.model.MaisonEdition.java; 
 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class MaisonEditionDAOImpl implements MaisonEditionDAO { 
 
   
    public void add(MaisonEdition maison) { 
        String sql = "INSERT INTO MaisonEdition(nom, adresse) VALUES(?, ?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, maison.getNom()); 
            stmt.setString(2, maison.getAdresse()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur ajout maison : " + e.getMessage()); 
        } 
    } 
 
    public void update(MaisonEdition maison) { 
        String sql = "UPDATE MaisonEdition SET nom=?, adresse=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, maison.getNom()); 
            stmt.setString(2, maison.getAdresse()); 
            stmt.setInt(3, maison.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur update maison : " + e.getMessage()); 
        } 
    } 
 
   
    public void delete(int idMaison) { 
        String sql = "DELETE FROM MaisonEdition WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idMaison); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur delete maison : " + e.getMessage()); 
        } 
    } 
 
    
    public MaisonEdition findById(int idMaison) { 
        String sql = "SELECT * FROM MaisonEdition WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idMaison); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new MaisonEdition(rs.getInt("id"), rs.getString("nom"), 
rs.getString("adresse")); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur find maison : " + e.getMessage()); 
        } 
        return null; 
    } 
 
   
    public List<MaisonEdition> findAll() { 
        List<MaisonEdition> maisons = new ArrayList<>(); 
        String sql = "SELECT * FROM MaisonEdition"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                maisons.add(new MaisonEdition(rs.getInt("id"), rs.getString("nom"), 
rs.getString("adresse"))); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur findAll maisons : " + e.getMessage()); 
        } 
        return maisons; 
    } 
}









//GESTION DES EXCEPTIONS


public void update(MaisonEdition maison) { 
    String sql = "UPDATE MaisonEdition SET nom=?, adresse=? WHERE id=?"; 
    try (Connection conn = SQLiteConnection.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) { 
        stmt.setString(1, maison.getNom()); 
        stmt.setString(2, maison.getAdresse()); 
        stmt.setInt(3, maison.getId()); 
        stmt.executeUpdate(); 
    } catch (SQLException e) { 
        System.err.println("Erreur update maison : " + e.getMessage()); 
        throw new RuntimeException("Impossible de mettre à jour la maison d'édition", e); 
    } 
} 
