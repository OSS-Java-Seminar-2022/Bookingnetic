CREATE TABLE IF NOT EXISTS address
(
    id serial not null,
    country varchar(255) not null,
    city varchar(255) not null,
    street varchar(255) not null unique,
    postal_code varchar(255),

    PRIMARY KEY (id)
);


CREATE TYPE roles AS ENUM ('ROLE_ADMIN', 'ROLE_USER');
CREATE CAST (varchar AS roles) WITH INOUT AS IMPLICIT;


CREATE TABLE IF NOT EXISTS users
(
    id serial not null,
    first_name  varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    phone varchar(255) ,
    registration_date date DEFAULT CURRENT_DATE,
    enum_role roles,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS accommodation
(
    id serial not null,
    title varchar(255) not null unique,
    description varchar(255),
    price_for_night numeric not null,
    creation_date date DEFAULT CURRENT_DATE,
    address_fk serial,
    user_fk serial,

    PRIMARY KEY (id),
    CONSTRAINT fk_address
        FOREIGN KEY(address_fk)
            REFERENCES address(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_user
        FOREIGN KEY (user_fk)
            REFERENCES users(id)
            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS image
(
    id serial not null,
    img bytea not null,
    description varchar(255),
    accommodation_fk serial,

    PRIMARY KEY (id),
    CONSTRAINT fk_accommodation
        FOREIGN KEY (accommodation_fk)
            REFERENCES accommodation(id)
            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS reservation
(
    id serial not null,
    reservation_date date not null DEFAULT CURRENT_DATE,
    check_in date not null,
    check_out date not null,
    price numeric not null,
    user_fk serial,
    accommodation_fk serial,

    PRIMARY KEY (id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_fk)
            REFERENCES users(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_accommodation
        FOREIGN KEY (accommodation_fk)
            REFERENCES accommodation(id)
            ON DELETE CASCADE
);