DELETE FROM user;
DELETE FROM region;
DELETE FROM area;
DELETE FROM role;

INSERT INTO role (id, name) VALUES
(2, 'ROLE_USER'),
(1, 'ROLE_ADMIN');

INSERT INTO area (id, name) VALUES
(1, 'Jižní Morava'),
(2, 'Severní Morava'),
(3, 'Východní Čechy '),
(4, 'Západní Čechy'),
(5, 'Střední Čechy');

INSERT INTO region (id, abbreviation, name, area_id) VALUES
(1, 'PHA', 'Hlavní město Praha', 5),
(2, 'STČ', 'Středočeský kraj', 5),
(3, 'JHČ', 'Jihočeský kraj', 4),
(4, 'PLK', 'Plzeňský kraj', 4),
(5, 'KVK', 'Karlovarský kraj', 4),
(6, 'ULK', 'Ústecký kraj', 4),
(7, 'LBK', 'Liberecký kraj', 3),
(8, 'HKK', 'Královéhradecký kraj', 3),
(9, 'PAK', 'Pardubický kraj', 3),
(10, 'OLK', 'Olomoucký kraj', 2),
(11, 'MSK', 'Moravskoslezský kraj', 2),
(12, 'JHM', 'Jihomoravský kraj', 1),
(13, 'ZLK', 'Zlínský kraj', 1),
(14, 'VYS', 'Kraj Vysočina', 3);

INSERT INTO user (id, email, username, password, firstname, surname, area_id, renew_task, role_id) VALUES
(1, 'admin@example.com', 'admin', '$2a$10$jd2BK8zYq5ySLaBsaFjpgOF5SsvDJkWHpFea4xpEmlHGzu2TjNuV2', NULL, NULL, NULL, false, 1);