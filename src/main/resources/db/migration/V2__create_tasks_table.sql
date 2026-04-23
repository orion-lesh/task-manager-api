CREATE TABLE tasks(
    id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    due_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp,
    category_id BIGINT,


    CONSTRAINT fk_tasks_category
                  FOREIGN KEY (category_id)
                  REFERENCES categories(id)
                  ON DELETE SET NULL ,
    CONSTRAINT chk_tasks_status
                  CHECK ( status in ('TODO', 'IN_PROGRESS', 'DONE' )),

    CONSTRAINT chk_task_priority
                  CHECK ( priority in ('LOW', 'MEDIUM', 'HIGH'))

);
CREATE INDEX idx_tasks_status      ON tasks (status);
CREATE INDEX idx_tasks_category_id ON tasks (category_id);
CREATE INDEX idx_tasks_due_date    ON tasks (due_date);