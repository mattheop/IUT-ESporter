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
    
    foreign key (ecurie) references ecurie(id)
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
    id       int auto_increment primary key,
    username varchar(50) not null,
    prenom   varchar(50) not null,
    nom      varchar(50) not null,
    password varchar(90) not null,
    locked   boolean default false,
    role     varchar(50) not null
);

create table users
(
    username varchar(50)  not null primary key,
    password varchar(500) not null,
    enabled  boolean      not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);