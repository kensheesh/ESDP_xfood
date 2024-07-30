insert into check_lists(status, work_schedule_id, uuid_link, feedback, duration, expert_id, end_time, type_id)
values

    ('DONE', 1, '8c1b9d73-4e77-4b2e-beb6-02182f6e8c1d', null, null, 1, '2024-07-21 17:00:00.000000', 1),
    ('DONE', 2, '4d682a52-cae4-4c9e-b1d7-6e6de10e93c7', null, null, 2, '2024-07-22 17:00:00.000000', 2),
    ('DONE', 3, 'd3f4a207-1d5a-4b93-b07c-4dc2d689efc0', null, null, 3, '2024-07-23 17:00:00.000000', 3),
    ('DONE', 4, 'ab9b2d78-829a-407b-9b67-5c74169e1b67', null, null, 1, '2024-07-21 17:00:00.000000', 1),
    ('DONE', 5, 'b94a3c89-4d1c-4b63-9d62-6e2e4a5a78c4', null, null, 2, '2024-07-22 17:00:00.000000', 2),
    ('DONE', 6, '5e67d5d0-19f4-4b22-931d-2c6974e20f94', null, null, 3, '2024-07-23 17:00:00.000000', 3),
    ('DONE', 7, 'e6bde7d1-b06f-4c54-a3c6-5a5df9a6d8c3', null, null, 1, '2024-07-21 17:00:00.000000', 1),
    ('DONE', 8, '14b0a665-0ae5-40a3-810b-4f15b40b04cf', null, null, 2, '2024-07-22 17:00:00.000000', 2),
    ('DONE', 9, 'cf0a5d2b-d1d3-4ec6-99c3-90b0e5c4b7f4', null, null, 3, '2024-07-23 17:00:00.000000', 3),
    ('DONE', 10, 'd272f750-d1c1-4c90-9c7e-2b77e8f03a6f', null, null, 1, '2024-07-21 17:00:00.000000', 4);


insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1,(select id from check_lists where uuid_link = '8c1b9d73-4e77-4b2e-beb6-02182f6e8c1d'), 10, 10),
    (2,(select id from check_lists where uuid_link = '8c1b9d73-4e77-4b2e-beb6-02182f6e8c1d'), 10, 10),
    (2,(select id from check_lists where uuid_link = '8c1b9d73-4e77-4b2e-beb6-02182f6e8c1d'), 10, 10);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = '4d682a52-cae4-4c9e-b1d7-6e6de10e93c7'), 4, 4),
    (2, (select id from check_lists where uuid_link = '4d682a52-cae4-4c9e-b1d7-6e6de10e93c7'), 4, 4),
    (3, (select id from check_lists where uuid_link = '4d682a52-cae4-4c9e-b1d7-6e6de10e93c7'), 4, 4),
    (4, (select id from check_lists where uuid_link = '4d682a52-cae4-4c9e-b1d7-6e6de10e93c7'), 4, 4);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'd3f4a207-1d5a-4b93-b07c-4dc2d689efc0'), 7, 7),
    (2, (select id from check_lists where uuid_link = 'd3f4a207-1d5a-4b93-b07c-4dc2d689efc0'), 7, 7),
    (3, (select id from check_lists where uuid_link = 'd3f4a207-1d5a-4b93-b07c-4dc2d689efc0'), 7, 7);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'ab9b2d78-829a-407b-9b67-5c74169e1b67'), 6, 6),
    (2, (select id from check_lists where uuid_link = 'ab9b2d78-829a-407b-9b67-5c74169e1b67'), 6, 6);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'b94a3c89-4d1c-4b63-9d62-6e2e4a5a78c4'), 8, 8),
    (2, (select id from check_lists where uuid_link = 'b94a3c89-4d1c-4b63-9d62-6e2e4a5a78c4'), 8, 8),
    (3, (select id from check_lists where uuid_link = 'b94a3c89-4d1c-4b63-9d62-6e2e4a5a78c4'), 8, 8);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = '5e67d5d0-19f4-4b22-931d-2c6974e20f94'), 5, 5),
    (2, (select id from check_lists where uuid_link = '5e67d5d0-19f4-4b22-931d-2c6974e20f94'), 5, 5),
    (3, (select id from check_lists where uuid_link = '5e67d5d0-19f4-4b22-931d-2c6974e20f94'), 5, 5);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'e6bde7d1-b06f-4c54-a3c6-5a5df9a6d8c3'), 9, 9),
    (2, (select id from check_lists where uuid_link = 'e6bde7d1-b06f-4c54-a3c6-5a5df9a6d8c3'), 9, 9);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = '14b0a665-0ae5-40a3-810b-4f15b40b04cf'), 6, 6),
    (2, (select id from check_lists where uuid_link = '14b0a665-0ae5-40a3-810b-4f15b40b04cf'), 6, 6);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'cf0a5d2b-d1d3-4ec6-99c3-90b0e5c4b7f4'), 8, 8),
    (2, (select id from check_lists where uuid_link = 'cf0a5d2b-d1d3-4ec6-99c3-90b0e5c4b7f4'), 8, 8),
    (3, (select id from check_lists where uuid_link = 'cf0a5d2b-d1d3-4ec6-99c3-90b0e5c4b7f4'), 8, 8);

insert into check_lists_criteria(criteria_id, checklist_id, value, max_value)
values
    (1, (select id from check_lists where uuid_link = 'd272f750-d1c1-4c90-9c7e-2b77e8f03a6f'), 4, 4),
    (2, (select id from check_lists where uuid_link = 'd272f750-d1c1-4c90-9c7e-2b77e8f03a6f'), 4, 4);
