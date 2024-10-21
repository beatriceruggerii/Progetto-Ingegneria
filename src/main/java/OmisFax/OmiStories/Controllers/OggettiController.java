package OmisFax.OmiStories.Controllers;


import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.Entities.Oggetto;
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
    public ResponseEntity<Map<String, Object>>getOggetti(@PathVariable long idScenario, HttpSession session) {
        Map<String, Object> oggetti = oggettoService.getOggetti(idScenario);
        return new ResponseEntity<>(oggetti, HttpStatus.OK);
    }
}