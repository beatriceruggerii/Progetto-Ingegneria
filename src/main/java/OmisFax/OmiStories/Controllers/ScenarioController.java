package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
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
public class ScenarioController {
    private final ScenarioService scenarioService;

    @Autowired
    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }


    @PostMapping("/salva_scenario")
    public ResponseEntity<String> salvaScenario(@RequestBody Scenario scenario, HttpSession session) {
        System.out.println("richiesta ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        scenario.setStoria(storia);

        if (scenarioService.salvaScenario(scenario)) {
            System.out.println("Scenario salvato");
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Scenario salvato con successo");
        } else {
            System.out.println("Scenario non salvato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare lo scenario.");
        }
    }
}
