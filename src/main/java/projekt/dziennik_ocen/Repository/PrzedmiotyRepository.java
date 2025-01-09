package projekt.dziennik_ocen.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import projekt.dziennik_ocen.model.Przedmioty;

import java.util.List;

public interface PrzedmiotyRepository extends CrudRepository<Przedmioty, Integer> {

}
