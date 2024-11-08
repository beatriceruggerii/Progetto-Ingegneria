package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Services.IndovinelloService;
import OmisFax.OmiStories.Services.interfaces.IIndovinelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/indovinello")
public class IndovinelloController {

    private IIndovinelloService indovinelloService;

    @Autowired
    public IndovinelloController(IndovinelloService indovinelloService) {
        this.indovinelloService = indovinelloService;
    }

    @PostMapping("/salva")
    public ResponseEntity<String> salvaIndovinello(@RequestBody IndovinelloDTO infoIndovinello) {
        System.out.println("Richiesta ricevuta");
        String message = indovinelloService.responseSalvaIndovinello(infoIndovinello);
        return ResponseEntity.ok(message);
    }


    @PutMapping("/modifica/{idIndovinello}")
    public ResponseEntity<String> modificaIndovinello(@PathVariable Long idIndovinello, @RequestBody IndovinelloDTO nuovoIndovinello) {
        boolean successo = indovinelloService.modificaIndovinello(idIndovinello, nuovoIndovinello);
        if (!successo) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Modifica fallita: Indovinello non trovato.");
        }
        return ResponseEntity.ok("Modifica avvenuta con successo.");
    }

}
