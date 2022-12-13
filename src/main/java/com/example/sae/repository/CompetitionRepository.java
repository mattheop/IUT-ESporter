package com.example.sae.repository;

import com.example.sae.models.Competition;
import com.example.sae.models.Tournois;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition, Integer> {

    List<Competition> findAll();

    //@Query("SELECT * from tournois WHERE tournois_id = :tournois_id")
    //List<Competition> getCompetitionByTournois(@Param("tournois_id") Integer tournois_id);
}
