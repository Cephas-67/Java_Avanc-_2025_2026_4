package main.java.com.bibliotheque.service;

import main.java.com.bibliotheque.dao.impl.MembreDAOImpl;
import main.java.com.bibliotheque.dao.interfaces.MembreDAO;
import main.java.com.bibliotheque.model.Membre;

import java.util.List;

/**
 * Service pour la gestion des membres avec logique métier
 */
public class MembreService {

    private final MembreDAO membreDAO;

    public MembreService() {
        this.membreDAO = new MembreDAOImpl();
    }

    /**
     * Ajouter un nouveau membre avec validation
     */
    public void addMembre(Membre membre) throws Exception {
        validateMembre(membre);
        membreDAO.add(membre);
    }

    /**
     * Modifier un membre existant
     */
    public void updateMembre(Membre membre) throws Exception {
        if (membre.getIdMembre() <= 0) {
            throw new Exception("ID du membre invalide");
        }
        validateMembre(membre);
        membreDAO.update(membre);
    }

    /**
     * Supprimer un membre
     */
    public void deleteMembre(int idMembre) throws Exception {
        if (idMembre <= 0) {
            throw new Exception("ID du membre invalide");
        }
        membreDAO.delete(idMembre);
    }

    /**
     * Rechercher un membre par ID
     */
    public Membre findById(int idMembre) {
        return membreDAO.findById(idMembre);
    }

    /**
     * Récupérer tous les membres
     */
    public List<Membre> getAllMembres() {
        return membreDAO.findAll();
    }

    /**
     * Rechercher des membres par mot-clé
     */
    public List<Membre> searchMembres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllMembres();
        }
        return membreDAO.search(keyword.trim());
    }

    /**
     * Valider les données d'un membre
     */
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

        if (membre.getEmail() == null || membre.getEmail().trim().isEmpty()) {
            throw new Exception("L'email est obligatoire");
        }

        if (!isValidEmail(membre.getEmail())) {
            throw new Exception("Format d'email invalide");
        }

        if (membre.getTelephone() != null && !membre.getTelephone().trim().isEmpty()) {
            if (!isValidPhoneNumber(membre.getTelephone())) {
                throw new Exception("Format de téléphone invalide");
            }
        }

        if (membre.getPenalite() < 0) {
            throw new Exception("La pénalité ne peut pas être négative");
        }
    }

    /**
     * Valider le format d'email
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Valider le format du numéro de téléphone
     */
    private boolean isValidPhoneNumber(String phone) {
        // Accepte les formats: +XXX, 0XXXXXXXXX, XX XX XX XX XX, etc.
        String phoneRegex = "^[+]?[0-9]{8,15}$|^[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}\\s[0-9]{2}$";
        String cleanPhone = phone.replaceAll("\\s+", "");
        return cleanPhone.matches(phoneRegex);
    }
}
