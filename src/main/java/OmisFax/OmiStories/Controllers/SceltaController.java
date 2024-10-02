package OmisFax.OmiStories.Controllers;

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
    private final ScenarioService scenarioService;


    @Autowired
    public SceltaController(SceltaService sceltaService, ScenarioService scenarioService) {
        this.sceltaService = sceltaService;
        this.scenarioService = scenarioService;
    }

    @PostMapping("/salva_scelta")
    public ResponseEntity<String> salvaScelta(@RequestBody Map<String, String> infoScelta, HttpSession session) {
        System.out.println("Richiesta ricevuta");

        String testoScelta = infoScelta.get("testo");
        long idMadre = Long.parseLong(infoScelta.get("idMadre"));
        long idFiglio = Long.parseLong(infoScelta.get("idFiglio"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Scelta scelta = new Scelta(testoScelta, scenarioMadre, scenarioFiglio);

        if (scenarioMadre == null || scenarioFiglio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (idFiglio!=idMadre) {
            if (sceltaService.registraScelta(scelta)) {
                System.out.println("scelta salvata");
                session.setAttribute("sceltaCorrente", scelta);
                return ResponseEntity.ok("Scelta salvata");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }
        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }
    @GetMapping("/fetch_scelte")
    public ResponseEntity<Map<String, Object>> fetchScelte(HttpSession session) {
        System.out.println("richiesta di fetch scelte ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            System.out.println("Storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scelta> listaScelte = sceltaService.listaScelte(storia);
        if(listaScelte.isEmpty()){
            System.out.println("Scelte non trovate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        System.out.println("scelte trovate: "+ listaScelte.size());
        for(int i = 0; i<listaScelte.size(); i++){
            System.out.println(listaScelte.get(i).toString());
        }
        responseData.put("listaScelte", listaScelte);
        return ResponseEntity.ok(responseData);
    }
}
