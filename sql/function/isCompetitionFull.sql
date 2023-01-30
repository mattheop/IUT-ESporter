create or replace function is_competition_full(p_competition_id int) returns int
begin

    declare MAX_EQUIPE_PAR_COMPETITION int;
    declare nb_equipe int;

    set MAX_EQUIPE_PAR_COMPETITION := 16;

    select count(competition_id)
    into nb_equipe
    from inscription
    where competition_id = p_competition_id;

    return nb_equipe >= MAX_EQUIPE_PAR_COMPETITION;

end;