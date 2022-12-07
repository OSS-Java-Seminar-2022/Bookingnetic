CREATE TABLE IF NOT EXISTS c_user
(
    c_user_id  serial not null,
    c_first_name  varchar(255) not null,
    c_last_name varchar(255) not null,
    c_email varchar(255) not null,
    c_password varchar(255) not null,
    c_phone varchar(255) not null,
    c_about varchar(255) not null,

    PRIMARY KEY (c_user_id)
);

CREATE TABLE IF NOT EXISTS c_address
(
    c_address_id  serial not null,
    c_country  varchar(255) not null,
    c_city varchar(255) not null,
    c_street varchar(255) not null,
    c_postal_code varchar(255) not null,

    PRIMARY KEY (c_address_id)
);
CREATE TABLE IF NOT EXISTS c_image
(
    c_image_id serial not null,

    PRIMARY KEY (c_image_id)

);
CREATE TABLE IF NOT EXISTS c_accommodation
(
    c_accommodation_id serial not null,
    c_name varchar(255) not null,
    c_description varchar(255),
    c_address_fk serial references c_address(c_address_id),
    c_user_fk serial references c_user(c_user_id),
    c_price numeric,

    PRIMARY KEY (c_accommodation_id)
);
CREATE TABLE IF NOT EXISTS c_reservation
(
    c_reservation_id serial not null,
    c_user_fk serial references c_user(c_user_id),

    PRIMARY KEY (c_reservation_id)
);