package com.example.sae.repository;

import com.example.sae.models.db.Rencontre;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface RencontreRepository extends Repository<Rencontre, Integer> {

    @Query("""
            SELECT `rencontre`.`rencontre_id`   AS `rencontre_id`,
                   `rencontre`.`competition_id` AS `competition_id`,
                   `rencontre`.`poule_num`      AS `poule_num`,
                   `rencontre`.`score_equipe1`  AS `score_equipe1`,
                   `rencontre`.`score_equipe2`  AS `score_equipe2`,
                   `rencontre`.`date_rencontre` AS `date_rencontre`,
                   `equipe1`.`equipe_id`        AS `equipe1_equipe_id`,
                   `equipe2`.`equipe_id`        AS `equipe2_equipe_id`,
                   `equipe2`.`nom`              AS `equipe2_nom`,
                   `equipe1`.`nom`              AS `equipe1_nom`,
                   `equipe1`.`ecurie_id`        AS `equipe1_ecurie_id`,
                   `equipe2`.`ecurie_id`        AS `equipe2_ecurie_id`,
                   `equipe1`.`jeu_spe`          AS `equipe1_jeu_spe`,
                   `equipe2`.`jeu_spe`          AS `equipe2_jeu_spe`,
                   `equipe1`.`logo_file_name`   AS `equipe1_logo_file_name`,
                   `equipe2`.`logo_file_name`   AS `equipe2_logo_file_name`
            FROM   `rencontre`
                   LEFT OUTER JOIN `equipe` `equipe1`
                                ON `equipe1`.`equipe_id` = `rencontre`.`equipe1`
                   LEFT OUTER JOIN `equipe` `equipe2`
                                ON `equipe2`.`equipe_id` = `rencontre`.`equipe2`
            where `rencontre`.`competition_id` = :pcid
            order by `rencontre`.`poule_num`, `rencontre`.`date_rencontre`
            """)
    Collection<Rencontre> findAllByCompetitonId(@Param("pcid") Integer pcid);

    @Modifying
    @Query("""
            UPDATE rencontre SET score_equipe1 = :pEquipe1, score_equipe2 = :pEquipe2 where rencontre_id = :pRencontreId;
                        """)
    int updateScore(@Param("pRencontreId") int rencontreId,
                    @Param("pEquipe1") int equipe1,
                    @Param("pEquipe2") int equipe2);

    @Query("SELECT count(*) FROM rencontre WHERE score_equipe1 is null OR score_equipe2 is null")
    int countRencontreByCompetition();
}
