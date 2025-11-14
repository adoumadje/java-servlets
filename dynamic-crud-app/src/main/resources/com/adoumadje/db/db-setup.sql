-- servlet database
DROP DATABASE IF EXISTS servletDb;
DROP USER IF EXISTS `servletUser`@`%`;
CREATE DATABASE IF NOT EXISTS servletDb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `servletUser`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `servletDb`.* TO `servletUser`@`%`;
FLUSH PRIVILEGES;

USE servletDb;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);