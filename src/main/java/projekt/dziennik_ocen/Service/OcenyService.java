package projekt.dziennik_ocen.Service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projekt.dziennik_ocen.Repository.*;
import projekt.dziennik_ocen.model.Oceny;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class OcenyService {

    private final OcenyRepository ocenyRepository;

    public OcenyService(OcenyRepository ocenyRepository, PrzedmiotyRepository przedmiotyRepository) {
        this.ocenyRepository = ocenyRepository;
    }

    public Map<String, Map<String, List<Oceny>>> groupByClassAndSubject(Integer klasaId) {
        Iterable<Oceny> allOceny = ocenyRepository.findAll();

        return StreamSupport.stream(allOceny.spliterator(), false)
                .filter(ocena -> ocena.getUczen().getKlasa().getId().equals(klasaId))
                .collect(Collectors.groupingBy(
                        ocena -> ocena.getUczen().getKlasa().getNazwaKlasy(),
                        Collectors.groupingBy(ocena -> ocena.getPrzedmiot().getNazwaPrzedmiotu())
                ));
    }

    public Iterable<Oceny> listAll() {
        return ocenyRepository.findAll();
    }

    public void save(Oceny ocena) {
        ocenyRepository.save(ocena);
    }

    public Oceny find(Integer id) {
        return ocenyRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        ocenyRepository.deleteById(id);
    }
}
