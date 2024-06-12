--liquibase formatted sql
--changeset Bubunur:insert_zones

insert into zones (name)
values ('Потери'),
       ('Курьеры'),
       ('Кассир'),
       ('Касса'),
       ('Тесто'),
       ('Кухня'),
       ('Ресторан'),
       ('Сотрудник'),
       (''),
       ('Чистота'),
       ('Менеджмент'),
       ('Пиццамейкер'),
       ('Менеджер');