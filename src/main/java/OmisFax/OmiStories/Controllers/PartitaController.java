package OmisFax.OmiStories.Controllers;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import OmisFax.OmiStories.Services.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partita")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;
    @Autowired
    private PartitaRepository partitaRepository;

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
            titoloStoria = titoloStoria.trim();
            if(partitaRepository.findByGiocatoreUsernameAndStoriaTitolo(username, titoloStoria) == null){
                Partita partita = partitaService.salvaPartita(titoloStoria, username);
                session.setAttribute("partitaCorrente", partita);
                return ResponseEntity.ok("Partita salvata con successo");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hai già avviato questa partita");
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il salvataggio della partita");
        }
    }

    @PutMapping("/aggiorna")
    public ResponseEntity<String> aggiornaPartita(@RequestBody long idScenarioFiglio, HttpSession session){
        Partita partitaCorrente = (Partita) session.getAttribute("partitaCorrente");
        if(partitaCorrente != null){
           return partitaService.aggiornaPartita(partitaCorrente, idScenarioFiglio);
        } else {
            return ResponseEntity.status((HttpStatus.UNAUTHORIZED)).body("partita corrente non recuperata");
        }
    }
}