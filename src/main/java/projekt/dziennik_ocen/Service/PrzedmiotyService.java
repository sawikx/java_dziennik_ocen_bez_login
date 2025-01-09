package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.PrzedmiotyRepository;
import projekt.dziennik_ocen.model.Przedmioty;

import java.util.List;

@Service
@Transactional
public class PrzedmiotyService {

    private final PrzedmiotyRepository przedmiotyRepository;

    public PrzedmiotyService(PrzedmiotyRepository przedmiotyRepository) {
        this.przedmiotyRepository = przedmiotyRepository;
    }

    public Iterable<Przedmioty> listAll() {
        return przedmiotyRepository.findAll();
    }

    public void save(Przedmioty przedmiot) {
        przedmiotyRepository.save(przedmiot);
    }

    public Przedmioty find(Integer id) {
        return przedmiotyRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        przedmiotyRepository.deleteById(id);
    }
}
