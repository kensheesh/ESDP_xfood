--liquibase formatted sql

--changeset Bubunur:insert_managers

insert into managers(name, surname, phone_number)
values ('Константин', 'Ковальчук', '74957556983'),
       ('Елизавета', 'Алехнович', '73557556083'),
       ('Кристина', 'Гергерт', '76958956903'),
       ('Юлия', 'Боровикова', '76651256983');