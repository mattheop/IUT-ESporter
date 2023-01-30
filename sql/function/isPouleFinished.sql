create or replace function is_poule_finished(p_competition_id int, p_poule_num int) returns int
begin

    declare nb_non_arbitre int;

    select sum(IF(score_equipe1 is null or score_equipe2 is null, 1, 0)) as nb_match_non_arbitre into nb_non_arbitre
    from rencontre
    where competition_id = p_competition_id
      and poule_num = p_poule_num;

    return nb_non_arbitre = 0;

end;