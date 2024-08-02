insert into shifts(start_time, end_time, opportunity_id)
values
    ('10:00:00', '12:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-22' AND user_id = 3)),
    ('14:00:00', '16:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-22' AND user_id = 3)),
    ('19:00:00', '21:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-22' AND user_id = 3)),
    ('10:00:00', '12:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-25' AND user_id = 3)),
    ('14:00:00', '16:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-27' AND user_id = 3)),
    ('19:00:00', '21:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-27' AND user_id = 3)),
    ('10:00:00', '12:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-23' AND user_id = 4)),
    ('14:00:00', '16:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-23' AND user_id = 4)),
    ('19:00:00', '21:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-23' AND user_id = 4)),
    ('10:00:00', '12:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-26' AND user_id = 4)),
    ('12:00:00', '14:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-26' AND user_id = 4)),
    ('15:00:00', '17:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-26' AND user_id = 4)),
    ('14:00:00', '16:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-23' AND user_id = 5)),
    ('19:00:00', '21:00:00', (SELECT user_id FROM opportunities WHERE date = '2024-07-25' AND user_id = 5));