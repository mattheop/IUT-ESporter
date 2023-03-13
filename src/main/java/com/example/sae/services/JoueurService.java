package com.example.sae.services;

import com.example.sae.models.db.Joueur;

import java.util.Collection;

public interface JoueurService {
    Joueur find(int id);
    Joueur save(Joueur joueur);
    void delete(int id);
    Collection<Joueur> findAll();
}
