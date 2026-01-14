CREATE DATABASE IF NOT EXISTS testdb;

USE testdb;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL
);

INSERT INTO user (nom, email) VALUES 
    ('Taha Raihane', 'taha.raihane@student.uic.ac.ma');

SELECT * FROM user;
