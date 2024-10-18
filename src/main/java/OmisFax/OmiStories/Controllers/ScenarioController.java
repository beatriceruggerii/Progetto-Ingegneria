package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Services.ScenarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScenarioController {
    private final ScenarioService scenarioService;

    @Autowired
    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }


    @PostMapping("/salva_scenario")
    public ResponseEntity<String> salvaScenario(@RequestBody ScenarioDTO scenariodto, HttpSession session) {
        System.out.println("richiesta ricevuta");
        return scenarioService.salvaScenario(scenariodto,session);
    }

    @GetMapping("/fetch_scenari")
    public ResponseEntity<Map<String, Object>> fetchScenari(HttpSession session) {
        System.out.println("richiesta di fetch scenari ricevuta");
        return scenarioService.fetchScenari(session);
    }

    @PutMapping("/modifica_scenario/{idScenario}")
    public ResponseEntity<String> modificaIndovinello(@PathVariable Long idScenario, @RequestBody ScenarioDTO nuovoScenario) {
        boolean successo = scenarioService.modificaScenario(idScenario, nuovoScenario);
        if (!successo) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modifica fallita: Scenario non trovato.");
        }
        return ResponseEntity.ok("Modifica avvenuta con successo.");
    }

    @GetMapping("/fetch_scenario_figlio/{idScelta}")
    public ResponseEntity<Map<String, Object>> fetchScenaroFiglio(@PathVariable Long idScelta, HttpSession session) {
        System.out.println("richiesta di fetch scenaro da scelta ricevuta");
        return scenarioService.fetchScenaroFiglio(idScelta, session);
    }

    @GetMapping("scenario/{idScenario}")
    public ResponseEntity<Map<String, Object>> fetchScenario(@PathVariable Long idScenario) {
        System.out.println("richiesta di fetch scenario ricevuta");
        return scenarioService.fetchScenario(idScenario);
    }

}
