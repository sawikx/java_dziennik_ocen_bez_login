CREATE TABLE przedmioty (
    id_przedmiotu INT AUTO_INCREMENT PRIMARY KEY,
    nazwa_przedmiotu VARCHAR(50) NOT NULL
) engine=InnoDB;

INSERT INTO przedmioty (nazwa_przedmiotu) VALUES
('Matematyka');
INSERT INTO przedmioty (nazwa_przedmiotu) VALUES
('Biologia');
INSERT INTO przedmioty (nazwa_przedmiotu) VALUES
('Historia');