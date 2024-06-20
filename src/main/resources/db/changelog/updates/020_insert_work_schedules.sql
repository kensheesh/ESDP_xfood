
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


