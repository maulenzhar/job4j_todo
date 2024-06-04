CREATE TABLE priorities (
                            id SERIAL PRIMARY KEY,
                            name TEXT UNIQUE NOT NULL,
                            position int
);

INSERT INTO priorities (name, position) VALUES ('High', 1);
INSERT INTO priorities (name, position) VALUES ('Medium', 2);
INSERT INTO priorities (name, position) VALUES ('Low', 3);

ALTER TABLE tasks ADD COLUMN priority_id int REFERENCES priorities(id);

UPDATE tasks SET priority_id = (SELECT id FROM priorities WHERE name = 'High');