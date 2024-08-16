--liquibase formatted sql
--changeset Dastan:insert_fees

INSERT INTO public.check_type_fees (check_type_id, fees, enabled, created_date) VALUES (1, 180.00, true, '2024-08-12 17:12:32.000000');
INSERT INTO public.check_type_fees (check_type_id, fees, enabled, created_date) VALUES (2, 85.00, true, '2024-08-16 17:13:05.000000');
INSERT INTO public.check_type_fees (check_type_id, fees, enabled, created_date) VALUES (3, 210.00, true, '2024-08-14 17:12:32.000000');
INSERT INTO public.check_type_fees (check_type_id, fees, enabled, created_date) VALUES (4, 100.00, true, '2024-08-11 17:12:32.000000');

--liquibase formatted sql
--changeset Dastan:add_uuid_for_some_pizzerias
UPDATE public.pizzerias SET name = 'Борисов-1', location_id = 1, uuid = '0022487F0C21BB2911EC2D8CE7399AEC' WHERE id = 1;
UPDATE public.pizzerias SET name = 'Полоцк-1', location_id = 1, uuid = '0022487F0C21BB2B11EC84DDD7E5F318' WHERE id = 2;
UPDATE public.pizzerias SET name = 'Новополоцк-1', location_id = 1, uuid = 'E274F351EF57A80B11EDBC04A8760104' WHERE id = 3;
UPDATE public.pizzerias SET name = 'Орша-1', location_id = 1, uuid = '000D3AA93D2FBB2911EC2D8D142A8BD6' WHERE id = 4;
UPDATE public.pizzerias SET name = 'СПб 2-2', location_id = 7, uuid = '000D3A2480C380E911E775E74BBD17B3' WHERE id = 27;
UPDATE public.pizzerias SET name = 'СПб 2-7', location_id = 7, uuid = '000D3AAC9DCABB2E11EC8356A8F86D2D' WHERE id = 28;

--liquibase formatted sql
--changeset Dastan:add_checklist_temlates
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (1, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (2, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (3, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (5, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (6, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (8, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (10, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (11, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (12, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (25, 4, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (32, 4, -8);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (2, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (3, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (4, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (5, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (6, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (7, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (10, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (11, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (12, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (13, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (16, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (17, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (18, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (19, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (21, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (30, 3, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (31, 3, -4);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (32, 3, -8);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (1, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (5, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (6, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (11, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (20, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (22, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (24, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (25, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (28, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (30, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (31, 2, -4);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (32, 2, -8);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (36, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (37, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (46, 2, 1);
INSERT INTO public.criteria_types (criteria_id, type_id, max_value) VALUES (48, 2, 1);
