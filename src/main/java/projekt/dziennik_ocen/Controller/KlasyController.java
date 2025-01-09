package projekt.dziennik_ocen.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.Service.NauczycieleService;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.Klasy;
import projekt.dziennik_ocen.Service.KlasyService;

import java.util.*;

@Controller
@RequestMapping("/klasy")
public class KlasyController {

    private final KlasyService klasyService;
    private final NauczycieleService nauczycieleService;

    public KlasyController(KlasyService klasyService, NauczycieleService nauczycieleService) {
        this.klasyService = klasyService;
        this.nauczycieleService = nauczycieleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Klasy> klasyList = (List<Klasy>) klasyService.listAll();
        Map<Integer, Long> studentCounts = new HashMap<>();

        // Pobieramy liczbę uczniów dla każdej klasy
        for (Klasy klasa : klasyList) {
            long count = klasyService.countStudentsInClass(klasa.getId());
            studentCounts.put(klasa.getId(), count);
        }

        model.addAttribute("listKlasy", klasyList);
        model.addAttribute("studentCounts", studentCounts); // Mapujemy liczbę uczniów
        return "klasy/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Klasy klasy = new Klasy();
        model.addAttribute("klasy", klasy);
        model.addAttribute("listNauczyciele", nauczycieleService.listAll());
        return "klasy/new";
    }

    @PostMapping("/save")
    public String saveClass(@ModelAttribute("klasy") Klasy klasy) {
        klasyService.save(klasy);
        return "redirect:/klasy/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model) throws NotFoundException {
        Klasy klasy = klasyService.find(id);
        if (klasy == null) {
            throw new NotFoundException("klasy nie znaleziona");
        }
        model.addAttribute("klasy", klasy);
        model.addAttribute("listNauczyciele", nauczycieleService.listAll());
        return "klasy/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable(name = "id") int id) {
        klasyService.delete(id);
        return "redirect:/klasy/";
    }
}
