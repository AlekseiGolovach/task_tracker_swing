INSERT INTO task_tracker.employee (id, full_name)
VALUES (1, 'Иванов В. В.');

INSERT INTO task_tracker.employee (id, full_name)
VALUES (2, 'Федоров А. В.');

INSERT INTO task_tracker.task (end_date, execution_date, is_done, name, start_date, employee_id)
VALUES ('2024-03-10', '2024-02-13', true, 'task1.1', '2024-01-13' , 1);

INSERT INTO task_tracker.task (end_date, execution_date, is_done, name, start_date, employee_id)
VALUES ('2024-02-11', '2024-01-19', false, 'task1.2', '2024-01-17' , 1);

INSERT INTO task_tracker.task (end_date, execution_date, is_done, name, start_date, employee_id)
VALUES ('2024-03-15', '2024-01-23', true, 'task2.1', '2024-01-11' , 2);

INSERT INTO task_tracker.task (end_date, execution_date, is_done, name, start_date, employee_id)
VALUES ('2024-01-17', '2024-01-15', false, 'task2.2', '2024-01-10' , 2);

