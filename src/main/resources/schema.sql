-- Player
create table Joueur
(
    id           int auto_increment primary key,
    prenom       varchar(50) not null,
    nom          varchar(50) not null,
    pseudo       varchar(50) not null,
    nationnalite varchar(75) not null,
    entree_pro   date


);