--liquibase formatted sql
--changeset Dastan:insert_new_users

insert into users(name, surname, tg_link, email, password, role)
VALUES ('Корчевой', 'Владислав', 't.me/v_korchevoy', 'v.korchevoy@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'ADMIN'),
       ('Ашимов', 'Арген', 't.me/a_ashimov', 'a.ashimov@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'SUPERVISOR'),
       ('Жумаканова', 'Бубунур', 't.me/b_z', 'b.zhumakanova@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'EXPERT'),
       ('Куренкиев', 'Данис', 't.me/d_kurenkiev', 'd.kurenkiev@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'EXPERT'),
       ('Абдыкалыков', 'Дастан', 't.me/d_abdykalykov', 'd.abdykalykov@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'EXPERT'),
       ('Бейшекеев', 'Нурсултан', 't.me/n_beyshikeev', 'n.beyshikeev@ex.com', '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2', 'EXPERT');


