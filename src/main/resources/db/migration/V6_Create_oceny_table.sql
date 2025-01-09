CREATE TABLE oceny (
    id_oceny INT AUTO_INCREMENT PRIMARY KEY,
    id_ucznia INT,
    id_przedmiotu INT,
    id_nauczyciela INT,
    ocena INT NOT NULL,
    data_oceny DATE NOT NULL,
    FOREIGN KEY (id_ucznia) REFERENCES uczniowie(id_ucznia),
    FOREIGN KEY (id_przedmiotu) REFERENCES przedmioty(id_przedmiotu),
    FOREIGN KEY (id_nauczyciela) REFERENCES nauczyciele(id_nauczyciela)
) engine=InnoDB;

INSERT INTO oceny (id_ucznia, id_przedmiotu, id_nauczyciela, ocena, data_oceny) VALUES
(1, 1, 1, 5, '2024-12-01');
INSERT INTO oceny (id_ucznia, id_przedmiotu, id_nauczyciela, ocena, data_oceny) VALUES
(2, 2, 2, 4, '2024-12-02');