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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SceltaController {

    private final SceltaService sceltaService;


    @Autowired
    public SceltaController(SceltaService sceltaService) {
        this.sceltaService = sceltaService;
    }

    @PostMapping("/salva_scelta")
    public ResponseEntity<String> salvaScelta(@RequestBody SceltaDTO infoScelta, HttpSession session) {
        System.out.println("Richiesta ricevuta");
        return sceltaService.responseSalvaScelta(infoScelta, session);

    }
    @GetMapping("/fetch_scelte")
    public ResponseEntity<Map<String, Object>> fetchScelte(HttpSession session) {
        System.out.println("richiesta di fetch scelte ricevuta");
        return sceltaService.responseFetchScelte(session);
    }
}
