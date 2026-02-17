package main.java.com.bibliotheque.dao.interfaces;

import main.java.com.bibliotheque.model.Livre;
import java.util.List;

public interface LivreDAO {
    void add(Livre livre);
    void update(Livre livre);
    void delete(int idLivre);
    Livre findById(int idLivre);
    List<Livre> findAll();
}