package com.example.sae.services.impl;

import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.exceptions.joueur.JoueurNotOwnedException;
import com.example.sae.exceptions.joueur.JoueurShouldBeAttachedToEcurie;
import com.example.sae.exceptions.UserNotEcurieException;
import com.example.sae.models.db.Joueur;
import com.example.sae.repository.JoueurRepository;
import com.example.sae.services.AppUserService;
import com.example.sae.services.JoueurService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JoueurServiceImpl implements JoueurService {
    private JoueurRepository joueurRepository;
    private AppUserService appUserService;

    public JoueurServiceImpl(JoueurRepository joueurRepository, AppUserService appUserService) throws UserNotEcurieException {
        this.joueurRepository = joueurRepository;
        this.appUserService = appUserService;
    }

    @Override
    public Joueur find(int id) throws JoueurNotFoundException, JoueurNotOwnedException {
        Joueur joueur = joueurRepository.findById(id).orElseThrow(JoueurNotFoundException::new);

        if (joueur.getEcurie().getId() != appUserService.getManagedEcurie().getId()) {
            throw new JoueurNotOwnedException();
        }

        return joueur;
    }

    @Override
    public Joueur save(Joueur joueur) {
        if (joueur.getEcurie() == null) {
            throw new JoueurShouldBeAttachedToEcurie();
        }

        if(joueur.getEcurie().getId() != this.appUserService.getManagedEcurie().getId()){
            throw new JoueurNotOwnedException();
        }

        // Formatting Nom et prenom des joueurs
        joueur.setNom(joueur.getNom().toUpperCase());
        joueur.setPrenom(joueur.getPrenom().substring(0, 1).toUpperCase() + joueur.getPrenom().substring(1).toLowerCase());

        return joueurRepository.save(joueur);
    }

    @Override
    public void save(Joueur joueur, int ecurieId) {
        joueur.setEcurie(AggregateReference.to(ecurieId));
        save(joueur);
    }

    @Override
    public void delete(int id) throws JoueurNotOwnedException, JoueurNotFoundException {
        Joueur joueur = find(id);

        if (joueur.getEcurie().getId() != appUserService.getManagedEcurie().getId()) {
            throw new JoueurNotOwnedException();
        }

        joueurRepository.deleteById(id);
    }

    @Override
    public Collection<Joueur> findAll() {
        return joueurRepository.findAll();
    }
}
