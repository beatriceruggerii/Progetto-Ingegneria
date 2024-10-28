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
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        System.out.println("Giocatore: " + giocatore.getUsername());
        System.out.println("Storia: " + storia.getTitolo());

        Scenario scenarioIniziale = scenarioRepository.findByStoriaAndInizialeTrue(storia);
        System.out.println("Scenario iniziale " + scenarioIniziale.toString());

        Partita partita = new Partita(giocatore, storia, scenarioIniziale);
        partitaRepository.save(partita);
        return partita;
    }

    public boolean aggiornaPartita(long idPartita, long idScenarioFiglio){
        Partita partita = partitaRepository.findById(idPartita);
        Optional<Scenario> ultimoScenarioOptional = scenarioRepository.findById(idScenarioFiglio);
        if(ultimoScenarioOptional.isPresent()){
            partita.setUltimoScenario(ultimoScenarioOptional.get());
            partitaRepository.save(partita);
            return true;
        }
        else {
            return false;
        }
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

            PartitaDTO partitaDTO = new PartitaDTO(storiaDTO, scenarioDTO, partita.getStoria().getAutore().getUsername(), partita.getId());
            partiteDTOs.add(partitaDTO);
        }

        return partiteDTOs;
    }

    public void deleteById(long idPartita) {
        partitaRepository.deleteById(idPartita);
    }

    public Partita getPartita(long idPartita) {
        return partitaRepository.findById(idPartita);
    }
}
