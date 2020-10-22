CREATE TABLE IF NOT EXISTS user
(
    id        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    firstname VARCHAR(50),
    surename  VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS role
(
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS region
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS kraj
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS incident
(
    id               INT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    creationDatetime DATETIME      NOT NULL,
    location         VARCHAR(505)  NOT NULL,
    note             VARCHAR(5000) NOT NULL,
    comment          VARCHAR(5000)
);

CREATE TABLE IF NOT EXISTS security_incident
(
    id       INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    checked  BOOL NOT NULL,
    crime    BOOL NOT NULL,
    police   BOOL NOT NULL,
    carriage VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS premise_incident
(
    id        INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    validFrom DATETIME NOT NULL,
    validTo   DATETIME NOT NULL
);

