--liquibase formatted sql
--changeset Bubunur:insert_opportunities

insert into opportunities(user_id, date, start_time, end_time)
values
    ((select id from users where email='expert1@ex.com'),'2024-06-10', '09:00:00','17:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-11', '11:00:00','15:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-12', '10:00:00','14:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-13', '09:00:00','17:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-14', '11:00:00','15:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-15', '10:00:00','14:00:00' ),
    ((select id from users where email='expert1@ex.com'),'2024-06-16', '10:00:00','14:00:00' ),

    ((select id from users where email='expert2@ex.com'),'2024-06-10', '09:00:00','17:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-11', '08:30:00','16:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-12', '12:00:00','17:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-13', '19:00:00','23:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-14', '10:00:00','13:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-15', '10:00:00','13:00:00' ),
    ((select id from users where email='expert2@ex.com'),'2024-06-16', '10:00:00','13:00:00' ),

    ((select id from users where email='expert3@ex.com'),'2024-06-10', '19:00:00','20:30:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-11', '13:00:00','17:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-12', '14:00:00','18:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-13', '19:00:00','20:30:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-14', '13:00:00','17:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-15', '14:00:00','18:00:00' ),
    ((select id from users where email='expert3@ex.com'),'2024-06-16', '11:00:00','20:00:00' );