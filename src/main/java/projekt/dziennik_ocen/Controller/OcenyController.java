package projekt.dziennik_ocen.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.Service.*;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.Nauczyciele;
import projekt.dziennik_ocen.model.Oceny;
import projekt.dziennik_ocen.model.Przedmioty;
import projekt.dziennik_ocen.model.Uczniowie;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oceny")
public class OcenyController {

    private final OcenyService ocenyService;
    private final UczniowieService uczniowieService;
    private final PrzedmiotyService przedmiotService;
    private final KlasyService klasyService;
    private final NauczycieleService nauczycieleService;

    public OcenyController(OcenyService ocenyService, UczniowieService uczniowieService, PrzedmiotyService przedmiotService, KlasyService klasyService, NauczycieleService nauczycieleService) {
        this.ocenyService = ocenyService;
        this.uczniowieService = uczniowieService;
        this.przedmiotService = przedmiotService;
        this.klasyService = klasyService;
        this.nauczycieleService = nauczycieleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listOceny", ocenyService.listAll());
        return "oceny/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("ocena", new Oceny());  // Dodajemy pusty obiekt Oceny
        model.addAttribute("klasy", klasyService.listAll());
        model.addAttribute("przedmioty", przedmiotService.listAll());
        model.addAttribute("uczniowie", uczniowieService.listAll());
        return "oceny/new";
    }

    @GetMapping("/uczniowie/byKlasa/{klasaId}")
    @ResponseBody
    public List<Uczniowie> getUczniowieByKlasa(@PathVariable Integer klasaId) {
        return uczniowieService.findByKlasaId(klasaId);
    }

    @GetMapping("/oceny/getNauczycieleByPrzedmiot")
    public String getNauczycieleByPrzedmiot(@RequestParam Integer przedmiotId, Model model) {
        model.addAttribute("nauczyciele", nauczycieleService.findByPrzedmiot(przedmiotId));
        return "oceny/fragments :: nauczycieleList";
    }

    @PostMapping("/save")
    public String saveOceny(@RequestParam("uczen") Integer uczenId,
                            @RequestParam("przedmiot.id") Integer przedmiotId,
                            @RequestParam("nauczyciel.id") Integer nauczycielId,
                            @RequestParam("ocena") Integer ocena,
                            @RequestParam("dataOceny") String dataOceny) {

        // Sprawdzenie czy wszystkie wymagane dane są obecne
        if (uczenId == null || przedmiotId == null || nauczycielId == null || ocena == null || dataOceny == null) {
            return "redirect:/oceny/new"; // Przekierowanie, jeśli brakuje danych
        }

        try {
            // Pobierz obiekty z bazy danych na podstawie ID
            Uczniowie uczen = uczniowieService.find(uczenId);
            Przedmioty przedmiot = przedmiotService.find(przedmiotId);
            Nauczyciele nauczyciel = nauczycieleService.find(nauczycielId);

            // Przekształć datę z formatu String na Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = sdf.parse(dataOceny);

            // Tworzymy obiekt Oceny i ustawiamy wartości
            Oceny oceny = new Oceny();
            oceny.setUczen(uczen);
            oceny.setPrzedmiot(przedmiot);
            oceny.setNauczyciel(nauczyciel);
            oceny.setOcena(ocena);
            oceny.setDataOceny(data);

            // Zapisz ocenę
            ocenyService.save(oceny);

            // Po zapisaniu, przekieruj do listy ocen
            return "redirect:/oceny/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/oceny/new"; // W przypadku błędu wróć do formularza
        }
    }




    @GetMapping("/klasa/{klasaId}")
    public String listByClass(@PathVariable("klasaId") Integer klasaId, Model model) {
        // Pobierz oceny dla klasy
        Map<String, Map<String, List<Oceny>>> groupedOceny = ocenyService.groupByClassAndSubject(klasaId);

        model.addAttribute("groupedOceny", groupedOceny);
        model.addAttribute("uczniowie", uczniowieService.listAll());
        model.addAttribute("przedmioty", przedmiotService.listAll());
        return "oceny/index";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model) throws NotFoundException {
        Oceny ocena = ocenyService.find(id);
        if (ocena == null) {
            throw new NotFoundException("Ocena nie znaleziona");
        }
        model.addAttribute("ocena", ocena);
        model.addAttribute("uczniowie", uczniowieService.find(ocena.getUczen().getId()));
        model.addAttribute("przedmioty", przedmiotService.find(ocena.getPrzedmiot().getId()));
        return "oceny/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteOcena(@PathVariable(name = "id") int id) {
        ocenyService.delete(id);
        return "redirect:/oceny/";
    }
}
