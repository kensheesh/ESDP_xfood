--liquibase formatted sql
--changeset Dastan:update_users

UPDATE public.users SET name = 'Нурсултан', surname = 'Бейшекеев' WHERE email = 'n.beishekeev@ex.com';
UPDATE public.users SET name = 'Арген', surname = 'Ашимов' WHERE email = 'a.ashimov@ex.com';
UPDATE public.users SET name = 'Бубунур', surname = 'Жумаканова' WHERE email = 'b.zhumakanova@ex.com';
UPDATE public.users SET name = 'Дастан', surname = 'Абдыкалыков' WHERE email = 'd.abdykalykov@ex.com';
UPDATE public.users SET name = 'Данис', surname = 'Куренкиев' WHERE email = 'd.kurenkiev@ex.com';
UPDATE public.users SET name = 'Владислав', surname = 'Корчевой' WHERE email = 'v.korchevoy@ex.com';



