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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class StoryController {
    private final StoriaService storiaService;
    @Autowired
    public StoryController(StoriaService storiaService) {
        this.storiaService = storiaService;
    }


    @PostMapping("/salva_storia")
    public ResponseEntity<String> salvaStoria(@RequestBody StoriaDTO payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        return storiaService.salvaStoria(payload, session);
    }

    @GetMapping("/fetch_storie")
    public ResponseEntity<Map<String, Object>> fetchStorie(HttpSession session) {
        System.out.println("richiesta di fetch storie ricevuta");
        return storiaService.responseFetchStorie(session);
    }


}