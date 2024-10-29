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

    @PutMapping("/riprendi/{idPartita}")
    public ResponseEntity<String> riprendiPartita(@PathVariable long idPartita, HttpSession session) {
        Partita partita = partitaService.getPartita(idPartita);
        if (partita != null) {
            session.setAttribute("idPartitaInCorso", idPartita);
            return ResponseEntity.ok("Partita ripresa con successo");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partita non trovata");
        }
    }

    @PostMapping("/salva")
    public ResponseEntity<String> salvaPartita(@RequestBody String titoloStoria, HttpSession session) {
        // Recupera l'username dalla sessione
        String username = (String) session.getAttribute("loggedUsername");
        if (username == null) {
            System.out.println("Utente non trovato" + username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non loggato.");
        }
        titoloStoria = titoloStoria.trim();
        try {
            if (partitaRepository.findByGiocatoreUsernameAndStoriaTitolo(username, titoloStoria) == null) {
                Partita partita = partitaService.salvaPartita(titoloStoria, username);
                session.setAttribute("idPartitaInCorso", partita.getId());
                return ResponseEntity.ok("Partita salvata con successo");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hai già avviato questa partita");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il salvataggio della partita");
        }
    }

    @PutMapping("/aggiorna")
    public ResponseEntity<String> aggiornaPartita(@RequestBody String idScenarioFiglio, HttpSession session) {
        try {
            long idScenario = Long.parseLong(idScenarioFiglio);
            Long idPartitaCorrente = (Long) session.getAttribute("idPartitaInCorso");
            if (idPartitaCorrente == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Partita corrente non recuperata");
            }
            if (partitaService.aggiornaPartita(idPartitaCorrente, idScenario)) {
                return ResponseEntity.ok("Partita aggiornata");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Non è stato possibile aggiornare la partita");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID scenario non valido");
        }
    }

    @DeleteMapping("/{idPartita}")
    public ResponseEntity<Void> eliminaPartita(@PathVariable long idPartita) {
        try {
            partitaService.deleteById(idPartita);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}