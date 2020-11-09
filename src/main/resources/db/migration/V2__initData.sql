DELETE FROM user;
DELETE FROM region;
DELETE FROM area;
DELETE FROM role;

INSERT INTO role (id, name, is_deleted) VALUES
(2, 'ROLE_USER', 0),
(1, 'ROLE_ADMIN', 0);

INSERT INTO area (id, name, is_deleted) VALUES
(1, 'Jižní Morava', 0),
(2, 'Severní Morava', 0),
(3, 'Východní Čechy ', 0),
(4, 'Západní Čechy', 0),
(5, 'Střední Čechy', 0);

INSERT INTO region (id, abbreviation, name, area_id, is_deleted) VALUES
(1, 'PHA', 'Hlavní město Praha', 5, 0),
(2, 'STČ', 'Středočeský kraj', 5, 0),
(3, 'JHČ', 'Jihočeský kraj', 4, 0),
(4, 'PLK', 'Plzeňský kraj', 4, 0),
(5, 'KVK', 'Karlovarský kraj', 4, 0),
(6, 'ULK', 'Ústecký kraj', 4, 0),
(7, 'LBK', 'Liberecký kraj', 3, 0),
(8, 'HKK', 'Královéhradecký kraj', 3, 0),
(9, 'PAK', 'Pardubický kraj', 3, 0),
(10, 'OLK', 'Olomoucký kraj', 2, 0),
(11, 'MSK', 'Moravskoslezský kraj', 2, 0),
(12, 'JHM', 'Jihomoravský kraj', 1, 0),
(13, 'ZLK', 'Zlínský kraj', 1, 0),
(14, 'VYS', 'Kraj Vysočina', 3, 0);

INSERT INTO user (id, email, username, password, firstname, surname, area_id, renew_task, role_id, is_deleted) VALUES
(1, 'admin@example.com', 'admin', '$2a$10$jd2BK8zYq5ySLaBsaFjpgOF5SsvDJkWHpFea4xpEmlHGzu2TjNuV2', NULL, NULL, NULL, false, 1, 0),
(2, 'user@example.com', 'user', '$2a$10$jd2BK8zYq5ySLaBsaFjpgOF5SsvDJkWHpFea4xpEmlHGzu2TjNuV2', 'Uživatel', 'Veselý', 3, false, 2, 0);

INSERT INTO carriage (id, serial_number, producer, color, home_station, is_deleted) VALUES
(1, 'SN054652', 'Škoda', 'Červená', 'Bohumín', 0),
(2, 'SN2255', 'Škoda', 'Zelená', 'Praha', 0);

INSERT INTO damage_type (id, name, is_deleted) VALUES
(NULL, 'Damage one', 0),
(NULL, 'Damage two', 0);

INSERT INTO fire_brigade_unit (id, name, is_deleted) VALUES
(1, 'Hasiči praha', 0),
(2, 'Hasiči Brno', 0),
(3, 'Hasiči Bohumín', 0);