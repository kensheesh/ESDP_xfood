--liquibase formatted sql
--changeset Bubunur:insert_check_types

insert into check_types(name)
values ('Онлайн-клн'),
       ('Одна камера'),
       ('Процессы'),
       ('Кассир-сервис');