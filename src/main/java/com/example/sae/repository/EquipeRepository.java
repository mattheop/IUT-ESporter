package com.example.sae.repository;

import com.example.sae.models.Equipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipeRepository extends CrudRepository<Equipe, Integer> {
    List<Equipe> findAll();
}
