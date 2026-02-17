
import  src.main.java.com.bibliotheque.dao.interfaces.Emprunt.java;
import src.main.java.com.bibliotheque.model.Emprunt.java; 
  
 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class EmpruntDAOImpl implements EmpruntDAO { 
 
    
    public void add(Emprunt emprunt) { 
        String sql = "INSERT INTO Emprunt(livre_id, membre_id, date_emprunt, date_retour) 
VALUES(?, ?, ?, ?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, emprunt.getLivreId()); 
            stmt.setInt(2, emprunt.getMembreId()); 
            stmt.setString(3, emprunt.getDateEmprunt()); // format YYYY-MM-DD 
            stmt.setString(4, emprunt.getDateRetour());  // format YYYY-MM-DD 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur ajout emprunt : " + e.getMessage()); 
        } 
    } 
 
   
    public void update(Emprunt emprunt) { 
        String sql = "UPDATE Emprunt SET livre_id=?, membre_id=?, date_emprunt=?, 
date_retour=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, emprunt.getLivreId()); 
            stmt.setInt(2, emprunt.getMembreId()); 
            stmt.setString(3, emprunt.getDateEmprunt()); 
            stmt.setString(4, emprunt.getDateRetour()); 
            stmt.setInt(5, emprunt.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur update emprunt : " + e.getMessage()); 
        } 
    } 
 
    
    public void delete(int idEmprunt) { 
        String sql = "DELETE FROM Emprunt WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idEmprunt); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur delete emprunt : " + e.getMessage()); 
        } 
    } 
 
    
    public Emprunt findById(int idEmprunt) { 
        String sql = "SELECT * FROM Emprunt WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idEmprunt); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new Emprunt( 
                        rs.getInt("id"), 
                        rs.getInt("livre_id"), 
                        rs.getInt("membre_id"), 
                        rs.getString("date_emprunt"), 
                        rs.getString("date_retour") 
                ); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur find emprunt : " + e.getMessage()); 
        } 
        return null; 
    } 
 
    
    public List<Emprunt> findAll() { 
        List<Emprunt> emprunts = new ArrayList<>(); 
        String sql = "SELECT * FROM Emprunt"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                emprunts.add(new Emprunt( 
                        rs.getInt("id"), 
                        rs.getInt("livre_id"), 
                        rs.getInt("membre_id"), 
                        rs.getString("date_emprunt"), 
                        rs.getString("date_retour") 
                )); 
            } 
        } catch (SQLException e) { 
System.err.println("Erreur findAll emprunts : " + e.getMessage()); 
} 
return emprunts; 
} 
}





//GESTION DE L'EMPRUNT


public void emprunterLivre(Emprunt emprunt) { 
    String sqlEmprunt = "INSERT INTO Emprunt(livre_id, membre_id, date_emprunt, (date_retour) VALUES(?, ?, ?, ?)\";
    String sqlUpdateLivre = "UPDATE Livre SET disponible = 0 WHERE id=?"; 
 
    try (Connection conn = SQLiteConnection.getConnection()) { 
        conn.setAutoCommit(false); // début transaction 
 
        try (PreparedStatement stmt1 = conn.prepareStatement(sqlEmprunt); 
             PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateLivre)) { 
 
            // Insertion emprunt 
            stmt1.setInt(1, emprunt.getLivreId()); 
            stmt1.setInt(2, emprunt.getMembreId()); 
            stmt1.setString(3, emprunt.getDateEmprunt()); 
            stmt1.setString(4, emprunt.getDateRetour()); 
            stmt1.executeUpdate(); 
 
            // Mise à jour livre 
            stmt2.setInt(1, emprunt.getLivreId()); 
            stmt2.executeUpdate(); 
 
            conn.commit(); // valider la transaction 
        } catch (SQLException e) { 
            conn.rollback(); // annuler si erreur 
            System.err.println("Erreur transaction emprunt : " + e.getMessage()); 
        } 
    } catch (SQLException e) { 
        System.err.println("Erreur connexion : " + e.getMessage()); 
    } 
}  