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
