package com.example.sae.repository;

import com.example.sae.models.Tournois;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TournoisRepository extends CrudRepository<Tournois, Integer> {

    @Query("SELECT * FROM tournois WHERE tournois_id = :id")
    Tournois getTournoisById(@Param("id") Integer id);

    @Query("SELECT * FROM tournois")
    Collection<Tournois> getAllTournois();

}
