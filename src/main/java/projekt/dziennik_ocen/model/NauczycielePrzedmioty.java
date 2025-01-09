package projekt.dziennik_ocen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "nauczyciele_przedmioty")
public class NauczycielePrzedmioty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_nauczyciela", nullable = false)
    private Nauczyciele nauczyciel;

    @ManyToOne
    @JoinColumn(name = "id_przedmiotu", nullable = false)
    private Przedmioty przedmiot;
}
