package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Partita salvaPartita(String titoloStoria, String username) {
        titoloStoria = titoloStoria.replace("\"", "").trim();
        System.out.println("Username recuperato: " + username);
        System.out.println("Titolo storia: " + titoloStoria);

        Utente giocatore = utenteRepository.findByUsername(username);
        System.out.println("Giocatore: " + giocatore.toString());

        Storia storia = storiaRepository.findStoriaByTitolo(titoloStoria);
        System.out.println("Storia: " + storia.toString());

        Scenario scenarioIniziale = scenarioRepository.findByStoriaAndInizialeTrue(storia);
        System.out.println("Scenario iniziale " + scenarioIniziale.toString());

        Partita partita = new Partita(giocatore, storia, scenarioIniziale);
        partitaRepository.save(partita);
        return partita;
    }

    public List<PartitaDTO> trovaPartitePerUtente(String username) {
        List<Partita> partite = partitaRepository.findByGiocatoreUsername(username);
        List<PartitaDTO> partiteDTOs = new ArrayList<>();

        for (Partita partita : partite) {
            String descrizioneIniziale = scenarioRepository.findByStoriaAndInizialeTrue(partita.getStoria()).getTesto();

            StoriaDTO storiaDTO = new StoriaDTO(
                    partita.getStoria().getTitolo(),
                    descrizioneIniziale
            );

            ScenarioDTO scenarioDTO = new ScenarioDTO(
                    partita.getUltimoScenario().getId(),
                    partita.getUltimoScenario().getTitolo(),
                    partita.getUltimoScenario().getTesto()
            );

            PartitaDTO partitaDTO = new PartitaDTO(storiaDTO, scenarioDTO, username, partita.getId());
            partiteDTOs.add(partitaDTO);
        }

        return partiteDTOs;
    }

    public void deleteById(long idPartita) {
        partitaRepository.deleteById(idPartita);
    }
}
