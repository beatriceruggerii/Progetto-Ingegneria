package OmisFax.OmiStories.Controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndovinelloController {

    private final IndovinelloService indovinelloService;

    private final ScenarioService scenarioService;

    @Autowired
    public IndovinelloController(IndovinelloService indovinelloService, ScenarioService scenarioService) {
        this.indovinelloService = indovinelloService;
        this.scenarioService = scenarioService;
    }

    @PostMapping("/salva_indovinello")
    public ResponseEntity<String> salvaIndovinello(@RequestBody Map<String, String> infoIndovinello, HttpSession session) {
        System.out.println("Richiesta ricevuta");

        String testoIndovinello = infoIndovinello.get("testo");
        String testoSoluzione = infoIndovinello.get("soluzione");
        long idMadre = Long.parseLong(infoIndovinello.get("idMadre"));
        long idFiglio = Long.parseLong(infoIndovinello.get("idFiglio"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Indovinello indovinello = new Indovinello(testoIndovinello, scenarioMadre, scenarioFiglio, testoSoluzione);

        if (scenarioMadre == null || scenarioFiglio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (idFiglio!=idMadre) {
            if (indovinelloService.registraIndovinello(indovinello)) {
                System.out.println("indovinello salvato");
                return ResponseEntity.ok("Indovinello salvato");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }

        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

    @GetMapping("/fetch_indovinelli")
    public ResponseEntity<Map<String, Object>> fetchIndovinelli(HttpSession session) {
        System.out.println("richiesta di fetch indovinelli ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            System.out.println("storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Indovinello> listaIndovinelli = new ArrayList<>();
        listaIndovinelli = indovinelloService.findByStoria(storia);
        if(listaIndovinelli.isEmpty()){
            System.out.println("nessun indovinello trovato");
            responseData.put("errorMessage","Nessun indovinello trovato.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        //debug
        System.out.println("Indovinelli trovati: "+ listaIndovinelli.size());
        for(int i = 0; i<listaIndovinelli.size(); i++){
            System.out.println(listaIndovinelli.get(i).toString());
        }
        responseData.put("listaIndovinelli", listaIndovinelli);
        return ResponseEntity.ok(responseData);
    }



}
