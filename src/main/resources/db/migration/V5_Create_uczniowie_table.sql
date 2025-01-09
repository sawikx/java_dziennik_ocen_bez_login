CREATE TABLE uczniowie (
    id_ucznia INT AUTO_INCREMENT PRIMARY KEY,
    imie VARCHAR(50) NOT NULL,
    nazwisko VARCHAR(50) NOT NULL,
    id_klasy INT,
    FOREIGN KEY (id_klasy) REFERENCES klasy(id_klasy)
) engine=InnoDB;

INSERT INTO uczniowie (imie, nazwisko, id_klasy) VALUES ('Piotr', 'Kowalski', 1);
INSERT INTO uczniowie (imie, nazwisko, id_klasy) VALUES ('Marta', 'Nowakowska', 2);