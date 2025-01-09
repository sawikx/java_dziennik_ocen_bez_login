package projekt.dziennik_ocen.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.Klasy;
import projekt.dziennik_ocen.model.Przedmioty;
import projekt.dziennik_ocen.Service.PrzedmiotyService;

@Controller
@RequestMapping("/przedmioty")
public class PrzedmiotyController {

    private final PrzedmiotyService przedmiotyService;

    public PrzedmiotyController(PrzedmiotyService przedmiotyService) {
        this.przedmiotyService = przedmiotyService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listPrzedmioty", przedmiotyService.listAll());
        return "przedmioty/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Przedmioty przedmiot = new Przedmioty();
        model.addAttribute("przedmiot", przedmiot);
        return "przedmioty/new";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model) throws NotFoundException {
        Przedmioty przedmiot = przedmiotyService.find(id);
        if (przedmiot == null) {
            throw new NotFoundException("Przedmiotu nie znaleziona");
        }
        model.addAttribute("przedmiot", przedmiot);
        return "przedmioty/edit";
    }

    @PostMapping("/save")
    public String saveSubject(@ModelAttribute("przedmiot") Przedmioty przedmiot) {
        przedmiotyService.save(przedmiot);
        return "redirect:/przedmioty/";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubject(@PathVariable(name = "id") int id) {
        przedmiotyService.delete(id);
        return "redirect:/przedmioty/";
    }
}

