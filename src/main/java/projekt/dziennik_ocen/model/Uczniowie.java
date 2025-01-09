package projekt.dziennik_ocen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "uczniowie")
public class Uczniowie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ucznia", unique = true, nullable = false)
    private Integer id;

    @Column(name = "imie", nullable = false, length = 50)
    private String imie;

    @Column(name = "nazwisko", nullable = false, length = 50)
    private String nazwisko;

    @ManyToOne
    @JoinColumn(name = "id_klasy")
    private Klasy klasa;

    // Klasa Uczniowie
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = true)
    private String role = "STUDENT"; // Domyślna wartość


    @Override
    public String toString() {
        return String.format("%s %s (%s)", imie, nazwisko, klasa != null ? klasa.getNazwaKlasy() : "---");
    }
}
