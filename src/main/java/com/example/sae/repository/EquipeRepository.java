package com.example.sae.repository;

import com.example.sae.models.Equipe;
import com.example.sae.models.Joueur;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface EquipeRepository extends CrudRepository<Equipe, Integer> {
}
