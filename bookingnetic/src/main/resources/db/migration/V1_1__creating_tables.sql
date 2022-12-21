CREATE TABLE IF NOT EXISTS address
(
    address_id serial not null,
    country varchar(255) not null,
    city varchar(255) not null,
    street varchar(255) not null,
    postal_code varchar(255) ,

    PRIMARY KEY (address_id)
);

CREATE TYPE roles AS ENUM ('admin', 'user', 'guest');


CREATE TABLE IF NOT EXISTS users
(
    user_id  serial not null,
    first_name  varchar(255) not null,
    last_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    phone varchar(255) ,
    about varchar(255) ,
    registration_date date DEFAULT CURRENT_DATE,
    enum_role roles not null,
    PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS accommodation
(
    accommodation_id serial not null,
    accommodation_name varchar(255) not null,
    description varchar(255),
    price_for_night numeric not null,
    creation_date date DEFAULT CURRENT_DATE,
    address_id serial,
    user_id serial,

    PRIMARY KEY (accommodation_id),
    CONSTRAINT fk_address
        FOREIGN KEY(address_id)
            REFERENCES address(address_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
            REFERENCES users(user_id)
            ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS image
(
    image_id serial not null,
    img bytea not null,
    description varchar(255),
    accommodation_id serial,

    PRIMARY KEY (image_id),
    CONSTRAINT fk_accommodation
        FOREIGN KEY (accommodation_id)
            REFERENCES accommodation(accommodation_id)
            ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS reservation
(
    reservation_id serial not null,
    reservation_date date not null DEFAULT CURRENT_DATE,
    start_date date not null,
    end_date date not null,
    total_price numeric not null,
    user_id serial,
    accommodation_id serial,

    PRIMARY KEY (reservation_id),
    CONSTRAINT fk_users
        FOREIGN KEY (user_id)
            REFERENCES users(user_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_accommodation
        FOREIGN KEY (accommodation_id)
            REFERENCES accommodation(accommodation_id)
            ON DELETE CASCADE
);