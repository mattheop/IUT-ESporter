package com.example.sae.services;

import com.example.sae.models.db.Equipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface EquipeService {
    Equipe find(int id);

    Equipe save(Equipe equipe);

    void delete(int id);

    Collection<Equipe> findAll();

    void saveLogo(Equipe equipe, MultipartFile file);

    void removeJoueur(Equipe equipe, int idJoueur);

    void addJoueur(Equipe equipe, int idJoueur);

    String getLogoUploadPath();
}
