create or replace trigger AI_RENCONTRE_AJOUT_GAGNANT_FINALE
    after update
    on rencontre
    for each row
begin

    declare winner_equipe_id int;
    declare winner_inscription_id int;
    declare competition_name varchar(100);

    if (is_poule_finished(NEW.competition_id, NEW.poule_num)
        and NEW.poule_num <> 99) then
        select equipe_id
        into winner_equipe_id
        from (SELECT equipe1 as equipe_id, sum(score_equipe1) as points
              from rencontre
              where competition_id = NEW.competition_id
                and poule_num = NEW.poule_num
              group by equipe1
              UNION
              SELECT equipe2 as equipe_id, sum(score_equipe2)
              from rencontre
              where competition_id = NEW.competition_id
                and poule_num = NEW.poule_num
              group by equipe2) t
        group by equipe_id
        order by sum(points) DESC
        LIMIT 1;

        select inscription_id
        into winner_inscription_id
        from inscription
        where competition_id = NEW.competition_id
          and equipe_id = winner_equipe_id;

        insert into inscription_poule(inscription_id,
                                      is_final,
                                      poule_num)
        values (winner_inscription_id,
                1,
                99);

        select concat(j.nom, ' du tournois ', t.nom) into competition_name
        from competition
            left OUTER JOIN jeu j
        on competition.jeu_id = j.jeu_id
            left OUTER JOIN tournois t on competition.tournois_id = t.tournois_id
        where competition.competition_id = NEW.competition_id;


        call sendNotificationToEquipe(winner_equipe_id, concat(
            'Félicitation! Vous avez été selectioné pour participer à la finale de ',
            competition_name,
            '. Vous pouvez visualiser la poule finale sur le detail de la compétition'
            ));
    end if;
end;

