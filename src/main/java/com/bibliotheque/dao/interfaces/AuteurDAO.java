package com.bibliotheque.dao.interfaces;

import com.bibliotheque.model.Auteur;
import java.util.List;

public interface AuteurDAO {

    void add(Auteur auteur);

    Auteur findById(int idAuteur);

    List<Auteur> findAll();
}
