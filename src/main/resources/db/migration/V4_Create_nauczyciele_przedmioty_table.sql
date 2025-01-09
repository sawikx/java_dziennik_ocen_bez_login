CREATE TABLE nauczyciele_przedmioty (
    id_nauczyciela INT,
    id_przedmiotu INT,
    PRIMARY KEY (id_nauczyciela, id_przedmiotu),
    FOREIGN KEY (id_nauczyciela) REFERENCES nauczyciele(id_nauczyciela),
    FOREIGN KEY (id_przedmiotu) REFERENCES przedmioty(id_przedmiotu)
) engine=InnoDB;

INSERT INTO nauczyciele_przedmioty (id_nauczyciela, id_przedmiotu) VALUES
(1, 1); -- Anna Nowak uczy Matematyki
INSERT INTO nauczyciele_przedmioty (id_nauczyciela, id_przedmiotu) VALUES
(1, 2); -- Anna Nowak uczy Biologii
INSERT INTO nauczyciele_przedmioty (id_nauczyciela, id_przedmiotu) VALUES
(2, 2); -- Jan Kowalski uczy Biologii
INSERT INTO nauczyciele_przedmioty (id_nauczyciela, id_przedmiotu) VALUES
(3, 3); -- Katarzyna Wiśniewska uczy Historii