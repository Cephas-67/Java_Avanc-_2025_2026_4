package main.java.com.bibliotheque.service;

import main.java.com.bibliotheque.dao.impl.EmpruntDAOImpl;
import main.java.com.bibliotheque.dao.impl.LivreDAOImpl;
import main.java.com.bibliotheque.dao.impl.MembreDAOImpl;
import main.java.com.bibliotheque.model.Emprunt;
import main.java.com.bibliotheque.model.Livre;
import main.java.com.bibliotheque.model.Membre;

import java.time.LocalDate;
import java.util.List;

public class EmpruntService {

    private final EmpruntDAOImpl empruntDAO;
    private final MembreDAOImpl membreDAO;
    private final LivreDAOImpl livreDAO;

    public EmpruntService() {
        this.empruntDAO = new EmpruntDAOImpl();
        this.membreDAO = new MembreDAOImpl();
        this.livreDAO = new LivreDAOImpl();

    }

    public void enregistrerEmprunt(int idMembre, int idLivre) throws Exception {
        Membre membre = membreDAO.findById(idMembre);
        if (membre == null) {
            throw new Exception("Membre introuvable");
        }

        Livre livre = livreDAO.findById(idLivre);
        if (livre == null) {
            throw new Exception("Livre introuvable");
        }

        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetourPrevue = dateEmprunt.plusWeeks(2);

        Emprunt emprunt = new Emprunt();
        emprunt.setMembre(membre);
        emprunt.setLivre(livre);
        emprunt.setDateEmprunt(dateEmprunt);
        emprunt.setDateRetourPrevue(dateRetourPrevue);
        emprunt.setStatut("EN_COURS");

        empruntDAO.enregistrerEmprunt(emprunt);
    }

    public void enregistrerRetour(int idEmprunt) throws Exception {
        if (idEmprunt <= 0) {
            throw new Exception("ID d'emprunt invalide");
        }
        empruntDAO.enregistrerRetour(idEmprunt);
    }

    public List<Emprunt> getEmpruntsByMembre(int idMembre) {
        return empruntDAO.findByMembre(idMembre);
    }

    public List<Emprunt> getEmpruntsEnCours() {
        return empruntDAO.findEnCours();
    }

    public List<Emprunt> getAllEmprunts() {
        return empruntDAO.findAll();
    }

    public List<Emprunt> searchEmprunts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEmprunts();
        }
        return empruntDAO.search(keyword.trim());
    }

    public List<Membre> getAllMembres() {
        return membreDAO.findAll();
    }

    public List<Livre> getAllLivres() {
        return livreDAO.findAll();
    }
}