package com.example.sae.repository;

import com.example.sae.models.db.Joueur;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface JoueurRepository extends CrudRepository<Joueur, Integer> {

    @Query("SELECT * FROM joueur WHERE ecurie = :e_id AND id not in (select distinct joueur_id from joueur_equipe)")
    Collection<Joueur> getJoueursOwnedWithoutTeam(@Param("e_id") Integer ecurie_id);

    @Query("delete from joueur where id = :pid")
    @Modifying
    void deleteById(@Param("pid") Integer id);
}
