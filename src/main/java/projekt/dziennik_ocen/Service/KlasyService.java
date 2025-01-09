package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.KlasyRepository;
import projekt.dziennik_ocen.Repository.UczniowieRepository;
import projekt.dziennik_ocen.model.Klasy;

@Service
@Transactional
public class KlasyService {

    private final KlasyRepository klasyRepository;
    private  final UczniowieRepository uczniowieRepository;

    public KlasyService(KlasyRepository klasyRepository, UczniowieRepository uczniowieRepository) {
        this.klasyRepository = klasyRepository;
        this.uczniowieRepository = uczniowieRepository;
    }

    public Iterable<Klasy> listAll() {
        return klasyRepository.findAll();
    }

    public void save(Klasy klasa) {
        klasyRepository.save(klasa);
    }

    public Klasy find(Integer id) {
        return klasyRepository.findById(id).orElse(null);
    }

    public Klasy findClassByWychowawcaId(Integer nauczycielId) {
        return klasyRepository.findByWychowawca_Id(nauczycielId); // Metoda, która zwraca klasę wychowawcza dla nauczyciela
    }

    public void delete(Integer id) {
        klasyRepository.deleteById(id);
    }

    public long countStudentsInClass(Integer klasaId) {
        return uczniowieRepository.countByKlasa_Id(klasaId);
    }

}
