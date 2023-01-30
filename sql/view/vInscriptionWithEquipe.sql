create view iut_sae.v_inscription_with_poule as
select i.*, ip.poule_num
from inscription i
         left join inscription_poule ip on i.inscription_id = ip.inscription_id;

