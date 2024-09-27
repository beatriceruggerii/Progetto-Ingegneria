package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.SceltaService;
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
    private final SceltaService sceltaService;

    @Autowired
    public StoryController(StoriaService storiaService, UtenteService utenteService, SceltaService sceltaService) {
        this.storiaService = storiaService;
        this.utenteService = utenteService;
        this.sceltaService = sceltaService;
    }

    @PostMapping("/crea_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody String titolo, HttpSession session) {
        System.out.println("richiesta ricevuta");
        String username = (String) session.getAttribute("loggedUsername");
        Utente autore = utenteService.trovaUtente(username);
        Storia storia = new Storia(titolo, autore);
        session.setAttribute("storiaCorrente", storia);

        if (storiaService.salvaStoria(storia)) {
            System.out.println("Storia salvata");
            return ResponseEntity.ok("Storia salvata con successo");
        } else {
            System.out.println("Storia non salvata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: storia già esistente");
        }
    }

    @PostMapping("/crea_scelta")
    public ResponseEntity<String> salvaScelta(@RequestBody Scelta scelta){
        System.out.println("richiesta ricevuta");
        if(scelta.getScenarioMadre() != scelta.getScenarioFiglio()){
            if(sceltaService.registraScelta(scelta)){
                return ResponseEntity.ok("Scelta salvata");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: madre e figlio sono lo stesso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

}