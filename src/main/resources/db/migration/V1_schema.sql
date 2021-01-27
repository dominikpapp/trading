create table card
(
    card_id           varchar(255)     not null primary key,
    card_type         varchar(255),
    card_damage       DOUBLE PRECISION not null,
    card_weakness     DOUBLE PRECISION,
    card_element_type varchar(255),
    card_name         varchar(255)
);

create table bundle
(
    bundle_id varchar(255) not null primary key
);


create table bundle_card
(
    bundle_card_bundle_id varchar(255) not null
        references bundle on delete cascade,
    bundle_card_card_id   varchar(255) not null unique references card on delete cascade
);

create table player
(
    player_id              varchar(255) not null
        constraint player_pkey primary key,
    player_number_of_coins integer      not null,
    player_password        varchar(255) not null,
    player_username        varchar(255) unique,
    player_name            varchar(255),
    player_bio             varchar(255),
    player_image           varchar(255),
    player_games_played    integer,
    player_elo             integer
);


create table player_card
(
    player_id varchar(255) not null
        references player on delete cascade,
    card_id   varchar(255) not null
        references card on delete cascade
);

create table player_deck_card
(
    player_id varchar(255) not null
        references player on delete cascade,
    card_id   varchar(255) not null
        unique
        references card on delete cascade
);

create table player_locked_card
(
    player_locked_card_player_id varchar(255) not null
        references player on delete cascade,
    player_locked_card_card_id   varchar(255) not null
        unique
        references card on delete cascade
);

create table trading_deal
(
    trading_deal_id             varchar(255) primary key,
    trading_deal_card_id        varchar(255) references card (card_id) on delete cascade,
    trading_deal_card_type      varchar(255),
    trading_deal_minimum_damage DOUBLE PRECISION,
    trading_deal_creator_id     varchar(255) references player (player_id) on delete cascade
);