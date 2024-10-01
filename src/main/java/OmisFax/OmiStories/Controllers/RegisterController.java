package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {
    private final UtenteService userService;

    @Autowired
    public RegisterController(UtenteService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Utente utente, HttpSession session) {
        if (userService.registraUtente(utente)) {
            session.setAttribute("loggedUsername", utente.getUsername());
            session.setAttribute("isPremium", utente.isPremium());
            return ResponseEntity.ok("Registrazione avvenuta con successo");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile completare la registrazione");
        }
    }
}