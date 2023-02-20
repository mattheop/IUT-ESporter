package com.example.sae.repository;

import com.example.sae.models.db.Equipe;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipeRepository extends CrudRepository<Equipe, Integer> {
    List<Equipe> findAll();

    @Query("""
            SELECT *
            FROM equipe
            WHERE equipe_id NOT IN (
                SELECT equipe_id FROM inscription WHERE competition_id = :id
            );
    """)
    List<Equipe> findNotInEquipe(int id);
}
