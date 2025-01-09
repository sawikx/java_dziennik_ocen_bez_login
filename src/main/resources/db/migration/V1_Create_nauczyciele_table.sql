CREATE TABLE nauczyciele (
    id_nauczyciela INT AUTO_INCREMENT PRIMARY KEY,
    imie VARCHAR(50) NOT NULL,
    nazwisko VARCHAR(50) NOT NULL,
    FOREIGN KEY (przedmiot_id) REFERENCES przedmioty(id_przedmiotu)
) engine=InnoDB;

INSERT INTO nauczyciele (imie, nazwisko) VALUES
('Anna', 'Nowak');
INSERT INTO nauczyciele (imie, nazwisko) VALUES
('Jan', 'Kowalski');
INSERT INTO nauczyciele (imie, nazwisko) VALUES
('Katarzyna', 'Wiśniewska');