package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.NauczycielePrzedmiotyRepository;
import projekt.dziennik_ocen.model.NauczycielePrzedmioty;

import java.util.List;

@Service
@Transactional
public class NauczycielePrzedmiotyService {

    private final NauczycielePrzedmiotyRepository nauczycielePrzedmiotyRepository;

    public NauczycielePrzedmiotyService(NauczycielePrzedmiotyRepository nauczycielePrzedmiotyRepository) {
        this.nauczycielePrzedmiotyRepository = nauczycielePrzedmiotyRepository;
    }

    public Iterable<NauczycielePrzedmioty> listAll() {
        return nauczycielePrzedmiotyRepository.findAll();
    }

    public void save(NauczycielePrzedmioty nauczycielPrzedmiot) {
        nauczycielePrzedmiotyRepository.save(nauczycielPrzedmiot);
    }

    public NauczycielePrzedmioty find(Integer id) {
        return nauczycielePrzedmiotyRepository.findById(id).orElse(null);
    }

    public List<NauczycielePrzedmioty> getnauczycielePrzedmiotyIdsByNauczycielId(Integer nauczyciel) {
        return nauczycielePrzedmiotyRepository.findByNauczyciel_Id(nauczyciel);
    }
    public void deleteByNauczycielAndPrzedmiot(Integer nauczycielId, Integer przedmiotId) {
        nauczycielePrzedmiotyRepository.deleteByNauczyciel_IdAndPrzedmiot_Id(nauczycielId, przedmiotId);
    }

    public void delete(Integer id) {
        nauczycielePrzedmiotyRepository.deleteById(id);
    }


}
