package projekt.dziennik_ocen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "przedmioty")
public class Przedmioty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_przedmiotu", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nazwa_przedmiotu", nullable = false, length = 50)
    private String nazwaPrzedmiotu;

    @Override
    public String toString() {
        return nazwaPrzedmiotu;
    }
}