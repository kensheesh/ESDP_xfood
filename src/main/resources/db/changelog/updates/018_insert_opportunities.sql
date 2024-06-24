--liquibase formatted sql
--changeset Bubunur:insert_opportunities

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