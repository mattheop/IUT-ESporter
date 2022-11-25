package com.example.sae.repository;

import com.example.sae.models.Jeu;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JeuRepository extends CrudRepository<Jeu, Integer> {
    List<Jeu> findAll();
}
