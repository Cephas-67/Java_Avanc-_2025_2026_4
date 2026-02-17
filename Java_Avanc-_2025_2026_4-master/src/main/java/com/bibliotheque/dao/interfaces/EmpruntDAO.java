package main.java.com.bibliotheque.dao.interfaces;

import main.java.com.bibliotheque.model.Emprunt;
import java.util.List;

public interface EmpruntDAO {

    // Une seule déclaration de cette méthode
    void enregistrerEmprunt(Emprunt emprunt);

    void enregistrerRetour(int idEmprunt);

    List<Emprunt> findByMembre(int idMembre);

    List<Emprunt> findEnCours();

    List<Emprunt> findAll();
}