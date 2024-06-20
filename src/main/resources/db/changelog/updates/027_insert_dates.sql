/*--liquibase formatted sql
--changeset Dastan:insert_work_week

UPDATE work_schedules SET start_time = '2024-06-17 08:00:00.000000', end_time = '2024-06-17 16:30:00.000000' WHERE id = 3;
UPDATE work_schedules SET start_time = '2024-06-20 10:00:00.000000', end_time = '2024-06-20 17:00:00.000000' WHERE id = 23;
UPDATE work_schedules SET start_time = '2024-06-17 09:30:00.000000', end_time = '2024-06-17 18:00:00.000000' WHERE id = 1;
UPDATE work_schedules SET start_time = '2024-06-18 10:00:00.000000', end_time = '2024-06-18 17:00:00.000000' WHERE id = 7;
UPDATE work_schedules SET start_time = '2024-06-19 09:00:00.000000', end_time = '2024-06-19 17:30:00.000000' WHERE id = 16;
UPDATE work_schedules SET start_time = '2024-06-20 08:00:00.000000', end_time = '2024-06-20 16:30:00.000000' WHERE id = 27;
UPDATE work_schedules SET start_time = '2024-06-18 10:00:00.000000', end_time = '2024-06-18 17:30:00.000000' WHERE id = 10;
UPDATE work_schedules SET start_time = '2024-06-17 10:00:00.000000', end_time = '2024-06-17 17:30:00.000000' WHERE id = 2;
UPDATE work_schedules SET start_time = '2024-06-19 10:00:00.000000', end_time = '2024-06-19 17:00:00.000000' WHERE id = 15;
UPDATE work_schedules SET start_time = '2024-06-20 09:30:00.000000', end_time = '2024-06-20 18:00:00.000000' WHERE id = 25;
UPDATE work_schedules SET start_time = '2024-06-18 08:30:00.000000', end_time = '2024-06-18 17:00:00.000000' WHERE id = 12;
UPDATE work_schedules SET start_time = '2024-06-18 09:00:00.000000', end_time = '2024-06-18 17:30:00.000000' WHERE id = 8;
UPDATE work_schedules SET start_time = '2024-06-20 08:30:00.000000', end_time = '2024-06-20 17:00:00.000000' WHERE id = 28;
UPDATE work_schedules SET start_time = '2024-06-20 10:00:00.000000', end_time = '2024-06-20 17:30:00.000000' WHERE id = 26;
UPDATE work_schedules SET start_time = '2024-06-17 08:30:00.000000', end_time = '2024-06-17 17:00:00.000000' WHERE id = 4;
UPDATE work_schedules SET start_time = '2024-06-17 08:00:00.000000', end_time = '2024-06-17 17:00:00.000000' WHERE id = 6;
UPDATE work_schedules SET start_time = '2024-06-18 08:00:00.000000', end_time = '2024-06-18 17:00:00.000000' WHERE id = 14;
UPDATE work_schedules SET start_time = '2024-06-19 08:00:00.000000', end_time = '2024-06-19 17:00:00.000000' WHERE id = 22;
UPDATE work_schedules SET start_time = '2024-06-19 08:30:00.000000', end_time = '2024-06-19 17:00:00.000000' WHERE id = 20;
UPDATE work_schedules SET start_time = '2024-06-19 11:00:00.000000', end_time = '2024-06-19 18:00:00.000000' WHERE id = 21;
UPDATE work_schedules SET start_time = '2024-06-19 08:00:00.000000', end_time = '2024-06-19 16:30:00.000000' WHERE id = 19;
UPDATE work_schedules SET start_time = '2024-06-18 11:00:00.000000', end_time = '2024-06-18 18:00:00.000000' WHERE id = 13;
UPDATE work_schedules SET start_time = '2024-06-17 11:00:00.000000', end_time = '2024-06-17 18:00:00.000000' WHERE id = 5;
UPDATE work_schedules SET start_time = '2024-06-19 09:30:00.000000', end_time = '2024-06-19 18:00:00.000000' WHERE id = 17;
UPDATE work_schedules SET start_time = '2024-06-16 08:00:00.000000', end_time = '2024-06-18 18:00:00.000000' WHERE id = 9;
UPDATE work_schedules SET start_time = '2024-06-18 08:00:00.000000', end_time = '2024-06-18 16:30:00.000000' WHERE id = 11;
UPDATE work_schedules SET start_time = '2024-06-16 08:00:00.000000', end_time = '2024-06-18 18:00:00.000000' WHERE id = 27;
UPDATE work_schedules SET start_time = '2024-06-18 08:00:00.000000', end_time = '2024-06-18 16:30:00.000000' WHERE id = 28;*/


--liquibase formatted sql
--changeset Bubunur:insert_new_work_schedules

insert into work_schedules(manager_id, pizzeria_id, start_time, end_time)
values
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'), '2024-06-17 10:00:00.000000', '2024-06-17 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'),'2024-06-18 10:00:00.000000', '2024-06-18 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'),  '2024-06-19 10:00:00.000000', '2024-06-19 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Полоцк-1'),  '2024-06-20 10:00:00.000000', '2024-06-20 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Полоцк-1'), '2024-06-21 09:00:00.000000',  '2024-06-21 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Нововолоцк-1'),'2024-06-22 09:00:00.000000', '2024-06-22 17:00:00.000000'),
    ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Нововолоцк-1'), '2024-06-23 09:00:00.000000', '2024-06-23 17:00:00.000000'),

    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Тучково-1'),  '2024-06-17 10:00:00.000000', '2024-06-17 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Тучково-1'), '2024-06-18 10:00:00.000000', '2024-06-18 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Отрадное-1'),'2024-06-19 10:00:00.000000', '2024-06-19 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Отрадное-1'),  '2024-06-20 10:00:00.000000', '2024-06-20 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Котельники-1'), '2024-06-21 09:00:00.000000',  '2024-06-21 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Кубинка-1'), '2024-06-22 09:00:00.000000', '2024-06-22 17:00:00.000000'),
    ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Кубинка-1'), '2024-06-23 09:00:00.000000', '2024-06-23 17:00:00.000000'),

    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Кострома-1'),  '2024-06-17 10:00:00.000000', '2024-06-17 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Кострома-2'),'2024-06-18 10:00:00.000000', '2024-06-18 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-2'), '2024-06-19 10:00:00.000000', '2024-06-19 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-3'),  '2024-06-20 10:00:00.000000', '2024-06-20 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-3'), '2024-06-21 09:00:00.000000',  '2024-06-21 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-1'), '2024-06-22 09:00:00.000000', '2024-06-22 17:00:00.000000'),
    ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-1'), '2024-06-23 09:00:00.000000', '2024-06-23 17:00:00.000000'),

    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-17 10:00:00.000000', '2024-06-17 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-18 10:00:00.000000', '2024-06-18 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'), '2024-06-19 10:00:00.000000', '2024-06-19 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'),  '2024-06-20 10:00:00.000000', '2024-06-20 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'), '2024-06-21 09:00:00.000000',  '2024-06-21 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-22 09:00:00.000000', '2024-06-22 17:00:00.000000'),
    ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'),  '2024-06-23 09:00:00.000000', '2024-06-23 17:00:00.000000')
;


insert into opportunities(user_id, date, start_time, end_time)
values
    ((select id from users where email='expert1@ex.com'),'2024-06-17', '09:00:00','17:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-18', '11:00:00','15:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-19', '10:00:00','14:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-20', '09:00:00','17:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-21', '11:00:00','15:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-22', '10:00:00','14:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-23', '10:00:00','14:00:00' ),

    ((select id from users where email='expert2@ex.com'),'2024-06-17', '09:00:00','17:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-18', '08:30:00','16:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-19', '12:00:00','17:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-20', '19:00:00','23:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-21', '10:00:00','13:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-22', '10:00:00','13:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-23', '10:00:00','13:00:00' ),

    ((select id from users where email='expert3@ex.com'),'2024-06-17', '19:00:00','20:30:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-18', '13:00:00','17:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-19', '14:00:00','18:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-20', '19:00:00','20:30:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-21', '13:00:00','17:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-22', '14:00:00','18:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-23', '11:00:00','20:00:00' );