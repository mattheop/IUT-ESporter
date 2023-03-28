package com.example.sae.repository;

import com.example.sae.models.db.Equipe;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipeRepository extends CrudRepository<Equipe, Integer> {
    List<Equipe> findAll();

    @Query("select count(*) from equipe where ecurie_id = :e_id")
    int countEquipeByEcurieId(@Param("e_id") int ecurieId);

    @Query("""
            SELECT *
            FROM equipe
            WHERE equipe_id NOT IN (
                SELECT equipe_id FROM inscription WHERE competition_id = :id
            );
    """)
    List<Equipe> findNotInEquipe(int id);
}
