CREATE TABLE IF NOT EXISTS account
(
    account_id  serial NOT NULL,
    first_name  varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    phone varchar(255) NOT NULL UNIQUE,
    about varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    date_created date NOT NULL,

    PRIMARY KEY (account_id)
);

CREATE TABLE IF NOT EXISTS address
(
    address_id  serial NOT NULL,
    country  varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    street varchar(255) NOT NULL,
    postal_code varchar(255) NOT NULL,

    PRIMARY KEY (address_id)
);
CREATE TABLE IF NOT EXISTS image
(
    image_id serial NOT NULL,

    PRIMARY KEY (image_id)

);
CREATE TABLE IF NOT EXISTS accommodation
(
    accommodation_id serial NOT NULL,
    accomodation_name varchar(255) NOT NULL,
    description varchar(255),
    address_fk serial REFERENCES address(address_id),
    user_fk serial REFERENCES account(account_id),
    price numeric NOT NULL,

    PRIMARY KEY (accommodation_id)
);
CREATE TABLE IF NOT EXISTS reservation
(
    reservation_id serial NOT NULL,
    account_fk serial REFERENCES account(account_id),
    time_of_reservation date NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    total_price numeric NOT NULL,

    PRIMARY KEY (reservation_id)
);