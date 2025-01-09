package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.UczniowieRepository;
import projekt.dziennik_ocen.model.Uczniowie;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UczniowieService {

    private final UczniowieRepository uczniowieRepository;

    public UczniowieService(UczniowieRepository uczniowieRepository) {
        this.uczniowieRepository = uczniowieRepository;
    }

    public Iterable<Uczniowie> listAll() {
        return uczniowieRepository.findAll();
    }

    public void save(Uczniowie uczen) {
        uczniowieRepository.save(uczen);
    }

    public Uczniowie find(Integer id) {
        return uczniowieRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        uczniowieRepository.deleteById(id);
    }

    public List<Uczniowie> findByKlasaId(Integer klasaId) {
        return uczniowieRepository.findByKlasaId(klasaId); // zakładając, że masz metodę findByKlasaId w repozytorium
    }
}

