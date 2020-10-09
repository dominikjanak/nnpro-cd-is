DELETE FROM user;
DELETE FROM incident;

INSERT INTO user (id, username, password) VALUES (1, 'user', '$2a$10$XgbP6h1UQ1tI8O6t/5u8mOGiWxMIbEwCmzawLCK4Ui9kNqrwqT1g2');

INSERT INTO incident (id, name) VALUES
(1, 'První incident'),
(2, 'Druhý incident'),
(3, 'Třetí incident')
