create sequence "user-sequence" start with 1 increment by 1;
create sequence "passport-sequence" start with 1 increment by 1;

create table users
(
    id                 bigint            not null,
    external_id        uuid              not null,
    phone              varchar(20)       unique,
    sex                varchar(12)       not null,
    photo_id           varchar(255),
    is_deleted         boolean           not null,
    birthdate          date              not null,
    created_at         timestamp(6)      not null,
    modified_at        timestamp(6)      not null,
    primary key (id)
);

create table passports
(
    id                         bigint          not null,
    external_id                uuid            not null,
    passport_series            varchar(4)      not null,
    passport_number            varchar(6)      not null,
    passport_division_name     varchar (255)   not null,
    passport_division_code     varchar (6)     not null,
    passport_date_of_issue     date            not null,
    created_at                 timestamp(6)    not null,
    modified_at                timestamp(6)    not null,
    user_id                    bigint          not null unique references users (id),
    primary key (id),

    constraint unique_passport unique (passport_series, passport_number)
);