--liquibase formatted sql
--changeset Bubunur:insert_locations


insert into locations(name, timezone)
values ('Беларусь', 3),
       ('Деревни', 3),
       ('Золотое Кольцо', 3),
       ('Москва', 3),
       ('Калининград', 2),
       ('Тверь', 3),
       ('Санкт-Петербург', 3),
       ('УСП', 3),
       ('Московская область', 3);