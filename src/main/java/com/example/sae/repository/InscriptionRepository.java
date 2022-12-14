package com.example.sae.repository;

import com.example.sae.models.Inscription;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface InscriptionRepository extends CrudRepository<Inscription, Integer> {

    @Query("""
            SELECT
              `inscription`.`inscription_id` AS `inscription_id`,
              `equipe`.`equipe_id` AS `equipe_equipe_id`,
              `equipe`.`nom` AS `equipe_nom`,
              `equipe`.`ecurie_id` AS `equipe_ecurie_id`,
              `equipe`.`jeu_spe` AS `equipe_jeu_spe`,
              `competition`.`competition_id` AS `competition_competition_id`,
              `competition`.`date_debut` AS `competition_date_debut`,
              `competition`.`date_fin_inscription` AS `competition_date_fin_inscription`,
              `competition_jeu`.`jeu_id` AS `competition_jeu_jeu_id`,
              `competition_jeu`.`nom` AS `competition_jeu_nom`,
              `competition_jeu`.`nb_joueur` AS `competition_jeu_nb_joueur`,
              `competition_tournois`.`tournois_id` AS `competition_tournois_tournois_id`,
              `competition_tournois`.`nom` AS `competition_tournois_nom`,
              `competition_tournois`.`cashpricepoints` AS `competition_tournois_cashpricepoints`,
              `competition_tournois`.`etenduetournois` AS `competition_tournois_etenduetournois`
            FROM `inscription`
              LEFT OUTER JOIN `equipe` `equipe` ON `equipe`.`equipe_id` = `inscription`.`equipe_id`
              LEFT OUTER JOIN `competition` `competition` ON `competition`.`competition_id` = `inscription`.`competition_id`
              LEFT OUTER JOIN `jeu` `competition_jeu` ON `competition_jeu`.`jeu_id` = `competition`.`jeu_id`
              LEFT OUTER JOIN `tournois` `competition_tournois` ON `competition_tournois`.`tournois_id` = `competition`.`tournois_id`

            """)
    List<Inscription> findAll();

    @Query("""
            SELECT
              `inscription`.`inscription_id` AS `inscription_id`,
              `equipe`.`equipe_id` AS `equipe_equipe_id`,
              `equipe`.`nom` AS `equipe_nom`,
              `equipe`.`ecurie_id` AS `equipe_ecurie_id`,
              `equipe`.`jeu_spe` AS `equipe_jeu_spe`,
              `competition`.`competition_id` AS `competition_competition_id`,
              `competition`.`date_debut` AS `competition_date_debut`,
              `competition`.`date_fin_inscription` AS `competition_date_fin_inscription`,
              `competition_jeu`.`jeu_id` AS `competition_jeu_jeu_id`,
              `competition_jeu`.`nom` AS `competition_jeu_nom`,
              `competition_jeu`.`nb_joueur` AS `competition_jeu_nb_joueur`,
              `competition_tournois`.`tournois_id` AS `competition_tournois_tournois_id`,
              `competition_tournois`.`nom` AS `competition_tournois_nom`,
              `competition_tournois`.`cashpricepoints` AS `competition_tournois_cashpricepoints`,
              `competition_tournois`.`etenduetournois` AS `competition_tournois_etenduetournois`
            FROM `inscription`
              LEFT OUTER JOIN `equipe` `equipe` ON `equipe`.`equipe_id` = `inscription`.`equipe_id`
              LEFT OUTER JOIN `competition` `competition` ON `competition`.`competition_id` = `inscription`.`competition_id`
              LEFT OUTER JOIN `jeu` `competition_jeu` ON `competition_jeu`.`jeu_id` = `competition`.`jeu_id`
              LEFT OUTER JOIN `tournois` `competition_tournois` ON `competition_tournois`.`tournois_id` = `competition`.`tournois_id`
                WHERE competition.tournois_id = :searched_id;
            """)
    List<Inscription> findAllByTournoisId(@Param("searched_id") Integer searched_id);


    @Modifying
    @Query("insert into inscription (competition_id, equipe_id) values (:cid, :eid);")
    void insertDirect(@Param("cid") Integer cid, @Param("eid") Integer eid);
}
