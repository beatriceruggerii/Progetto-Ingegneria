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
    private final SceltaService sceltaService;
    private final ScenarioService scenarioService;
    private final IndovinelloService indovinelloService;

    @Autowired
    public StoryController(StoriaService storiaService, UtenteService utenteService, SceltaService sceltaService, ScenarioService scenarioService, IndovinelloService indovinelloService) {
        this.storiaService = storiaService;
        this.utenteService = utenteService;
        this.sceltaService = sceltaService;
        this.scenarioService = scenarioService;
        this.indovinelloService = indovinelloService;
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

        if (storiaService.salvaStoria(storia) && scenarioService.salvaScenario(scenarioIniziale)) {
            System.out.println("Storia salvata");
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Storia salvata con successo");
        } else {
            System.out.println("Storia non salvata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: storia già esistente");
        }
    }

    @PostMapping("/salva_scelta")
    public ResponseEntity<String> salvaScelta(@RequestBody Map<String, String> infoScelta) {
        System.out.println("Richiesta ricevuta");

        String testoScelta = infoScelta.get("testo");
        long idMadre = Long.parseLong(infoScelta.get("idMadre"));
        long idFiglio = Long.parseLong(infoScelta.get("idFiglio"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Scelta scelta = new Scelta(testoScelta, scenarioMadre, scenarioFiglio);

        if (scenarioMadre == null || scenarioFiglio == null) {
            System.out.println("sono null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (!scenarioMadre.equals(scenarioFiglio)) {
            System.out.println("ok");
            if (sceltaService.registraScelta(scelta)) {
                System.out.println("salvando");
                return ResponseEntity.ok("Scelta salvata");
            }
        } else {
            System.out.println("madre = figlio");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: madre e figlio sono lo stesso");
        }

        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

    @PostMapping("/salva_indovinello")
    public ResponseEntity<String> salvaIndovinello(@RequestBody Map<String, String> infoIndovinello) {
        System.out.println("Richiesta ricevuta");

        String testoIndovinello = infoIndovinello.get("testo");
        String testoSoluzione = infoIndovinello.get("soluzione");
        long idMadre = Long.parseLong(infoIndovinello.get("idMadre"));
        long idFiglio = Long.parseLong(infoIndovinello.get("idFiglio"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Indovinello indovinello = new Indovinello(testoIndovinello, scenarioMadre, scenarioFiglio, testoSoluzione);

        if (scenarioMadre == null || scenarioFiglio == null) {
            System.out.println("sono null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (!scenarioMadre.equals(scenarioFiglio)) {
            System.out.println("ok");
            if (indovinelloService.registraIndovinello(indovinello)) {
                System.out.println("salvando");
                return ResponseEntity.ok("Indovinello salvato");
            }
        } else {
            System.out.println("madre = figlio");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: madre e figlio sono lo stesso");
        }

        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

}