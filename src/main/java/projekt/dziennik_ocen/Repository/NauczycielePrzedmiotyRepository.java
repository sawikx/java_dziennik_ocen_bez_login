package projekt.dziennik_ocen.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import projekt.dziennik_ocen.model.NauczycielePrzedmioty;

import java.util.List;

public interface NauczycielePrzedmiotyRepository extends CrudRepository<NauczycielePrzedmioty, Integer> {

    void deleteByNauczyciel_IdAndPrzedmiot_Id(Integer nauczyciel, Integer przedmiot);

    List<NauczycielePrzedmioty> findByNauczyciel_Id(Integer nauczyciel);
    // Wyszukiwanie nauczycieli, którzy uczą określony przedmiot
    List<NauczycielePrzedmioty> findByPrzedmiotId(Integer przedmiot);
}
