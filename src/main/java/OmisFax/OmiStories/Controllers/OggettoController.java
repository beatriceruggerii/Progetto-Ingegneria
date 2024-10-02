package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.OggettoDTO;
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
    //TODO: gestione eccezioni in responseentities
    @PostMapping("/salva_oggetto")
    public ResponseEntity<String> salvaOggetto(@RequestBody OggettoDTO payload, HttpSession session) {
        System.out.println("----\n richiesta di salvataggio oggeetto ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");

        return oggettoService.salvaOggetto(payload, storia);
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

