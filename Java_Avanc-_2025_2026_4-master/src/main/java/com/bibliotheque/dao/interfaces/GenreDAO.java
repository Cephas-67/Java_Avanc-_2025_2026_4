package main.java.com.bibliotheque.dao.interfaces;

import main.java.com.bibliotheque.model.Genre;
import java.util.List;

public interface GenreDAO {

    void add(Genre genre);

    void update(Genre genre);

    void delete(int idGenre);

    Genre findById(int idGenre);

    List<Genre> findAll();
}