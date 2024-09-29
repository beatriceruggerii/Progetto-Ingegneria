package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Services.OggettoService;
import OmisFax.OmiStories.Services.ScenarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class OggettoController {
    private final OggettoService oggettoService;
    private final ScenarioService scenarioService;
    private final ScenarioRepository scenarioRepository;

    @Autowired
    public OggettoController(OggettoService oggettoService, ScenarioService scenarioService, ScenarioRepository scenarioRepository) {
        this.oggettoService = oggettoService;
        this.scenarioService = scenarioService;
        this.scenarioRepository = scenarioRepository;
    }

    @PostMapping("/salva_oggetto")
    public ResponseEntity<String> salvaScenario(@RequestBody Oggetto oggetto, HttpSession session) {
        System.out.println("----\n richiesta di salvataggio oggeetto ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");

        if (oggettoService.salvaOggetto(oggetto)) {
            System.out.println("Oggetto salvato " + oggetto.toString());
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Oggetto salvato con successo: " + oggetto.getNomeOggetto());
        } else {
            System.out.println("Oggetto non salvato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto.");
        }
    }

    //FIXME: non salva gli scenari con l'oggetto

    /*
    @PostMapping("/salva_oggetto")
    public ResponseEntity<String> salvaScenario(@RequestBody Map<String, String> payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        Oggetto oggetto;
        Storia storia = (Storia) session.getAttribute("storiaCorrente");

        String titoloScenarioMadre = payload.get("scenarioMadreOggetto");
        Scenario scenarioMadre = scenarioRepository.findByTitoloAndStoria(titoloScenarioMadre, storia);

        String titoloScenarioControllore = payload.get("scenarioControlloreOggetto");
        Scenario scenarioControllore = scenarioRepository.findByTitoloAndStoria(titoloScenarioControllore, storia);

        if (scenarioMadre == null || scenarioControllore == null) {
            System.out.println("Oggetto non salvato: non esistono scenari corrispondenti ad almeno uno di quelli specificato.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto, non esistono scenari corrispondenti.");
        }

        oggetto = new Oggetto(payload.get("nomeOggetto"), scenarioMadre, scenarioControllore);
        if (oggettoService.salvaOggetto(oggetto)) {
            System.out.println("Oggetto salvato " + oggetto.getNomeOggetto());
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Oggetto salvato con successo: " + oggetto.getNomeOggetto());
        } else {
            System.out.println("Oggetto non salvato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto.");
        }
    }
    */

}

