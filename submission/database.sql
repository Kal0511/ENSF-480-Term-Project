DROP DATABASE IF EXISTS ENSF480;
CREATE DATABASE ENSF480; 
USE ENSF480;

DROP TABLE IF EXISTS FEEINFO;
CREATE TABLE FEEINFO (
    integrity_keeper ENUM('') NOT NULL PRIMARY KEY,
    price INT unsigned NOT NULL,
    perioddays INT unsigned NOT NULL
);
INSERT INTO FEEINFO (price, perioddays)
VALUES
    (80, 60);

DROP TABLE IF EXISTS SUBSCRIPTIONS;
DROP TABLE IF EXISTS EMAILS;
DROP TABLE IF EXISTS PROPERTIES;
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
	username        VARCHAR(150) NOT NULL,
    password        VARCHAR(150) NOT NULL,
    accounttype     VARCHAR(150) NOT NULL,
    PRIMARY KEY     (username)
);

INSERT INTO USERS (username, password, accounttype)
VALUES
    ('landlord1', 'ensf480', 'landlord'),
    ('landlord2', 'ensf480', 'landlord'),
    ('landlord3', 'ensf480', 'landlord'),
    ('moussavifan', 'ensf480', 'landlord'),
    ('renter1', 'ensf480', 'renter'),
    ('renter2', 'ensf480', 'renter'),
    ('renter3', 'ensf480', 'renter'),
    ('manager', 'ensf480', 'manager');

CREATE TABLE SUBSCRIPTIONS (
    id INT unsigned NOT NULL AUTO_INCREMENT,
    renter VARCHAR(150) NOT NULL,
    type VARCHAR(150) NOT NULL,
    bedrooms INT unsigned NOT NULL,
    bathrooms INT unsigned NOT NULL,
    furnished INT NOT NULL,
    quadrant VARCHAR(3) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (renter) REFERENCES USERS(username) ON UPDATE CASCADE
);
CREATE TABLE EMAILS (
    sender VARCHAR(150) NOT NULL,
    receiver VARCHAR(150) NOT NULL,
    message VARCHAR(200) NOT NULL,
    FOREIGN KEY (sender) REFERENCES USERS(username) ON UPDATE CASCADE,
    FOREIGN KEY (receiver) REFERENCES USERS(username) ON UPDATE CASCADE
);

DROP TABLE IF EXISTS NOTIFICATIONS;
DROP TABLE IF EXISTS RENTEDHISTORY;
DROP TABLE IF EXISTS PROPERTYLOGS;
CREATE TABLE PROPERTIES (
    id INT unsigned NOT NULL AUTO_INCREMENT,
    address VARCHAR(150) NOT NULL,
    owner VARCHAR(150) NOT NULL,
    type VARCHAR(150) NOT NULL,
    bedrooms INT unsigned NOT NULL,
    bathrooms INT unsigned NOT NULL,
    furnished INT NOT NULL,
    quadrant VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    expirydate DATE NOT NULL,
    PRIMARY KEY (id), 
    FOREIGN KEY (owner) REFERENCES USERS(username) ON UPDATE CASCADE
  );

INSERT INTO PROPERTIES (address, owner, type, bedrooms, bathrooms, furnished, quadrant, status, expirydate)
VALUES
    ('111 North ST',        'moussavifan',   'apartment',	        1,	2,	1,	'NE',	'active',   '2022-1-1'),
    ('23 North Blvd',       'moussavifan',   'apartment',	        1,	1,	1,	'NE',	'active',   '2021-11-1'),
    ('100 St Albert Rd',    'moussavifan',   'attached house',	    4,	5,	0,	'NW',	'active',   '2022-1-1'),
    ('22 Shawnnesay Rd',    'moussavifan',   'attached house',	    4,	6,	1,	'NW',	'occupied', '2022-1-1'),
    ('33 Chinook Hhwy',     'moussavifan',   'townhouse',	        6,	7,	1,	'SE',	'active',   '2022-1-1'),
    ('1 St SE',             'moussavifan',   'townhouse',	        2,	3,	0,	'SW',	'active',   '2021-11-1'),
    ('123 Fake St',         'landlord1',      'detached house',	    1,	3,	0,	'NE',	'occupied', '2022-1-1'),
    ('401 Error Blvd',      'landlord1',      'attached house',	    4,	4,	1,	'SE',	'active',   '2022-1-1'),
    ('420 Lit Rd',          'landlord1',      'townhouse',	        3,	4,	1,	'SE',	'active',   '2021-11-1'),
    ('69 Nice River',       'landlord1',      'attached house',	    1,	2,	0,	'NE',	'active',   '2022-1-1'),
    ('100 Uni Death Way',   'landlord1',      'detached house',	    1,	1,	1,	'SW',	'active',   '2022-1-1'),
    ('4 Gpa Park',          'landlord2',     'apartment',	        4,	5,	1,	'SW',	'occupied', '2021-11-1'),
    ('87 South Park',       'landlord2',      'apartment',	        4,	5,	1,	'SW',	'occupied', '2022-1-1'),
    ('29 Ph Dee Highway',   'landlord2',      'detached house',	    1,	1,	0,	'SE',	'active',   '2022-1-1'),
    ('21 Northmount Dr',    'landlord3',      'townhouse',	        2,	3,	0,	'NW',	'active',   '2021-11-1'),
    ('2 St SW',             'moussavifan',   'townhouse',	        1,	2,	1,	'SW',	'active',   '2022-1-1');



CREATE TABLE NOTIFICATIONS (
    subscriptionid INT unsigned NOT NULL,
    listingid INT unsigned NOT NULL,
    renter VARCHAR(150) NOT NULL,
    FOREIGN KEY (subscriptionid) REFERENCES SUBSCRIPTIONS(id) ON UPDATE CASCADE,
    FOREIGN KEY (listingid) REFERENCES PROPERTIES(id) ON UPDATE CASCADE,
    FOREIGN KEY (renter) REFERENCES USERS(username) ON UPDATE CASCADE
);

CREATE TABLE PROPERTYLOGS (
    
    listingid INT unsigned NOT NULL,
    statuschange VARCHAR(20) NOT NULL,
    logdate DATE NOT NULL,
    FOREIGN KEY (listingid) REFERENCES PROPERTIES(id) ON DELETE CASCADE
);


