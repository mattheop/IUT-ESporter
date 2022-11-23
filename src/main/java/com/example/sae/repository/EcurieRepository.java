package com.example.sae.repository;

import com.example.sae.models.Ecurie;
import com.example.sae.models.Joueur;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface EcurieRepository extends CrudRepository<Ecurie, Integer> {

    @Query("select * from joueur where ecurie = :id")
    Collection<Joueur> getJoueurs(@Param("id") Integer ecurie_id);
}
