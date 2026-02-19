package main.java.com.bibliotheque.service;

import main.java.com.bibliotheque.dao.impl.LivreDAOImpl;
import main.java.com.bibliotheque.model.Livre;

import java.util.List;

public class LivreService {

    private final LivreDAOImpl livreDAO;

    public LivreService() {
        this.livreDAO = new LivreDAOImpl();
    }

    public void addLivre(Livre livre) throws Exception {
        validateLivre(livre);
        livreDAO.add(livre);
    }

    public void updateLivre(Livre livre) throws Exception {
        if (livre.getIdLivre() <= 0) {
            throw new Exception("ID du livre invalide");
        }
        validateLivre(livre);
        livreDAO.update(livre);
    }

    public void deleteLivre(int idLivre) throws Exception {
        if (idLivre <= 0) {
            throw new Exception("ID du livre invalide");
        }
        livreDAO.delete(idLivre);
    }

    public Livre findById(int idLivre) {
        return livreDAO.findById(idLivre);
    }

    public List<Livre> getAllLivres() {
        return livreDAO.findAll();
    }

    public List<Livre> searchLivres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllLivres();
        }
        return livreDAO.search(keyword.trim());
    }

    private void validateLivre(Livre livre) throws Exception {
        if (livre == null) {
            throw new Exception("Le livre ne peut pas être null");
        }
        if (livre.getTitre() == null || livre.getTitre().trim().isEmpty()) {
            throw new Exception("Le titre est obligatoire");
        }
        if (livre.getAnneePublication() <= 0) {
            throw new Exception("L'année doit être positive");
        }
    }
}