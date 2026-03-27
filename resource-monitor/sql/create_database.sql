CREATE DATABASE IF NOT EXISTS resource_monitor_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE resource_monitor_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);