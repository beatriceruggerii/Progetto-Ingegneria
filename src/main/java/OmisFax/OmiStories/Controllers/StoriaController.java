package OmisFax.OmiStories.Controllers;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Services.StoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/storia")
public class StoriaController {
    private StoriaService storiaService;
    @Autowired
    public StoriaController(StoriaService storiaService) {
        this.storiaService = storiaService;
    }


    @PostMapping("/salva")
    public ResponseEntity<String> salvaStoria(@RequestBody StoriaDTO payload, HttpSession session) {
        System.out.println("richiesta ricevuta");
        String username = (String) session.getAttribute("loggedUsername");
        Storia storia = storiaService.salvaStoria(payload,username);
        session.setAttribute("storiaCorrente", storia);
        return ResponseEntity.ok("Storia salvata con successo.");
    }

    @GetMapping("/{titoloStoria}")
    public ResponseEntity<Map<String, Object>> fetchDatiStoria(@PathVariable String titoloStoria) {
        System.out.println("richiesta dei dati della storia ricevuti");
        return ResponseEntity.ok(storiaService.responseDatiStoria(titoloStoria));
    }


}