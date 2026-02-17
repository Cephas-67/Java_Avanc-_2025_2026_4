package main.java.com.bibliotheque.dao.interfaces;

import main.java.com.bibliotheque.model.Auteur;
import java.util.List;

public interface AuteurDAO {

    void add(Auteur auteur);

    void update(Auteur auteur);

    void delete(int idAuteur);

    Auteur findById(int idAuteur);

    List<Auteur> findAll();
}