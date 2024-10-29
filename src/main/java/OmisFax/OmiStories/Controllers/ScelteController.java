package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.SceltaDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.SceltaService;
import OmisFax.OmiStories.Services.ScelteService;
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

@RestController
@RequestMapping("/scelte")
public class ScelteController {

    private final ScelteService scelteService;

    @Autowired
    public ScelteController(ScelteService scelteService) {
        this.scelteService = scelteService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> fetchScelte(HttpSession session) {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        System.out.println("richiesta di fetch scelte ricevuta");
        Map<String,Object> stringObjectMap = scelteService.responseFetchScelte(storia);
        return ResponseEntity.ok(stringObjectMap);
    }

    //ritorna tutte le scelte che hanno scenario iniziale con idScenario passato nel path
    @GetMapping("/{idScenario}")
    public ResponseEntity<Map<String, Object>> fetchScelteScenario(@PathVariable Long idScenario, HttpSession session) {
        System.out.println("richiesta di fetch scelte dello scenarioricevuta");
        Map<String,Object> stringObjectMap = scelteService.fetchScelteScenario(idScenario);
        return ResponseEntity.ok(stringObjectMap);
    }


}
