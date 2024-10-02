package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Services.OggettoService;
import OmisFax.OmiStories.Services.ScenarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    //TODO: sposta la logica di business in oggettoservice
    @PostMapping("/salva_oggetto")
    public ResponseEntity<String> salvaOggetto(@RequestBody Map<String, String> payload, HttpSession session) {
        System.out.println("----\n richiesta di salvataggio oggeetto ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        String nomeOggetto = payload.get("nomeOggetto");
        long idMadre = Long.parseLong(payload.get("scenarioMadreOggetto"));
        long idControllore = Long.parseLong(payload.get("scenarioControlloreOggetto"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioControllore = scenarioService.findById(idControllore);

        if (scenarioMadre == null || scenarioControllore == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (idControllore == idMadre) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }
        if (scenarioControllore.equals(scenarioService.findByTitoloAndStoria("Scenario Iniziale", storia))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza non può richiedere oggetti di accesso!");
        }

        Oggetto oggetto = new Oggetto(nomeOggetto, scenarioMadre, scenarioControllore);

        if (oggettoService.salvaOggetto(oggetto)) {
            System.out.println("Oggetto salvato " + oggetto.toString());
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Oggetto salvato con successo: " + oggetto.getNomeOggetto());
        } else {
            //System.out.println("Oggetto non salvato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto.");
        }
    }

    @GetMapping("/fetch_oggetti")
    public ResponseEntity<Map<String, Object>> fetchOggetti(HttpSession session) {
        System.out.println("richiesta di fetch oggetti ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            System.out.println("storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Oggetto> listaOggetti = new ArrayList<>();
        listaOggetti = oggettoService.findByStoria(storia);
        if (listaOggetti.isEmpty()) {
            System.out.println("nessun oggetto trovato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        //debug
        System.out.println("oggetti trovati: " + listaOggetti.size());
        for (int i = 0; i < listaOggetti.size(); i++) {
            System.out.println(listaOggetti.get(i).toString());
        }
        responseData.put("listaOggetti", listaOggetti);
        return ResponseEntity.ok(responseData);
    }


}

