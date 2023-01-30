create or replace trigger BI_MAX_16_INSCRIPTION_PAR_COMPETITION
    before insert
    on inscription
    for each row
begin

    if is_competition_full(NEW.competition_id) then
        signal sqlstate '45000'
            set message_text = 'Nombre d\'inscription dépassé';
    end if;
end;

create or replace trigger BU_MAX_16_INSCRIPTION_PAR_COMPETITION
    before update
    on inscription
    for each row
begin

    if NEW.competition_id <> OLD.competition_id then
        if is_competition_full(NEW.competition_id) then
            signal sqlstate '45000'
                set message_text = 'Nombre d\'inscription dépassé';
        end if;
    end if;
end;