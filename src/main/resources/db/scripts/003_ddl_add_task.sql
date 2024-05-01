ALTER TABLE tasks ADD COLUMN user_id INT NOT NULL, ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);

insert into users (id, login, name, password) values (1, 'test', 'test', 'test');

insert into tasks(title, description, created, done, user_id) values('продукты', 'взять продукты', '2024-03-01 21:50:19.861300', false, 1) ON CONFLICT DO NOTHING;
insert into tasks(title, description, created, done, user_id) values('уборка', 'убраться в квартире', '2024-03-01 21:51:19.861300', false, 1) ON CONFLICT DO NOTHING;
insert into tasks(title, description, created, done, user_id) values('стирка', 'постирать вещи', '2024-03-01 21:52:19.861300', false, 1) ON CONFLICT DO NOTHING;