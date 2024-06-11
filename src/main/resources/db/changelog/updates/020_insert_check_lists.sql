--liquibase formatted sql
--changeset Bubunur:insert_check_lists

insert into check_lists(status, work_schedule_id, opportunity_id)
values
    ('DONE', (select id from work_schedules where date='2024-06-10' and manager_id=(select id from managers where phone_number = '74957556983')), (select id from opportunities where date = '2024-06-10' and user_id=(select id from users where email='expert1@ex.com'))),
    ('IN_PROGRESS', (select id from work_schedules where date='2024-06-11' and manager_id=(select id from managers where phone_number = '73557556083')), (select id from opportunities where date = '2024-06-11' and user_id=(select id from users where email='expert1@ex.com'))),
    ('NEW', (select id from work_schedules where date='2024-06-16' and manager_id=(select id from managers where phone_number = '74957556983')), (select id from opportunities where date = '2024-06-16' and user_id=(select id from users where email='expert1@ex.com')))
;
