package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Services.SceltaService;
import OmisFax.OmiStories.Services.ScenarioService;
import OmisFax.OmiStories.Services.StoriaService;
import OmisFax.OmiStories.Services.UtenteService;
import OmisFax.OmiStories.Services.IndovinelloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class StoryController {
    private final StoriaService storiaService;
    private final UtenteService utenteService;
    private final ScenarioService scenarioService;

    @Autowired
    public StoryController(StoriaService storiaService, UtenteService utenteService, ScenarioService scenarioService) {
        this.storiaService = storiaService;
        this.utenteService = utenteService;
        this.scenarioService = scenarioService;
    }


    @PostMapping("/salva_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody StoriaDTO payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        return storiaService.salvaStoria(payload, session);
    }


}