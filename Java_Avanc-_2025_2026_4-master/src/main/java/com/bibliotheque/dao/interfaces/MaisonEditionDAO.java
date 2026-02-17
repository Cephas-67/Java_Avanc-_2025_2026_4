package main.java.com.bibliotheque.dao.interfaces;

import main.java.com.bibliotheque.model.MaisonEdition;
import java.util.List;

public interface MaisonEditionDAO {

    void add(MaisonEdition maisonEdition);

    void update(MaisonEdition maisonEdition);

    void delete(int idMaisonEdition);

    MaisonEdition findById(int idMaisonEdition);

    List<MaisonEdition> findAll();
}