package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.InventarioRepository;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import OmisFax.OmiStories.Services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartitaService implements IPartitaService {
    @Autowired
    private PartitaRepository partitaRepository;
    @Autowired
    private IStoriaService storiaService;
    @Autowired
    private IUtenteService utenteService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private InventarioRepository inventarioRepository;

    public Partita salvaPartita(String titoloStoria, String username) {
        titoloStoria = titoloStoria.replace("\"", "").trim();
        System.out.println("Username recuperato: " + username);
        System.out.println("Titolo storia: " + titoloStoria);

        Utente giocatore = utenteService.findByUsername(username);
        System.out.println("Giocatore: " + giocatore.toString());

        Storia storia = storiaService.findStoriaByTitolo(titoloStoria);
        System.out.println("Giocatore: " + giocatore.getUsername());
        System.out.println("Storia: " + storia.getTitolo());

        Scenario scenarioIniziale = scenarioService.findByStoriaAndInizialeTrue(storia);
        System.out.println("Scenario iniziale " + scenarioIniziale.toString());

        Partita partita = new Partita(giocatore, storia, scenarioIniziale);
        partitaRepository.save(partita);
        return partita;
    }

    public boolean aggiornaPartita(long idPartita, long idScenarioFiglio){
        Partita partita = partitaRepository.findById(idPartita);
        Scenario ultimoScenario = scenarioService.findById(idScenarioFiglio);
        if(ultimoScenario != null){
            partita.setUltimoScenario(ultimoScenario);
            partitaRepository.save(partita);
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteById(long idPartita) {
        inventarioRepository.deleteByPartitaId(idPartita);
        partitaRepository.deleteById(idPartita);
    }

    public Partita getPartita(long idPartita) {
        return partitaRepository.findById(idPartita);
    }

    public Optional<Partita> findById(Long idPartita) {
        return partitaRepository.findById(idPartita);
    }
}
