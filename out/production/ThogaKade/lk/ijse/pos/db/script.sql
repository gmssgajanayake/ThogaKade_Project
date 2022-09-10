SHOW
DATABASES ;
CREATE DATABASE Thogakade;
USE Thogakade;
CREATE TABLE system_user
(
    name     VARCHAR(45)  NOT NULL,
    email    VARCHAR(100) NOT NULL PRIMARY KEY,
    password VARCHAR(1000) NOT NULL
);
DESC system_user;

CREATE TABLE customer
(
    id      VARCHAR(45) NOT NULL PRIMARY KEY,
    name    VARCHAR(45) NOT NULL,
    address TEXT,
    salary  DECIMAL(10, 2) DEFAULT 0
);
DESCRIBE customer;
