/*DELETE FROM damage;
DELETE FROM file;
DELETE FROM incident;
DELETE FROM security_incident;
DELETE FROM fire_incident;
DELETE FROM premise_incident;
DELETE FROM carriage;
DELETE FROM eps;
DELETE FROM tel_number;
DELETE FROM technical_system;
DELETE FROM building;
DELETE FROM damage_type;
DELETE FROM fire_brigade_unit;*/

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

/*INSERT INTO carriage (id, serial_number, producer, color, home_station, is_deleted, weight, length, depo) VALUES
(1, '18 26 7744 123-5', 'Krnovské opravny a strojírny s.r.o.', 'Červená', 'Pardubice hl. n.', 0, 22.5, 18.8, 'DKV Pardubice'),
(2, '22 17 6541 889-0', 'ŠKODA VAGONKA a.s.', 'Zelená', 'Praha hl. n.', 0, 18.6, 17, 'DKV Praha'),
(3, '28 36 8732 763-6', 'CZ LOKO a.a.', 'Černá', 'Bohumín', 0, 20.2, 18.3, 'DKV Bohumín');

INSERT INTO damage_type (id, name, is_deleted) VALUES
(NULL, 'Damage one', 0),
(NULL, 'Damage two', 0);

INSERT INTO fire_brigade_unit (id, name, is_deleted) VALUES
(1, 'Hasiči praha', 0),
(2, 'Hasiči Brno', 0),
(3, 'Hasiči Bohumín', 0);

INSERT INTO building (id, innerno, address, gps, owner, building_manager, gas_shut_off, water_shut_off, elect_switchboard, is_deleted) VALUES
(1, '325687990123', 'Husova 344, Plzeň, PLK, 12376', '54.123465 78.78945', 'CD Cargo', 'Jan Novák', 'Chodba, 1. patro', 'Přízemí', 'Přízemí', 0),
(2, '112287990123', 'Nová 15, Praha, PHA, 11122', '55.123465 77.78945', 'CD Cargo', 'Jiří Nový', 'Chodba, 1. patro', 'Přízemí', 'Přízemí', 0);

INSERT INTO tel_number (id, name, number, building_id) VALUES
(1, 'Pohotovost', '123456789', 1);

INSERT INTO technical_system (id, name, system_type, location, manufacturer, building_id) VALUES
(1, 'Čidlo pohybu', 'Čidlo', 'Přízemí', 'Elektrobock CZ s.r.o.', 1),
(2, 'Kamerový systém', 'Kamera', 'Přízemí', 'Sikur Systems s.r.o.', 1),
(3, 'PCO', 'PCO', '1. patro', 'D. I. Seven', 1),
(4, 'Čidlo pohybu 2', 'Čidlo', '1. patro', 'Elektrobock CZ s.r.o.', 1);

INSERT INTO eps (id, type, location, building_id) VALUES
(1, 'EPS 1', '2. patro', 1);

INSERT INTO security_incident (id, checked, crime, police, manager_id, carriage_id, building_id, railroad_id) VALUES
(1, 0, 1, 1, 2, 1, 1, 1);

INSERT INTO incident (id, creationDateTime, location, note, description, owner_id, region_id, securityIncident_id) VALUES
(1, '2012-11-03T10:15:30', 'Pardubice hl. n.', 'Zkontrolovat', 'Rozbité okno', 2, 9, 1);*/