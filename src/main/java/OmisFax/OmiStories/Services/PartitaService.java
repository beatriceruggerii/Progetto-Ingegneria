package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartitaService {
    @Autowired
    private PartitaRepository partitaRepository;
    @Autowired
    private StoriaRepository storiaRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ScenarioRepository scenarioRepository;

    public void salvaPartita(String titoloStoria, String username) {
        titoloStoria = titoloStoria.replace("\"", "").trim();
        System.out.println("Username recuperato: " + username);
        System.out.println("Titolo storia: " + titoloStoria);

        Utente giocatore = utenteRepository.findByUsername(username);

        Storia storia = storiaRepository.findStoriaByTitolo(titoloStoria);

        Scenario scenarioIniziale = scenarioRepository.findByTitoloAndStoria("Scenario Iniziale", storia);

        Partita partita = new Partita(giocatore, storia, scenarioIniziale);
        partitaRepository.save(partita);
    }

}
