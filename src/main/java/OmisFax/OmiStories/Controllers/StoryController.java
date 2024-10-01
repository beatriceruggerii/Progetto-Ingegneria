package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Services.SceltaService;
import OmisFax.OmiStories.Services.ScenarioService;
import OmisFax.OmiStories.Services.StoriaService;
import OmisFax.OmiStories.Services.UtenteService;
import OmisFax.OmiStories.Services.IndovinelloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class StoryController {
    private final StoriaService storiaService;
    private final UtenteService utenteService;
    private final ScenarioService scenarioService;

    @Autowired
    public StoryController(StoriaService storiaService, UtenteService utenteService, ScenarioService scenarioService) {
        this.storiaService = storiaService;
        this.utenteService = utenteService;
        this.scenarioService = scenarioService;
    }


    @PostMapping("/salva_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody Map<String, String> payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        String titolo = payload.get("titolo");
        String descrizioneIniziale = payload.get("descrizioneIniziale");
        String username = (String) session.getAttribute("loggedUsername");
        Utente autore = utenteService.trovaUtente(username);
        Storia storia = new Storia(titolo, autore);
        Scenario scenarioIniziale = new Scenario(storia, "Scenario Iniziale", descrizioneIniziale);

        if(storiaService.getStoria(titolo) != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Esiste già una storia con questo titolo.");
        }

        if (storiaService.salvaStoria(storia) && scenarioService.salvaScenario(scenarioIniziale)) {
            System.out.println("Storia salvata");
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Storia salvata con successo");
        } else {
            System.out.println("Storia non salvata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("C'è stato un errore con il salvataggio della tua storia, ritenta.");
        }
    }





}