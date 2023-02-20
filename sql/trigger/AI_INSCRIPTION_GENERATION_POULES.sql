create or replace trigger AI_INSCRIPTION_GENERATION_POULES
    after insert
    on inscription
    for each row
begin

    declare competition_name varchar(100);
    select concat(j.nom, ' du tournois ', t.nom)
    into competition_name
    from competition
             left OUTER JOIN jeu j
                             on competition.jeu_id = j.jeu_id
             left OUTER JOIN tournois t on competition.tournois_id = t.tournois_id
    where competition.competition_id = NEw.competition_id;

    call sendNotificationToEquipe(NEW.equipe_id,
                                  concat('Vous venez de vous inscrire à la compétition de ',
                                         competition_name, '.'));

    if is_competition_full(NEW.competition_id) then
        call generate_poules_qualif(NEW.competition_id);

        update competition
        set etat_competition = 'QUALIFICATION'
        where competition.competition_id = NEW.competition_id;

        call generate_rencontre_from_competition_and_poule(NEW.competition_id, 1);
        call generate_rencontre_from_competition_and_poule(NEW.competition_id, 2);
        call generate_rencontre_from_competition_and_poule(NEW.competition_id, 3);
        call generate_rencontre_from_competition_and_poule(NEW.competition_id, 4);
    end if;
end;

