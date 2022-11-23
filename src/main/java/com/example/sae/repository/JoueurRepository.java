package com.example.sae.repository;

import com.example.sae.models.Joueur;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface JoueurRepository extends CrudRepository<Joueur, Integer> {
}
