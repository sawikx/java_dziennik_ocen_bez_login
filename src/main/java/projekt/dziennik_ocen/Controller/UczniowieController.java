package projekt.dziennik_ocen.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekt.dziennik_ocen.exceptions.NotFoundException;
import projekt.dziennik_ocen.model.Uczniowie;
import projekt.dziennik_ocen.Service.UczniowieService;
import projekt.dziennik_ocen.Service.KlasyService;

import java.util.List;

@Controller
@RequestMapping("/uczniowie")
public class UczniowieController {

    private final UczniowieService uczniowieService;
    private final KlasyService klasyService;

    public UczniowieController(UczniowieService uczniowieService, KlasyService klasaService) {
        this.uczniowieService = uczniowieService;
        this.klasyService = klasaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("listUczniowie", uczniowieService.listAll());
        return "uczniowie/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Uczniowie uczen = new Uczniowie();
        model.addAttribute("uczen", uczen);
        model.addAttribute("klasy", klasyService.listAll());
        return "uczniowie/new";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("uczen") Uczniowie uczen) {
        uczniowieService.save(uczen);
        return "redirect:/uczniowie/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") int id, Model model) throws NotFoundException {
        Uczniowie uczen = uczniowieService.find(id);
        if (uczen == null) {
            throw new NotFoundException("Uczeń nie znaleziony");
        }
        model.addAttribute("uczen", uczen);
        model.addAttribute("klasy", klasyService.listAll());
        return "uczniowie/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id) {
        uczniowieService.delete(id);
        return "redirect:/uczniowie/";
    }

    @GetMapping("/byKlasa/{klasaId}")
    public ResponseEntity<List<Uczniowie>> getUczniowieByKlasa(@PathVariable Integer klasaId) {
        List<Uczniowie> uczniowie = uczniowieService.findByKlasaId(klasaId);
        if (uczniowie.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(uczniowie); // 200 OK
    }
}

