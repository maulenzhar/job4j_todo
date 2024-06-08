CREATE TABLE task_categories (
                                 id serial PRIMARY KEY,
                                 task_id INTEGER NOT NULL,
                                 category_id INTEGER NOT NULL,
                                 FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
                                 FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);