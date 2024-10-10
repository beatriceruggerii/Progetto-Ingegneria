package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.IndovinelloService;
import OmisFax.OmiStories.Services.OggettoService;
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
public class IndovinelloController {

    private final IndovinelloService indovinelloService;

    @Autowired
    public IndovinelloController(IndovinelloService indovinelloService) {
        this.indovinelloService = indovinelloService;
    }

    @PostMapping("/salva_indovinello")
    public ResponseEntity<String> salvaIndovinello(@RequestBody IndovinelloDTO infoIndovinello, HttpSession session) {
        System.out.println("Richiesta ricevuta");
        return indovinelloService.responseSalvaIndovinello(infoIndovinello, session);
    }

    @GetMapping("/fetch_indovinelli")
    public ResponseEntity<Map<String, Object>> fetchIndovinelli(HttpSession session) {
        System.out.println("richiesta di fetch indovinelli ricevuta");
        return indovinelloService.responseFetchIndovinelli(session);
    }

    @PutMapping("/modifica_indovinello/{idIndovinello}")
    public ResponseEntity<String> modificaIndovinello(@PathVariable Long idIndovinello, @RequestBody IndovinelloDTO nuovoIndovinello) {
        boolean successo = indovinelloService.modificaIndovinello(idIndovinello, nuovoIndovinello);
        if (!successo) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modifica fallita: Indovinello non trovato.");
        }
        return ResponseEntity.ok("Modifica avvenuta con successo.");
    }

}
