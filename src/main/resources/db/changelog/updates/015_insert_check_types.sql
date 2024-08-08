--liquibase formatted sql
--changeset Bubunur:insert_check_types

insert into check_types(name, total_max_value)
values ('Онлайн-клн', 60),
       ('Одна камера', 24),
       ('Процессы', 12),
       ('Кассир-сервис', null);