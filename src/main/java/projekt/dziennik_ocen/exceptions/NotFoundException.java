package projekt.dziennik_ocen.exceptions;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // stwórz stronę error/404.html
    }
}
