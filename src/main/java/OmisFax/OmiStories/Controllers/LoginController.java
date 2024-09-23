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
public class LoginController {
    @Autowired
    private UtenteService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Utente utente, HttpSession session) {
        Utente logUtente = userService.trovaUtente(utente.getUsername());
        if(logUtente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile completare il login");
        }
        if (userService.autentica(logUtente.getUsername(), utente.getPassword())) {
            session.setAttribute("loggedUserId", logUtente);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile completare il login");
        }
    }
}
