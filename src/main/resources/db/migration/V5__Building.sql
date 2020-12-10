ALTER TABLE `security_incident` CHANGE `carriage_id` `carriage_id` INT NULL DEFAULT NULL;
ALTER TABLE `security_incident` ADD `building_id` INT NULL  DEFAULT NULL AFTER `carriage_id`;

create table building (
  ID int not null auto_increment,
  ELECT_SWITCHBOARD varchar(50) not null,
  GAS_SHUT_OFF varchar(50) not null,
  WATER_SHUT_OFF varchar(50) not null,
  ADDRESS varchar(100) not null,
  BUILDING_MANAGER varchar(50) not null,
  GPS varchar(30) not null,
  OWNER varchar(50) not null,
  INNERNO varchar(12) not null,
  IS_DELETED boolean not null,
  primary key (ID),
  constraint UK_S8BE1KS7YUUCFBYJYA3C887T8
    unique (INNERNO)
);
create table eps (
  ID int not null auto_increment,
  LOCATION varchar(30) not null,
  TYPE varchar(50) not null,
  BUILDING_ID int not null,
  primary key (ID),
  constraint FKFVMQ01NKB6IMB3T0M9JQVV8H1
    foreign key (BUILDING_ID)
    references building (ID)
);
create table technical_system (
  ID int not null auto_increment,
  LOCATION varchar(50) not null,
  MANUFACTURER varchar(50) not null,
  NAME varchar(50) not null,
  SYSTEM_TYPE varchar(30) not null,
  BUILDING_ID int not null,
  primary key (ID),
  constraint FK4F76ATPXCNYMJ8S7WNWV12IAG
    foreign key (BUILDING_ID)
    references building (ID)
);
create table tel_number (
  ID int not null auto_increment,
  NAME varchar(100) not null,
  NUMBER varchar(15) not null,
  BUILDING_ID int not null,
  primary key (ID),
  constraint FK2A1U7NNV7HOCCMAXNGKE19APQ
    foreign key (BUILDING_ID)
    references building (ID)
);
create table file (
  ID int not null auto_increment,
  CONTENT mediumblob not null,
  CONTENT_TYPE varchar(255) not null,
  CREATED timestamp not null,
  FILENAME varchar(150) not null,
  BUILDING_ID int not null,
  primary key (ID),
  constraint FK48HFQQ8RJ5GHK3TVPMYXHVQPG
    foreign key (BUILDING_ID)
    references building (ID)
);
create table fire_extinguisher (
  ID int not null auto_increment,
  LAST_CHECK_DATE timestamp not null,
  LOCATION varchar(30) not null,
  TYPE varchar(255),
  BUILDING_ID int not null,
  primary key (ID),
  constraint FKD1RL2K7VXHRH9E6GQTDF85HVW
    foreign key (BUILDING_ID)
    references building (ID)
);
create table hydrant (
  ID int not null auto_increment,
  FLOW_RATE float not null,
  LAST_CHECK_DATE timestamp not null,
  LOCATION varchar(30) not null,
  NAME varchar(30) not null,
  BUILDING_ID int not null,
  primary key (ID),
  constraint FKMIA2M4VI6OIDMX8XVLHGONPYS
    foreign key (BUILDING_ID)
    references building (ID)
);


ALTER TABLE security_incident
ADD FOREIGN KEY (building_id) REFERENCES building (ID);

INSERT INTO building (ID, ELECT_SWITCHBOARD, GAS_SHUT_OFF, WATER_SHUT_OFF, ADDRESS, BUILDING_MANAGER, GPS, OWNER, INNERNO, IS_DELETED) VALUES (1, 'Prvni patro chodba', 'Kotelna', 'Neni v budove', 'Strakonicka 1899 Praha 2', 'Jan Z Rokycan', '54.123465 78.78945', 'CD Cargo', 'BD1234', false);

INSERT INTO eps (ID, LOCATION, TYPE, BUILDING_ID) VALUES (1, 'Druhe patro', 'Ustredna EPS', 1);

INSERT INTO tel_number (ID, NAME, NUMBER, BUILDING_ID) VALUES (1, 'Majitel', '789789789', 1);

INSERT INTO hydrant (ID, FLOW_RATE, LAST_CHECK_DATE, LOCATION, NAME, BUILDING_ID) VALUES (1, 15, '2020-12-04 18:10:00.000000', 'Chodba priyemi', 'Vodni hyrant', 1);

INSERT INTO fire_extinguisher (ID, LAST_CHECK_DATE, LOCATION, TYPE, BUILDING_ID) VALUES (1, '2020-12-05 20:12:00.000000', 'Kotelna v prvnim patre', 'DRYPOWDER', 1);
INSERT INTO fire_extinguisher (ID, LAST_CHECK_DATE, LOCATION, TYPE, BUILDING_ID) VALUES (2, '2020-12-05 20:10:00.000000', 'Kotelna v prvnim patre', 'WATER', 1);

INSERT INTO technical_system (ID, LOCATION, MANUFACTURER, NAME, SYSTEM_TYPE, BUILDING_ID) VALUES (1, 'Kotelna dole', 'Jablotron', 'Cidlo pohybu', 'Ochrana', 1);



