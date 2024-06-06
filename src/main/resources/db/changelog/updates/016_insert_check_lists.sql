--liquibase formatted sql

--changeset Bubunur:insert_check_lists

insert into check_lists(status, work_schedule_id, opportunity_id)
values
    ('done', (select id from work_schedules where date= '2024-06-05' and manager_id = (select id from managers where phone_number = '74957556983')), (select id from opportunities where date='2024-06-05' and user_id = (select id from users where email = 'expert1@ex.com') )),
    ('new', (select id from work_schedules where date= '2024-06-07' and manager_id = (select id from managers where phone_number = '74957556983')), (select id from opportunities where  date='2024-06-07' and user_id = (select id from users where email = 'expert1@ex.com') )),
    ('done', (select id from work_schedules where date= '2024-06-05' and manager_id = (select id from managers where phone_number = '73557556083')), (select id from opportunities where  date='2024-06-05' and user_id = (select id from users where email = 'expert2@ex.com') )),
    ('new', (select id from work_schedules where date= '2024-06-07' and manager_id = (select id from managers where phone_number = '73557556083')), (select id from opportunities where  date='2024-06-07' and user_id = (select id from users where email = 'expert2@ex.com') )),
    ('done', (select id from work_schedules where date= '2024-06-06' and manager_id = (select id from managers where phone_number = '76651256983')), (select id from opportunities where  date='2024-06-05' and user_id = (select id from users where email = 'expert3@ex.com') )),
    ('in_progress', (select id from work_schedules where date= '2024-06-07' and manager_id = (select id from managers where phone_number = '76651256983')), (select id from opportunities where  date='2024-06-06' and user_id = (select id from users where email = 'expert3@ex.com') )),
    ('new', (select id from work_schedules where date= '2024-06-09' and manager_id = (select id from managers where phone_number = '76651256983')), (select id from opportunities where  date='2024-06-07' and  user_id = (select id from users where email = 'expert3@ex.com') ));