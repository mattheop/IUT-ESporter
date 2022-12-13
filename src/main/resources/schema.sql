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

create table equipe
(
    id        int auto_increment primary key,
    nom       varchar(50) not null,
    jeu_spe   int         not null,
    ecurie_id int         not null,
    foreign key (jeu_spe) references jeu (id),
    foreign key (ecurie_id) references ecurie (id)
);

create table joueur_equipe
(
    joueur_id int not null,
    equipe_id int not null,

    primary key (joueur_id, equipe_id),
    foreign key (joueur_id) references joueur (id),
    foreign key (equipe_id) references equipe (id)
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

create table competition
(
    competition_id       int auto_increment primary key,
    date_debut           datetime not null,
    date_fin_inscription datetime not null,
    jeu_id               int,
    tournois_id          int,

    foreign key (jeu_id) references jeu (jeu_id),
    foreign key (tournois_id) references tournois (tournois_id)
);

create table inscription
(
    inscription_id int auto_increment primary key,
    competition_id int,
    equipe_id      int,


    foreign key (competition_id) references competition (competition_id),
    foreign key (equipe_id) references equipe (id)
)