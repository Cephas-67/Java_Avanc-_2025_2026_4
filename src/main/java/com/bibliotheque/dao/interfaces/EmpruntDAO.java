package com.bibliotheque.dao.interfaces;

import com.bibliotheque.model.Emprunt;
import java.util.List;

public interface EmpruntDAO {

    void enregistrerEmprunt(Emprunt emprunt);

    void enregistrerRetour(int idEmprunt);

    List<Emprunt> findByMembre(int idMembre);

    List<Emprunt> findEnCours();
}
