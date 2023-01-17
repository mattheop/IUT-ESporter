package com.example.sae.repository;

import com.example.sae.models.db.Poule;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PouleRepository extends CrudRepository<Poule, Integer> {


    @Query("""
            select  inscription_poule.*,
                    e2.points   AS `ecurie_points`,
                    e.equipe_id AS `equipe_equipe_id`,
                    e.nom       AS `equipe_nom`,
                    e.equipe_id AS `equipe_ecurie_id`,
                    e.jeu_spe   AS `equipe_jeu_spe`
            from inscription_poule
                left join inscription i on i.inscription_id = inscription_poule.inscription_id
                left join equipe e on i.equipe_id = e.equipe_id
                left join ecurie e2 on e.ecurie_id = e2.ecurie_id;
                        """)
    Collection<Poule> findAll();

    @Query("""
            select  inscription_poule.*,
                    e2.points   AS `ecurie_points`,
                    e.equipe_id AS `equipe_equipe_id`,
                    e.nom       AS `equipe_nom`,
                    e.equipe_id AS `equipe_ecurie_id`,
                    e.jeu_spe   AS `equipe_jeu_spe`
            from inscription_poule
                left join inscription i on i.inscription_id = inscription_poule.inscription_id
                left join equipe e on i.equipe_id = e.equipe_id
                left join ecurie e2 on e.ecurie_id = e2.ecurie_id
            where i.competition_id = :comp_id;
                        """)
    Collection<Poule> findAllByCompetitionId(@Param("comp_id") int competitionId);
}
