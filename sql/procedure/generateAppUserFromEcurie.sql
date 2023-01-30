create procedure generate_app_user()
begin
    declare done int default FALSE;
    declare cursor_ecurie_id int;
    declare cursor_nom_ecurie varchar(255);

    declare curEcurieWithoutAppUser cursor for
        select e.*
        from ecurie e
                 left join app_user a
                           on a.managed_ecurie = e.ecurie_id
        where managed_ecurie is null;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    open curEcurieWithoutAppUser;

    vEcurie:
    loop

        fetch curEcurieWithoutAppUser into cursor_ecurie_id, cursor_nom_ecurie;
        if done then
            leave vEcurie;
        end if;

        insert into app_user(username,
                             prenom,
                             nom,
                             password,
                             role,
                             managed_ecurie)
        VALUES (cursor_nom_ecurie,
                concat('Manager ', cursor_nom_ecurie),
                '',
                '$2a$12$us8C8nqUZxXM9yZyzmGc4eKzsJAdAWcl7BL6PDtWuVQEC9hiwfh.y',
                'ROLE_ECURIE',
                cursor_ecurie_id);

    end loop;

end;