package com.example.sae.repository;

import com.example.sae.models.Inscription;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface InscriptionRepository extends CrudRepository<Inscription, Integer> {

    Collection<Inscription> findAll();
}
