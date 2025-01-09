package projekt.dziennik_ocen.Repository;

import org.springframework.data.repository.CrudRepository;
import projekt.dziennik_ocen.model.Klasy;

public interface KlasyRepository extends CrudRepository<Klasy, Integer>{

    Klasy findByWychowawca_Id(Integer wychowawca); // Zapytanie, które zwraca klasę według ID wychowawcy

}
