create table engine
(
    id   serial primary key,
    name varchar(255)
);

create table ford
(
    id        serial primary key,
    name      varchar(255),
    engine_id int references engine (id)
);

create table driver
(
    id   serial primary key,
    name varchar(255)
);

create table history_owner
(
    id        serial primary key,
    driver_id int references driver (id),
    car_id    int references ford (id)
);