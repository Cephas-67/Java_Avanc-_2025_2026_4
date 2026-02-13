package com.bibliotheque.dao.interfaces;

import com.bibliotheque.model.Genre;
import java.util.List;

public interface GenreDAO {

    void add(Genre genre);

    Genre findById(int idGenre);

    List<Genre> findAll();
}
