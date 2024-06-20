--liquibase formatted sql
--changeset Bubunur:insert_work_schedules

insert into work_schedules(manager_id, pizzeria_id, date, start_time, end_time)
values
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'), '2024-06-10', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'), '2024-06-11', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'), '2024-06-12', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Полоцк-1'), '2024-06-13', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Полоцк-1'), '2024-06-14', '09:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Нововолоцк-1'), '2024-06-15', '09:00:00', '17:00:00'),
        ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Нововолоцк-1'), '2024-06-16', '09:00:00', '17:00:00'),

        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Тучково-1'), '2024-06-10', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Тучково-1'), '2024-06-11', '09:00:00', '17:00:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Отрадное-1'),'2024-06-12', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Отрадное-1'), '2024-06-13', '09:00:00', '17:30:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Котельники-1'), '2024-06-14', '10:00:00', '17:00:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Кубинка-1'), '2024-06-15', '09:30:00', '17:00:00'),
        ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Кубинка-1'), '2024-06-16', '09:30:00', '17:00:00'),

       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Кострома-1'), '2024-06-10', '10:00:00', '17:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Кострома-2'),'2024-06-11', '09:00:00', '17:30:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-2'), '2024-06-12', '10:00:00', '18:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-3'), '2024-06-13', '10:00:00', '17:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-3'), '2024-06-14', '08:00:00', '16:30:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-1'), '2024-06-15', '08:30:00', '17:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-1'), '2024-06-16', '10:00:00', '17:00:00'),

       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'),'2024-06-10', '10:00:00', '17:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-11', '09:00:00', '17:30:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'), '2024-06-12', '09:30:00', '17:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'), '2024-06-13', '10:00:00', '17:30:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Отрадное-1'), '2024-06-14', '10:00:00', '17:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-15', '11:00:00', '18:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Реутов-1'), '2024-06-16', '08:00:00', '17:00:00')
;

