package com.example.sae.repository;

import com.example.sae.models.db.Competition;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends Repository<Competition, Integer> {

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                   `competition`.`etat_competition` AS `etat_competition`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`,
                   is_competition_full(competition_id) as 'is_full',
                   competition_participation(competition_id) as 'np_participation'
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`;
            """)
    List<Competition> findAll();

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                   `competition`.`etat_competition` AS `etat_competition`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`,
                   is_competition_full(competition_id) as 'is_full',
                   competition_participation(competition_id) as 'np_participation'
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`
            WHERE `competition`.`etat_competition` in ('QUALIFICATION', 'FINALE');
            """)
    List<Competition> findAllInProgress();

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                `competition`.`etat_competition` AS `etat_competition`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`,
                   is_competition_full(competition_id) as 'is_full',
                   competition_participation(competition_id) as 'np_participation'
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`
            WHERE competition_id = :searched_id;
            """)
    Optional<Competition> findById(@Param("searched_id") Integer searchedId);

    @Query("""
            SELECT `competition`.`competition_id` AS `competition_id`,
                   `competition`.`date_debut` AS `date_debut`,
                   `competition`.`date_fin_inscription` AS `date_fin_inscription`,
                   `competition`.`etat_competition` AS `etat_competition`,
                   `jeu`.`jeu_id` AS `jeu_jeu_id`,
                   `jeu`.`nom` AS `jeu_nom`,
                   `jeu`.`nb_joueur` AS `jeu_nb_joueur`,
                   `tournois`.`tournois_id` AS `tournois_tournois_id`,
                   `tournois`.`nom` AS `tournois_nom`,
                   `tournois`.`etenduetournois` AS `tournois_etenduetournois`,
                   `tournois`.`cashpricepoints` AS `tournois_cashpricepoints`,
                   is_competition_full(competition_id) as 'is_full',
                   competition_participation(competition_id) as 'np_participation'
            FROM `competition`
            LEFT OUTER JOIN `jeu` `jeu` ON `jeu`.`jeu_id` = `competition`.`jeu_id`
            LEFT OUTER JOIN `tournois` `tournois` ON `tournois`.`tournois_id` = `competition`.`tournois_id`
            WHERE competition.tournois_id = :searched_id;
            """)
    List<Competition> findByTournoisId(@Param("searched_id") Integer searchedId);

    @Modifying
    @Query("insert into competition (date_debut, date_fin_inscription, jeu_id, tournois_id) " +
            "values (:date_debut, :date_fin, :jeu_id, :tournois_id);")
    void insertDirect(@Param("date_debut") LocalDateTime dateDebut,
                      @Param("date_fin") LocalDateTime dateFin,
                      @Param("jeu_id") Integer jeuId,
                      @Param("tournois_id") Integer tournoisId);

}
