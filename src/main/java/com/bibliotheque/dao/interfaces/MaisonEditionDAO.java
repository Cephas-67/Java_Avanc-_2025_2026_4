package com.bibliotheque.dao.interfaces;

import com.bibliotheque.model.MaisonEdition;
import java.util.List;

public interface MaisonEditionDAO {

    void add(MaisonEdition maisonEdition);

    MaisonEdition findById(int idMaisonEdition);

    List<MaisonEdition> findAll();
}
