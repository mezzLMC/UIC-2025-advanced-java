CREATE DATABASE IF NOT EXISTS testdb;

USE testdb;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL
);

INSERT INTO user (nom, email) VALUES 
    ('Mohamed Mazouz', 'mohamed.mazouz@student.uic.ac.ma'),
    ('Antonin Campi', 'antonin.campi@student.uic.ac.ma'),
    ('Mathis Deruywe', 'mathis.deruywe@student.uic.ac.ma');

SELECT * FROM user;
