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

    @PutMapping("/modifica_scelta/{idScelta}")
    public ResponseEntity<String> modificaIndovinello(@PathVariable Long idScelta, @RequestBody SceltaDTO nuovaScelta) {
        boolean successo = sceltaService.modificaScelta(nuovaScelta);
        if (!successo) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modifica fallita: Scelta non trovata.");
        }
        return ResponseEntity.ok("Modifica avvenuta con successo.");
    }
}
