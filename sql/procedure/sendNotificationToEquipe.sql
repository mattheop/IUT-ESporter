create or replace procedure sendNotificationToEquipe(p_equipe_id int, p_message text)
begin

    insert into notification(ecurie_id, message) VALUES ((select equipe.ecurie_id from equipe where equipe_id = p_equipe_id), p_message);

end;