package projekt.dziennik_ocen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "oceny")
public class Oceny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oceny", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_ucznia", nullable = false)
    private Uczniowie uczen;

    @ManyToOne
    @JoinColumn(name = "id_przedmiotu", nullable = false)
    private Przedmioty przedmiot;

    @ManyToOne
    @JoinColumn(name = "id_nauczyciela", nullable = false)
    private Nauczyciele nauczyciel;

    @Column(name = "ocena", nullable = false)
    private Integer ocena;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_oceny", nullable = false)
    private Date dataOceny;

    @Override
    public String toString() {
        return String.format(
                "Ocena: %d, Uczeń: %s, Przedmiot: %s, Nauczyciel: %s, Data: %s",
                ocena,
                uczen != null ? uczen.toString() : "---",
                przedmiot != null ? przedmiot.toString() : "---",
                nauczyciel != null ? nauczyciel.toString() : "---",
                dataOceny
        );
    }
}