package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.StoriaService;
import OmisFax.OmiStories.Services.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StoryController {
    private final StoriaService storiaService;
    private final UtenteService utenteService;

    @Autowired
    public StoryController(StoriaService storiaService, UtenteService utenteService) {
        this.storiaService = storiaService;
        this.utenteService = utenteService;
    }

    @PostMapping("/crea_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody String titolo, HttpSession session) {
        System.out.println("richiesta ricevuta");
        String username = (String) session.getAttribute("loggedUsername");
        Utente autore = utenteService.trovaUtente(username);
        Storia storia = new Storia(titolo, autore);

        if (storiaService.salvaStoria(storia)) {
            System.out.println("Storia salvata");
            return ResponseEntity.ok("Storia salvata con successo");
        } else {
            System.out.println("Storia non salvata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: storia già esistente");
        }
    }

}