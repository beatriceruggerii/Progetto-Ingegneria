package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Services.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partita")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;

    @PostMapping("/salva")
    public ResponseEntity<String> salvaPartita(@RequestBody String titoloStoria, HttpSession session) {
        try {
            // Recupera l'username dalla sessione
            String username = (String) session.getAttribute("loggedUsername");
            System.out.println("Salvataggio partita: " + username + " storia: " + titoloStoria);
            if (username == null) {
                System.out.println("Utente non trovato"+username);
                throw new RuntimeException("Utente non loggato");
            }
            Partita partita = partitaService.salvaPartita(titoloStoria, username);
            session.setAttribute("idPartitaInCorso",partita.getId());
            return ResponseEntity.ok("Partita salvata con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il salvataggio della partita");
        }
    }
}