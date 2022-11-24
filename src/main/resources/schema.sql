-- Player
create table joueur
(
    id           int auto_increment primary key,
    prenom       varchar(50) not null,
    nom          varchar(50) not null,
    pseudo       varchar(50) not null,
    nationnalite varchar(75) not null,
    entree_pro   date,
    ecurie       int,

    foreign key (ecurie) references ecurie (id)
);

-- Ecurie
create table ecurie
(
    id         int auto_increment primary key,
    nom_ecurie varchar(50) not null
);

-- AppUser
create table app_user
(
    id             int auto_increment primary key,
    username       varchar(50) not null,
    prenom         varchar(50) not null,
    nom            varchar(50) not null,
    password       varchar(90) not null,
    locked         boolean default false,
    role           varchar(50) not null,
    managed_ecurie int,

    foreign key (managed_ecurie) references ecurie (id)
);

create table jeu
(
    id        int auto_increment primary key,
    nom       varchar(50) not null,
    nb_joueur int         not null
);

create table users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);