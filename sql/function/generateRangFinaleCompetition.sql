drop procedure if exists generate_rang_finale_competetion;

create procedure generate_rang_finale_competetion(IN p_competition_id int)
begin

UPDATE inscription, (select equipe_id, sum(points) pts, ROW_NUMBER() over (order by sum(points) DESC) as rang
                     from (SELECT equipe1 as equipe_id, sum(score_equipe1) as points
                           from rencontre
                           where competition_id = p_competition_id
                             and poule_num = 99
                           group by equipe1
                           UNION
                           SELECT equipe2 as equipe_id, sum(score_equipe2)
                           from rencontre
                           where competition_id = p_competition_id
                             and poule_num = 99
                           group by equipe2) t
                     group by equipe_id
                     order by sum(points) DESC
                     LIMIT 3) v
set inscription.rang_finale = v.rang
where inscription.equipe_id = v.equipe_id;
end;