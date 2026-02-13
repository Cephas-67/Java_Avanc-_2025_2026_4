package com.bibliotheque.dao.interfaces;

import com.bibliotheque.model.Membre;
import java.util.List;

public interface MembreDAO {

    void add(Membre membre);

    void update(Membre membre);

    void delete(int idMembre);

    Membre findById(int idMembre);

    List<Membre> findAll();

    List<Membre> search(String keyword);
}
