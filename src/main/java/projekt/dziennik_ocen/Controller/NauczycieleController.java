package projekt.dziennik_ocen.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.DTO.NauczycieleDTO;
import projekt.dziennik_ocen.Repository.NauczycieleRepository;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.*;
import projekt.dziennik_ocen.Service.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nauczyciele")
public class NauczycieleController {

    private final NauczycieleService nauczycieleService;
    private final PrzedmiotyService przedmiotyService;
    private final NauczycielePrzedmiotyService nauczycielePrzedmiotyService;
    private final KlasyService klasyService;

    public NauczycieleController(NauczycieleService nauczycieleService, PrzedmiotyService przedmiotyService, NauczycielePrzedmiotyService nauczycielePrzedmiotyService, KlasyService klasyService) {
        this.nauczycieleService = nauczycieleService;
        this.przedmiotyService = przedmiotyService;
        this.nauczycielePrzedmiotyService = nauczycielePrzedmiotyService;
        this.klasyService = klasyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Nauczyciele> nauczycieleList = (List<Nauczyciele>) nauczycieleService.listAll();

        // Tworzymy mapę, która przechowa informację, czy nauczyciel jest wychowawcą oraz jakiej klasy
        Map<Integer, String> wychowawcyInfo = new HashMap<>();
        for (Nauczyciele nauczyciel : nauczycieleList) {
            Klasy wychowawcaKlasa = klasyService.findClassByWychowawcaId(nauczyciel.getId());
            if (wychowawcaKlasa != null) {
                wychowawcyInfo.put(nauczyciel.getId(), wychowawcaKlasa.getNazwaKlasy());
            } else {
                wychowawcyInfo.put(nauczyciel.getId(), "Brak");
            }
        }
        model.addAttribute("listNauczyciele", nauczycieleList);
        model.addAttribute("lnp", nauczycielePrzedmiotyService.listAll()); // lista relacji nauczyciele-przedmioty
        model.addAttribute("wychowawcyInfo",wychowawcyInfo);
        return "nauczyciele/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("nauczyciel", new Nauczyciele());
        model.addAttribute("listPrzedmioty", przedmiotyService.listAll());
        model.addAttribute("klasy", klasyService.listAll());
        return "nauczyciele/new";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("nauczyciel") Nauczyciele nauczyciel,
                       @RequestParam(value = "przedmioty", required = false) List<Integer> przedmiotyIds,
                       @RequestParam(value = "klasaId", required = false) Integer klasaId) {

        // Zapisz nauczyciela
        nauczycieleService.save(nauczyciel);

        // Przypisanie nauczyciela do przedmiotów
        if (przedmiotyIds != null) {
            nauczycieleService.updateNauczycielPrzedmioty(nauczyciel, przedmiotyIds);
        }

        // Jeśli klasa została wybrana, przypisz nauczyciela jako wychowawcę
        if (klasaId != null) {
            Klasy klasa = klasyService.find(klasaId);
            if (klasa != null) {
                klasa.setWychowawca(nauczyciel); // Przypisanie wychowawcy do klasy
                klasyService.save(klasa); // Zapisz zmiany w klasie
            }
        }

        return "redirect:/nauczyciele/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) throws NotFoundException {
        Nauczyciele nauczyciel = nauczycieleService.find(id);
        if (nauczyciel == null) {
            throw new NotFoundException("Nauczyciel nie znaleziony");
        }
        List<NauczycielePrzedmioty> przypisanePrzedmioty = nauczycielePrzedmiotyService.getnauczycielePrzedmiotyIdsByNauczycielId(id);
        List<Przedmioty> wszystkiePrzedmioty = (List<Przedmioty>) przedmiotyService.listAll();

        // Filtracja dostępnych przedmiotów
        List<Przedmioty> dostepnePrzedmioty = wszystkiePrzedmioty.stream()
                .filter(przedmiot -> przypisanePrzedmioty.stream()
                        .noneMatch(nauczycielPrzedmiot -> nauczycielPrzedmiot.getPrzedmiot().getId().equals(przedmiot.getId())))
                .toList();

        model.addAttribute("nauczyciel", nauczyciel);
        model.addAttribute("przypisanePrzedmioty", przypisanePrzedmioty);
        model.addAttribute("dostepnePrzedmioty", dostepnePrzedmioty);
        model.addAttribute("klasy", klasyService.listAll());
        return "nauczyciele/edit";
    }

    @PostMapping("/addSubject/{nauczycielId}/{przedmiotId}")
    public String addSubject(@PathVariable Integer nauczycielId, @PathVariable Integer przedmiotId) {
        Nauczyciele nauczyciel = nauczycieleService.find(nauczycielId);
        Przedmioty przedmiot = przedmiotyService.find(przedmiotId);

        NauczycielePrzedmioty nauczycielPrzedmiot = new NauczycielePrzedmioty();
        nauczycielPrzedmiot.setNauczyciel(nauczyciel);
        nauczycielPrzedmiot.setPrzedmiot(przedmiot);
        nauczycielePrzedmiotyService.save(nauczycielPrzedmiot);

        return "redirect:/nauczyciele/edit/" + nauczycielId;
    }

    @PostMapping("/removeSubject/{nauczycielId}/{przedmiotId}")
    public String removeSubject(@PathVariable Integer nauczycielId, @PathVariable Integer przedmiotId) {
        nauczycielePrzedmiotyService.deleteByNauczycielAndPrzedmiot(nauczycielId, przedmiotId);
        return "redirect:/nauczyciele/edit/" + nauczycielId;
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable(name = "id") int id) {
        nauczycieleService.delete(id);
        return "redirect:/nauczyciele/";
    }

    @GetMapping("/byPrzedmiot/{przedmiotId}")
    @ResponseBody
    public List<NauczycieleDTO> getNauczycieleByPrzedmiot(@PathVariable Integer przedmiotId) {
        // Sprawdzenie poprawności przekazanego przedmiotu
        System.out.println("Pobieram nauczycieli dla przedmiotu ID: " + przedmiotId);

        List<Nauczyciele> nauczyciele = nauczycieleService.findByPrzedmiot(przedmiotId);

        // Jeśli nie znaleziono nauczycieli, to zwróć pustą listę lub odpowiedni błąd
        if (nauczyciele.isEmpty()) {
            return Collections.emptyList();
        }

        return nauczyciele.stream()
                .map(n -> new NauczycieleDTO(n.getId(), n.getImie(), n.getNazwisko()))
                .collect(Collectors.toList());
    }


}
