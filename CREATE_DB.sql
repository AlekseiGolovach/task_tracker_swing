CREATE SCHEMA task_tracker;

CREATE TABLE task_tracker.employee(
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      full_name VARCHAR(50) DEFAULT NULL,
                                      PRIMARY KEY(ID)
);

CREATE TABLE task_tracker.task(
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  name VARCHAR(50) DEFAULT NULL,
                                  is_done BOOLEAN DEFAULT FALSE,
                                  start_date DATE,
                                  end_date DATE,
                                  execution_date DATE,
                                  employee_id BIGINT NOT NULL,
                                  FOREIGN KEY (employee_id) REFERENCES employee(id),
                                  PRIMARY KEY(ID)
);
