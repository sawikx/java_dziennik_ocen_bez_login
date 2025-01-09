package projekt.dziennik_ocen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "klasy")
public class Klasy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_klasy", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nazwa_klasy", nullable = false, length = 10)
    private String nazwaKlasy;

    @ManyToOne
    @JoinColumn(name = "wychowawca_id")
    private Nauczyciele wychowawca;

    @Override
    public String toString() {
        return String.format("%s (Wychowawca: %s)", nazwaKlasy, wychowawca != null ? wychowawca.toString() : "---");
    }
}
