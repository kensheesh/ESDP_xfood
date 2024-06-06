--liquibase formatted sql

--changeset Bubunur:insert_opportunities

insert into opportunities(user_id, date, start_time, end_time)
values ((select id from users where email='expert1@ex.com'),'2024-06-05', '09:00:00','17:00:00' ),
       ((select id from users where email='expert1@ex.com'),'2024-06-06', '11:00:00','15:00:00' ),
       ((select id from users where email='expert1@ex.com'),'2024-06-07', '10:00:00','14:00:00' ),
       ((select id from users where email='expert2@ex.com'),'2024-06-05', '09:00:00','17:00:00' ),
       ((select id from users where email='expert2@ex.com'),'2024-06-06', '08:30:00','16:00:00' ),
       ((select id from users where email='expert2@ex.com'),'2024-06-07', '12:00:00','17:00:00' ),
       ((select id from users where email='expert2@ex.com'),'2024-06-08', '19:00:00','23:00:00' ),
       ((select id from users where email='expert2@ex.com'),'2024-06-10', '10:00:00','13:00:00' ),
       ((select id from users where email='expert3@ex.com'),'2024-06-05', '19:00:00','20:30:00' ),
       ((select id from users where email='expert3@ex.com'),'2024-06-06', '13:00:00','17:00:00' ),
       ((select id from users where email='expert3@ex.com'),'2024-06-07', '14:00:00','18:00:00' ),
       ((select id from users where email='expert3@ex.com'),'2024-06-09', '11:00:00','20:00:00' );