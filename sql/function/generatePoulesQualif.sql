drop procedure if exists generate_poules_qualif;

create procedure generate_poules_qualif(p_competion_id int)
begin
    declare done int default FALSE;

    declare cur_inscription_id int;
    declare cur_generated_poule_num int;

    declare curInscriptionWithPoules cursor for
        select inscription.inscription_id, MOD(ROW_NUMBER() over (order by points) - 1, 4) + 1 as pouleNum
        from inscription
                 inner join equipe e on inscription.equipe_id = e.equipe_id
                 inner join ecurie e2 on e.ecurie_id = e2.ecurie_id
        where competition_id = p_competion_id
        order by pouleNum, points;


    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    # On verifie le nombre inscription

    if is_competition_full(p_competion_id) = FALSE then
        signal sqlstate '45001'
            set message_text = 'Generation des poules impossible si les inscription != 16';
    end if;

    open curInscriptionWithPoules;
    vLoop:
    loop
        fetch curInscriptionWithPoules into cur_inscription_id, cur_generated_poule_num;

        if done then
            leave vLoop;
        end if;

        insert into inscription_poule(inscription_id,
                                      poule_num)
        values (cur_inscription_id,
                cur_generated_poule_num)
        on duplicate key update poule_num=cur_generated_poule_num;

    end loop;

    call sendNotificationEtatCompetitionQualification(p_competion_id);

end;