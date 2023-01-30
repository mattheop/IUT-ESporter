create or replace procedure sendNotificationToEcurie(p_ecurie_id int, p_message text)
begin

    insert into notification(ecurie_id, message) VALUES (p_ecurie_id, p_message);

end;