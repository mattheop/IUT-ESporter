drop procedure if exists generate_rencontre_from_competition_and_poule;

create procedure generate_rencontre_from_competition_and_poule(IN p_competition_id int, IN p_poule_num int)
begin

    # Duplicate key
    DECLARE EXIT HANDLER FOR 1062
        BEGIN
            ROLLBACK;
            signal sqlstate '45002'
            set message_text = 'Les rencontres pour cette poule et cette competion sont deja genere.';
        END;

    insert into rencontre(competition_id, equipe1, equipe2, date_rencontre, poule_num)
    select p_competition_id,
           i.equipe_id,
           i2.equipe_id,
           date_add(c.date_debut, INTERVAL (ROW_NUMBER() over ()) - 1 HOUR) as "Date du match",
           p_poule_num
    from v_inscription_with_poule i
             join v_inscription_with_poule i2 on i.inscription_id < i2.inscription_id
        and i2.competition_id = p_competition_id
        and i2.poule_num = p_poule_num
             join competition c on i.competition_id = c.competition_id
    where i.competition_id = p_competition_id
      and i.poule_num = p_poule_num;
end;