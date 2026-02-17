import src.main.java.com.bibliotheque.dao.interfaces.GenreDAO.java;
import src.main.java.com.bibliotheque.model.Genre.java; 
 

 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
 
public class GenreDAOImpl implements GenreDAO { 
 
    
    public void add(Genre genre) { 
        String sql = "INSERT INTO Genre(nom) VALUES(?)"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, genre.getNom()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur ajout genre : " + e.getMessage()); 
        } 
    } 
 
    
    public void update(Genre genre) { 
        String sql = "UPDATE Genre SET nom=? WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setString(1, genre.getNom()); 
            stmt.setInt(2, genre.getId()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur update genre : " + e.getMessage()); 
        } 
    } 
 
    
    public void delete(int idGenre) { 
        String sql = "DELETE FROM Genre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idGenre); 
            stmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.err.println("Erreur delete genre : " + e.getMessage()); 
        } 
    } 
 
    
    public Genre findById(int idGenre) { 
        String sql = "SELECT * FROM Genre WHERE id=?"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, idGenre); 
            ResultSet rs = stmt.executeQuery(); 
            if (rs.next()) { 
                return new Genre(rs.getInt("id"), rs.getString("nom")); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur find genre : " + e.getMessage()); 
        } 
        return null; 
    } 
 
    
    public List<Genre> findAll() { 
        List<Genre> genres = new ArrayList<>(); 
        String sql = "SELECT * FROM Genre"; 
        try (Connection conn = SQLiteConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                genres.add(new Genre(rs.getInt("id"), rs.getString("nom"))); 
            } 
        } catch (SQLException e) { 
            System.err.println("Erreur findAll genres : " + e.getMessage()); 
        } 
        return genres; 
    } 
}







//GESTION DES EXCEPTION





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
