package com.example.sae.repository;

import com.example.sae.models.Competition;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition, Integer> {

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`;
            """)
    List<Competition> findAll();

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`
            WHERE competition.tournois_id = :searched_id;
            """)
    List<Competition> findByTournoisId(@Param("searched_id") Integer searched_id);

    @Modifying
    @Query("insert into competition (date_debut, date_fin_inscription, jeu_id, tournois_id) " +
            "values (:date_debut, :date_fin, :jeu_id, :tournois_id);")
    void insertDirect(@Param("date_debut") LocalDateTime date_debut,
                      @Param("date_fin") LocalDateTime date_fin,
                      @Param("jeu_id") Integer jeu_id,
                      @Param("tournois_id") Integer tournois_id);

    //@Query("SELECT * from tournois WHERE tournois_id = :tournois_id")
    //List<Competition> getCompetitionByTournois(@Param("tournois_id") Integer tournois_id);
}
