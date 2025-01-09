package projekt.dziennik_ocen.Repository;

import org.springframework.data.repository.CrudRepository;
import projekt.dziennik_ocen.model.Nauczyciele;

import java.util.Optional;


public interface NauczycieleRepository extends CrudRepository<Nauczyciele, Integer>{

    Optional<Nauczyciele> findByLogin(String login);
}
