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
@RequestMapping("/scelta")
public class SceltaController {

    private SceltaService sceltaService;


    @Autowired
    public SceltaController(SceltaService sceltaService) {
        this.sceltaService = sceltaService;
    }

    @PostMapping("/salva")
    public ResponseEntity<String> salvaScelta(@RequestBody SceltaDTO infoScelta, HttpSession session) {
        System.out.println("Richiesta ricevuta");
        Scelta scelta = sceltaService.responseSalvaScelta(infoScelta);
        session.setAttribute("sceltaCorrente", scelta);
        return ResponseEntity.ok("Scelta salvata con successo");
    }

    @PutMapping("/modifica/{idScelta}")
    public ResponseEntity<String> modificaScelta(@PathVariable Long idScelta, @RequestBody SceltaDTO nuovaScelta) {
        boolean successo = sceltaService.modificaScelta(nuovaScelta);
        if (!successo) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modifica fallita: Scelta non trovata.");
        }
        return ResponseEntity.ok("Modifica avvenuta con successo.");
    }

}
