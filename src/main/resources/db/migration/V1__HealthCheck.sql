create table if not exists health_check (
    id serial primary key,
    status varchar(30) not null
);

insert into health_check(status) values ('OK');