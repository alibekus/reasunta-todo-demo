DELETE FROM t_task;
ALTER SEQUENCE seq_activity RESTART WITH 10000;

INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Meeting', 'Meet with business partner', '2022-06-15 15:45', '2022-06-12 19:55', '2022-06-14 10:33');
INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Dentist', 'Go to dentist', '2022-06-17 11:15', '2022-06-11 11:21', '2022-06-14 10:33');
INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Food', 'Buy bread 1 item, milk 1l, apples 1 kg', '2022-06-30 10:00', '2022-06-23 10:33', '2022-06-23 10:33');
INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Sport', 'Go to the gym', '2022-06-29 19:30', '2022-06-24 16:24', '2022-06-23 10:33');
INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Massage', 'Massage of back', '2022-07-02 09:30', '2022-06-22 16:24', '2022-06-25 10:33');
INSERT INTO t_task (id, title, description, activity_datetime, created, modified)
    VALUES (NEXTVAL('seq_activity'), 'Read books', 'Read sci-fi', null, now(), now());
