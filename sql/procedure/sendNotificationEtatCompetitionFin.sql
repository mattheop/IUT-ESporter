create or replace procedure sendNotificationEtatCompetitionFin(p_competition_id int)
begin

    declare done int default FALSE;
    declare cur_ecu_id int;
    declare competition_name varchar(100);

    declare cursorEcurieId cursor for
        select e.ecurie_id
        from inscription
                 left outer join equipe e on inscription.equipe_id = e.equipe_id
                 left join ecurie e2 on e.ecurie_id = e2.ecurie_id
        where inscription.competition_id = p_competition_id
          and inscription.rang_finale IS NOT NULL;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    select concat(j.nom, ' du tournois ', t.nom)
    into competition_name
    from competition
             left OUTER JOIN jeu j
                             on competition.jeu_id = j.jeu_id
             left OUTER JOIN tournois t on competition.tournois_id = t.tournois_id
    where competition.competition_id = p_competition_id;

    open cursorEcurieId;
    vLoop:
    loop
        fetch cursorEcurieId into cur_ecu_id;

        if done then
            leave vLoop;
        end if;

        call sendNotificationToEcurie(cur_ecu_id, concat(
                'La compétition de ',
                competition_name,
                ' est finie. Vous pouvez visualiser le podium sur le detail de la compétition en question.'
            ));


    end loop;
end;