package projekt.dziennik_ocen.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import projekt.dziennik_ocen.model.Uczniowie;

import java.util.List;
import java.util.Optional;

public interface UczniowieRepository extends CrudRepository<Uczniowie, Integer> {
    long countByKlasa_Id(Integer klasa);
    List<Uczniowie> findByKlasaId(Integer klasaId);
    Optional<Uczniowie> findByLogin(String login);
}
