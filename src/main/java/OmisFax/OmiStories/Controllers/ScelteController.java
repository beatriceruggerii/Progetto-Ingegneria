package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.SceltaDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.SceltaService;
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

    private final SceltaService sceltaService;


    @Autowired
    public ScelteController(SceltaService sceltaService) {
        this.sceltaService = sceltaService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> fetchScelte(HttpSession session) {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        System.out.println("richiesta di fetch scelte ricevuta");
        return sceltaService.responseFetchScelte(storia);
        //TODO: le responseentities devono essere generate nel controller
    }

    //ritorna tutte le scelte che hanno scenario iniziale con idScenario passato nel path
    @GetMapping("/scelte/{idScenario}")
    public ResponseEntity<Map<String, Object>> fetchScelteScenario(@PathVariable Long idScenario, HttpSession session) {
        System.out.println("richiesta di fetch scelte dello scenarioricevuta");
        return sceltaService.responseFetchScelteScenario(idScenario,session);
        //TODO: le responseentities devono essere generate nel controller
    }


}
