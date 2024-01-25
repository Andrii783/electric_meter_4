CREATE table person
(
    id             int generated by default as identity primary key,
    first_name     varchar(20)    not null,
    second_name    varchar(20)    not null,
    phone          varchar(13)    not null unique,
    email          varchar unique not null,
    name_company   varchar(50),
    number_company int,
    username       varchar(50),
    password varchar not null ,
    role varchar

);
CREATE table address
(
    id        int generated by default as identity primary key,
    city      varchar(20) not null,
    street    varchar(20) not null,
    house     int,
    letter    varchar(1) default '',
    entrance  int,
    apartment int,
    owner_id  int references person (id)

);
CREATE table meter
(
    id         int generated by default as identity primary key,
    number     int not null unique,
    indexes    int,
    date       TIMESTAMP default CURRENT_DATE,
    address_id int references address (id),
    name       varchar

);
CREATE table index
(
    id       int generated by default as identity primary key,
    index    int,
    date     TIMESTAMP default CURRENT_TIMESTAMP,
    meter_id int references meter (id)
);
insert into person (first_name, second_name, phone, email, name_company, number_company, username, password) VALUES ('And','And',+380961234565,'fjgjjg@gmail','Electro',1234567890,'And',1234);
DROP table index;
DROP table meter;
DROP table address;
DROP table person;

