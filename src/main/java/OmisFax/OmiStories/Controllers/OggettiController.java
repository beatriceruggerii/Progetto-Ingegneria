package OmisFax.OmiStories.Controllers;


import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.OggettoService;
import OmisFax.OmiStories.Services.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oggetti")
public class OggettiController {
    @Autowired
    private OggettoService oggettoService;

    @GetMapping("/{idScenario}")
    public ResponseEntity<Map<String, Object>> getOggetti(@PathVariable long idScenario, HttpSession session) {
        Map<String, Object> oggetti = oggettoService.getOggetti(idScenario);
        return new ResponseEntity<>(oggetti, HttpStatus.OK);
    }

    @GetMapping("/controllori/{idScenario}")
    public ResponseEntity<Map<String, Object>> getOggettiContollori(@PathVariable long idScenario, HttpSession session) {
        long idPartita = (long) session.getAttribute("idPartitaInCorso");
        Map<String, Object> oggetti = oggettoService.getOggettiControllori(idScenario, idPartita);
        return new ResponseEntity<>(oggetti, HttpStatus.OK);
    }

    @GetMapping("/")
    //ritorna gli oggetti creati all'interno di una storia
    public ResponseEntity<Map<String, Object>> fetchOggettiStoria(HttpSession session) {
        System.out.println("richiesta di fetch oggetti ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        return oggettoService.fetchOggettiStoria(storia);
    }
}