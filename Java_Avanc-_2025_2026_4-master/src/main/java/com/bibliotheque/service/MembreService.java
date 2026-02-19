package main.java.com.bibliotheque.service;

import main.java.com.bibliotheque.dao.impl.MembreDAOImpl;
import main.java.com.bibliotheque.dao.interfaces.MembreDAO;
import main.java.com.bibliotheque.model.Membre;

import java.util.List;

public class MembreService {

    private final MembreDAOImpl membreDAO;

    public MembreService() {
        this.membreDAO = new MembreDAOImpl();
    }

    public void addMembre(Membre membre) throws Exception {
        validateMembre(membre);
        membreDAO.add(membre);
    }

    public void updateMembre(Membre membre) throws Exception {
        if (membre.getIdMembre() <= 0) {
            throw new Exception("ID du membre invalide");
        }
        validateMembre(membre);
        membreDAO.update(membre);
    }

    public void deleteMembre(int idMembre) throws Exception {
        if (idMembre <= 0) {
            throw new Exception("ID du membre invalide");
        }
        membreDAO.delete(idMembre);
    }

    public Membre findById(int idMembre) {
        return membreDAO.findById(idMembre);
    }

    public List<Membre> getAllMembres() {
        return membreDAO.findAll();
    }

    public List<Membre> searchMembres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllMembres();
        }
        return membreDAO.search(keyword.trim());
    }

    private void validateMembre(Membre membre) throws Exception {
        if (membre == null) {
            throw new Exception("Le membre ne peut pas être null");
        }
        if (membre.getNom() == null || membre.getNom().trim().isEmpty()) {
            throw new Exception("Le nom est obligatoire");
        }
        if (membre.getPrenom() == null || membre.getPrenom().trim().isEmpty()) {
            throw new Exception("Le prénom est obligatoire");
        }
    }
}