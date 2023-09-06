CREATE DATABASE GenesisResources;

USE GenesisResources;

CREATE TABLE Persons(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100),
	surname VARCHAR(100),
	personID VARCHAR(100) UNIQUE,
	uuid VARCHAR(100) UNIQUE
);

INSERT INTO Persons (name, surname, personID, uuid) VALUES("John","Doe","jXa4g3H7oPq2",UUID());
INSERT INTO Persons (name, surname, personID, uuid) VALUES("Jake","Sully","yB9fR6tK0wLm",UUID());
INSERT INTO Persons (name, surname, personID, uuid) VALUES("Adam","Smith","cN1vZ8pE5sYx",UUID());

SELECT id, name, surname FROM Persons WHERE id = 1;
SELECT id, name, surname, personID, uuid FROM Persons WHERE id = 1;

SELECT * FROM Persons;
SELECT id, name, surname FROM Persons;

UPDATE Persons SET name = "Jane", surname = "Smith" WHERE id = 3;

DELETE FROM Persons WHERE id=3;

DELETE FROM Persons ;
ALTER TABLE Persons AUTO_INCREMENT = 1;

