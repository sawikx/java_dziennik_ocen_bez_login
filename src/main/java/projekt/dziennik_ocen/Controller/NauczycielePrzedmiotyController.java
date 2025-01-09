package projekt.dziennik_ocen.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.NauczycielePrzedmioty;
import projekt.dziennik_ocen.Service.NauczycielePrzedmiotyService;
import projekt.dziennik_ocen.Service.NauczycieleService;
import projekt.dziennik_ocen.Service.PrzedmiotyService;

@Controller
@RequestMapping("/nauczyciel-przedmiot")
public class NauczycielePrzedmiotyController {

    private final NauczycielePrzedmiotyService nauczycielePrzedmiotyService;
    private final NauczycieleService nauczycieleService;
    private final PrzedmiotyService przedmiotService;

    public NauczycielePrzedmiotyController(NauczycielePrzedmiotyService nauczycielePrzedmiotyService, NauczycieleService nauczycieleService, PrzedmiotyService przedmiotService) {
        this.nauczycielePrzedmiotyService = nauczycielePrzedmiotyService;
        this.nauczycieleService = nauczycieleService;
        this.przedmiotService = przedmiotService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listNauczycielePrzedmioty", nauczycielePrzedmiotyService.listAll());
        return "nauczyciel-przedmiot/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        NauczycielePrzedmioty nauczycielPrzedmiot = new NauczycielePrzedmioty();
        model.addAttribute("nauczycielPrzedmiot", nauczycielPrzedmiot);
        model.addAttribute("nauczyciele", nauczycieleService.listAll());
        model.addAttribute("przedmioty", przedmiotService.listAll());
        return "nauczyciel-przedmiot/new";
    }

    @PostMapping("/save")
    public String saveNauczycielPrzedmiot(@ModelAttribute("nauczycielPrzedmiot") NauczycielePrzedmioty nauczycielPrzedmiot) {
        nauczycielePrzedmiotyService.save(nauczycielPrzedmiot);
        return "redirect:/nauczyciel-przedmiot/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model) throws NotFoundException {
        NauczycielePrzedmioty nauczycielPrzedmiot = nauczycielePrzedmiotyService.find(id);
        if (nauczycielPrzedmiot == null) {
            throw new NotFoundException("Powiązanie nauczyciela z przedmiotem nie znalezione");
        }
        model.addAttribute("nauczycielPrzedmiot", nauczycielPrzedmiot);
        model.addAttribute("nauczyciele", nauczycieleService.listAll());
        model.addAttribute("przedmioty", przedmiotService.listAll());
        return "nauczyciel-przedmiot/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteNauczycielPrzedmiot(@PathVariable(name = "id") int id) {
        nauczycielePrzedmiotyService.delete(id);
        return "redirect:/nauczyciel-przedmiot/";
    }
}
