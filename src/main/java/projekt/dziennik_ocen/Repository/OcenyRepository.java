package projekt.dziennik_ocen.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import projekt.dziennik_ocen.model.Oceny;

import java.util.List;

public interface OcenyRepository extends CrudRepository<Oceny, Integer> {
    @Query("SELECT o FROM Oceny o WHERE o.uczen.klasa.id = :klasaId")
    List<Oceny> findByClassId(@Param("klasaId") Integer klasaId);
}
