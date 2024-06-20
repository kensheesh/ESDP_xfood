--liquibase formatted sql
--changeset Bubunur:insert_check_lists

insert into check_lists(status, work_schedule_id, opportunity_id, uuid_link)
values
    ('DONE', (select id from work_schedules where start_time='2024-06-17 10:00:00.000000' and manager_id=(select id from managers where phone_number = '74957556983')), (select id from opportunities where date = '2024-06-17' and user_id=(select id from users where email='expert1@ex.com')),'aa0073f5-5aec-45b8-9f86-f27625c42fa3' ),
    ('IN_PROGRESS', (select id from work_schedules where start_time='2024-06-18 10:00:00.000000' and manager_id=(select id from managers where phone_number = '73557556083')), (select id from opportunities where date = '2024-06-18' and user_id=(select id from users where email='expert1@ex.com')), '6a854a8c-41f7-4eee-bbd5-9db540a61dd2'),
    ('NEW', (select id from work_schedules where start_time='2024-06-22 09:00:00.000000' and manager_id=(select id from managers where phone_number = '74957556983')), (select id from opportunities where date = '2024-06-23' and user_id=(select id from users where email='expert1@ex.com')), '2900fd68-3139-466b-ba51-04242077b67d')
;
