package com.example.sae.repository;

import com.example.sae.models.Joueur;
import org.springframework.data.repository.CrudRepository;

public interface JoueurRepository extends CrudRepository<Joueur, Integer> {
}
