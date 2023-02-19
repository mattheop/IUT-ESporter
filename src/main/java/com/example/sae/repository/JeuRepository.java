package com.example.sae.repository;

import com.example.sae.models.db.Jeu;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JeuRepository extends CrudRepository<Jeu, Integer> {
    List<Jeu> findAll();

    @Query("""
            SELECT *
            FROM `jeu`
            WHERE `jeu`.`jeu_id` NOT IN (
                SELECT `competition`.`jeu_id`
                FROM `competition`
                WHERE `competition`.`tournois_id` = :tournoi_id
            );
            """)
    List<Jeu> findJeuNotInTournois(@Param("tournoi_id") Integer tournoiId);
}
