create or replace trigger AI_RENCONTRE_CHANGEMENT_ETAT_COMPETITION
    after update
    on rencontre
    for each row
begin

    declare curr_etat_competition VARCHAR(40);

    select etat_competition into curr_etat_competition
    from competition
    where competition.competition_id = NEW.competition_id;

    if (curr_etat_competition = 'QUALIFICATION') then
        if (is_poule_finished(NEW.competition_id, 1) and
            is_poule_finished(NEW.competition_id, 2) and
            is_poule_finished(NEW.competition_id, 3) and
            is_poule_finished(NEW.competition_id, 4)
            ) then

            update competition
            set etat_competition = 'FINALE'
            where competition.competition_id = NEW.competition_id;
        end if;
    end if;

    if (curr_etat_competition = 'FINALE') then
        if (is_poule_finished(NEW.competition_id, 99)
            ) then

            update competition
            set etat_competition = 'FIN'
            where competition.competition_id = NEW.competition_id;

            call generate_rang_finale_competetion(NEW.competition_id);
            call sendNotificationEtatCompetitionFin(NEW.competition_id);
        end if;
    end if;

end;

