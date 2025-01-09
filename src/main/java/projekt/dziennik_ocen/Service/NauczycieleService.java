package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.NauczycielePrzedmiotyRepository;
import projekt.dziennik_ocen.Repository.NauczycieleRepository;
import projekt.dziennik_ocen.Repository.PrzedmiotyRepository;
import projekt.dziennik_ocen.model.Nauczyciele;
import projekt.dziennik_ocen.model.NauczycielePrzedmioty;
import projekt.dziennik_ocen.model.Przedmioty;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class NauczycieleService {

    private final NauczycieleRepository nauczycieleRepository;
    private final NauczycielePrzedmiotyRepository nauczycielePrzedmiotyRepository;
    private final PrzedmiotyRepository przedmiotyRepository;

    public NauczycieleService(NauczycieleRepository nauczycieleRepository, NauczycielePrzedmiotyRepository nauczycielePrzedmiotyRepository, PrzedmiotyRepository przedmiotyRepository) {
        this.nauczycieleRepository = nauczycieleRepository;
        this.nauczycielePrzedmiotyRepository = nauczycielePrzedmiotyRepository;
        this.przedmiotyRepository = przedmiotyRepository;
    }
    public void updateNauczycielPrzedmioty(Nauczyciele nauczyciel, List<Integer> przedmiotyIds) {
        // Dodanie nowych przypisań
        for (Integer przedmiotId : przedmiotyIds) {
            Przedmioty przedmiot = przedmiotyRepository.findById(przedmiotId).orElse(null);
            if (przedmiot != null) {
                NauczycielePrzedmioty nauczycielPrzedmiot = new NauczycielePrzedmioty();
                nauczycielPrzedmiot.setNauczyciel(nauczyciel);
                nauczycielPrzedmiot.setPrzedmiot(przedmiot);
                nauczycielePrzedmiotyRepository.save(nauczycielPrzedmiot);
            }
        }
    }
    public Iterable<Nauczyciele> listAll() {
        return nauczycieleRepository.findAll();
    }

    public void save(Nauczyciele nauczyciel) {
        nauczycieleRepository.save(nauczyciel);
    }

    public Nauczyciele find(Integer id) {
        return nauczycieleRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        nauczycieleRepository.deleteById(id);
    }

    // Metoda do pobierania nauczycieli dla wybranego przedmiotu
    public List<Nauczyciele> findByPrzedmiot(Integer przedmiot) {
        List<NauczycielePrzedmioty> nauczycielePrzedmioty = nauczycielePrzedmiotyRepository.findByPrzedmiotId(przedmiot);
        return nauczycielePrzedmioty.stream()
                .map(NauczycielePrzedmioty::getNauczyciel) // Pobieramy nauczycieli powiązanych z przedmiotem
                .collect(Collectors.toList());
    }

}
