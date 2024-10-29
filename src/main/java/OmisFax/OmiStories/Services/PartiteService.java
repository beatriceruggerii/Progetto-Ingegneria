package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.PartitaDTO;
import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Partita;
import OmisFax.OmiStories.Repositories.PartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartiteService {
    @Autowired
    private PartitaRepository partitaRepository;
    @Autowired
    private ScenarioService scenarioService;

    public List<PartitaDTO> trovaPartitePerUtente(String username) {
        List<Partita> partite = partitaRepository.findByGiocatoreUsername(username);
        List<PartitaDTO> partiteDTOs = new ArrayList<>();

        for (Partita partita : partite) {
            String descrizioneIniziale = scenarioService.findByStoriaAndInizialeTrue(partita.getStoria()).getTesto();

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
}
