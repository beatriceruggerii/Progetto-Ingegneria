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

    @Autowired
    public OggettoController(OggettoService oggettoService, ScenarioService scenarioService) {
        this.oggettoService = oggettoService;
        this.scenarioService = scenarioService;
    }


    //TODO: è necessario un builder?
    @PostMapping("/salva_oggetto")
    public ResponseEntity<String> salvaScenario(@RequestBody Map<String, String> payload, HttpSession session) {
        System.out.println("----\n richiesta di salvataggio oggeetto ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        String nomeOggetto = payload.get("nomeOggetto");
        long idMadre = Long.parseLong(payload.get("scenarioMadreOggetto"));
        long idControllore = Long.parseLong(payload.get("scenarioControlloreOggetto"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioControllore = scenarioService.findById(idControllore);


        Oggetto oggetto = new Oggetto(nomeOggetto, scenarioMadre,scenarioControllore);

        if (oggettoService.salvaOggetto(oggetto)) {
            System.out.println("Oggetto salvato " + oggetto.toString());
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Oggetto salvato con successo: " + oggetto.getNomeOggetto());
        } else {
            System.out.println("Oggetto non salvato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto.");
        }
    }

}

