package com.example.sae.services.impl;

import com.example.sae.exceptions.equipe.EquipeNotFoundException;
import com.example.sae.exceptions.equipe.EquipeNotOwnedException;
import com.example.sae.exceptions.equipe.EquipeUploadLogoException;
import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.models.db.Equipe;
import com.example.sae.models.ref.JoueurRef;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.services.AppUserService;
import com.example.sae.services.EquipeService;
import com.example.sae.services.JoueurService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EquipeServiceImpl implements EquipeService {
    private static final String UPLOAD_EQUIPE_LOGO_FOLDER = System.getProperty("user.dir") + "/uploads/equipes_logo/";
    private EquipeRepository equipeRepository;
    private AppUserService appUserService;
    private JoueurService joueurService;

    public EquipeServiceImpl(EquipeRepository equipeRepository, AppUserService appUserService, JoueurService joueurService) {
        this.equipeRepository = equipeRepository;
        this.appUserService = appUserService;
        this.joueurService = joueurService;
    }

    @Override
    public Equipe find(int id) throws EquipeNotFoundException {
        return this.equipeRepository.findById(id).orElseThrow(EquipeNotFoundException::new);
    }

    @Override
    public Equipe save(Equipe equipe) throws EquipeNotOwnedException {
        if (equipe.getEcurie() == null || this.appUserService.getManagedEcurie().getId() != equipe.getEcurie().getId()) {
            throw new EquipeNotOwnedException();
        }

        return equipeRepository.save(equipe);
    }

    @Override
    public void delete(int id) throws EquipeNotOwnedException {
        Equipe equipe = find(id);

        if (equipe.getEcurie() == null || this.appUserService.getManagedEcurie().getId() != equipe.getEcurie().getId()) {
            throw new EquipeNotOwnedException();
        }

        equipeRepository.deleteById(id);
    }

    @Override
    public Collection<Equipe> findAll() {
        return equipeRepository.findAll();
    }

    @Override
    public void removeJoueur(Equipe equipe, int idJoueur) {
        Set<JoueurRef> oldComposition = equipe.getJoueursIds();

        equipe.setJoueursIds(oldComposition.stream()
                .filter(joueurRef -> joueurRef.getJoueurId() != idJoueur)
                .collect(Collectors.toSet()));

        save(equipe);
    }

    @Override
    public void addJoueur(Equipe equipe, int idJoueur) throws JoueurNotFoundException {
        joueurService.find(idJoueur);
        equipe.addJoueur(new JoueurRef(idJoueur));
        save(equipe);
    }

    @Override
    public void saveLogo(Equipe equipe, MultipartFile multipartFile) throws EquipeUploadLogoException {
        String fileName = equipe.getId() + "-eid@" + equipe.getEcurie().getId() + "_" + equipe.getNom().replaceAll(" ", "_") + "." + Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
            Files.write(Paths.get(UPLOAD_EQUIPE_LOGO_FOLDER + fileName), bytes);
        } catch (IOException e) {
            throw new EquipeUploadLogoException();
        }

        equipe.setLogoFileName(fileName);
        this.equipeRepository.save(equipe);
    }

    @Override
    public String getLogoUploadPath() {
        return UPLOAD_EQUIPE_LOGO_FOLDER;
    }
}
