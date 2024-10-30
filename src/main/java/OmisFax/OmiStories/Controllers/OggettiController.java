package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.interfaces.IOggettiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/oggetti")
public class OggettiController {
    @Autowired
    private IOggettiService oggettiService;

    @GetMapping("/{idScenario}")
    public ResponseEntity<Map<String, Object>> getOggetti(@PathVariable long idScenario, HttpSession session) {
        Map<String, Object> oggetti = oggettiService.getOggetti(idScenario);
        return new ResponseEntity<>(oggetti, HttpStatus.OK);
    }

    @GetMapping("/controllori/{idScenario}")
    public ResponseEntity<Map<String, Object>> getOggettiContollori(@PathVariable long idScenario, HttpSession session) {
        long idPartita = (long) session.getAttribute("idPartitaInCorso");
        Map<String, Object> oggetti = oggettiService.getOggettiControllori(idScenario, idPartita);
        return new ResponseEntity<>(oggetti, HttpStatus.OK);
    }

    @GetMapping("/")
    //ritorna gli oggetti creati all'interno di una storia
    public ResponseEntity<Map<String, Object>> fetchOggettiStoria(HttpSession session) {
        System.out.println("richiesta di fetch oggetti ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        Map<String, Object> responseData = oggettiService.fetchOggettiStoria(storia);
        return ResponseEntity.ok(responseData);
    }
}