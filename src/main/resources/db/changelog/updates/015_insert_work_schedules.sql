insert into work_schedules(manager_id, pizzeria_id, date, start_time, end_time)
values ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Борисов-1'), '2024-06-05', '10:00:00', '16:00:00'),
       ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Полоцк-1'), '2024-06-06', '09:00:00', '18:00:00'),
       ((select id from managers where phone_number = '74957556983'), (select id from pizzerias where name='Орша-1'), '2024-06-07', '08:30:00', '17:00:00'),
       ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Тучково-1'), '2024-06-05', '10:00:00', '16:00:00'),
       ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Котельники-1'), '2024-06-06', '08:30:00', '17:00:00'),
       ((select id from managers where phone_number = '73557556083'), (select id from pizzerias where name='Кубинка-1'), '2024-06-07', '09:00:00', '18:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-2'), '2024-06-08', '08:00:00', '17:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-3'), '2024-06-10', '10:00:00', '16:00:00'),
       ((select id from managers where phone_number = '76958956903'), (select id from pizzerias where name='Иванова-1'), '2024-06-05', '09:00:00', '18:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Печора-1'), '2024-06-06', '10:00:00', '16:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Усинск-1'), '2024-06-07', '08:30:00', '17:00:00'),
       ((select id from managers where phone_number = '76651256983'), (select id from pizzerias where name='Сосногорск-1'), '2024-06-09', '08:00:00', '17:00:00');