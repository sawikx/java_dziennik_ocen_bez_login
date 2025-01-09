CREATE TABLE klasy (
    id_klasy INT AUTO_INCREMENT PRIMARY KEY,
    nazwa_klasy VARCHAR(10) NOT NULL,
    wychowawca_id INT,
    FOREIGN KEY (wychowawca_id) REFERENCES nauczyciele(id_nauczyciela)
) engine=InnoDB;

INSERT INTO klasy (nazwa_klasy, wychowawca_id) VALUES
('1A', 1);
INSERT INTO klasy (nazwa_klasy, wychowawca_id) VALUES
('1B', 2);