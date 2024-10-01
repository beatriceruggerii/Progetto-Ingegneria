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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/fetch_scenari")
    public ResponseEntity<Map<String, Object>> fetchScenari(HttpSession session) {
        System.out.println("richiesta di fetch scenari ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scenario> listaScenari = new ArrayList<>();
        listaScenari = scenarioService.findByStoria(storia);
        if (listaScenari.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        //debud
        System.out.println("scenari trovati: " + listaScenari.size());
        for (int i = 0; i < listaScenari.size(); i++) {
            System.out.println(listaScenari.get(i).toString());
        }
        responseData.put("listaScenari", listaScenari);
        return ResponseEntity.ok(responseData);
    }
}
