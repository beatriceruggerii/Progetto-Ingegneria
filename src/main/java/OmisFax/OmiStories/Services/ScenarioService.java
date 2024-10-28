package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class ScenarioService {
    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private ScenarioFactory scenarioFactory;
    @Autowired
    private SceltaRepository sceltaRepository;
    @Autowired
    private StoriaRepository storiaRepository;


    public String salvaScenario(ScenarioDTO scenariodto, Storia storia) {
        System.out.println("richiesta ricevuta");

        Scenario scenario = scenarioFactory.createScenario(storia, scenariodto.getTitolo().trim(), scenariodto.getTesto().trim());
        scenarioRepository.save(scenario);
        System.out.println("Scenario salvato");
        return "Scenario salvato con successo";

    }

    public Map<String, Object> fetchScenari(Storia storia) {
        System.out.println("richiesta di fetch scenari ricevuta");
        if (storia == null) {
            throw new IllegalArgumentException("Storia non trovata");
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scenario> listaScenari = new ArrayList<>();
        listaScenari = scenarioRepository.findByStoria(storia);

        responseData.put("listaScenari", listaScenari);
        return responseData;
    }

    public boolean salvaScenario(Scenario scenario) {
        if (scenarioRepository.findByTitoloAndStoria(scenario.getTitolo(), scenario.getStoria()) != null) {
            return false;
        }
        try {
            scenarioRepository.save(scenario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Scenario> findByStoria(Storia storia) {
        return scenarioRepository.findByStoria(storia);
    }

    public Scenario findById(long id) {
        Optional<Scenario> scenarioOptional = scenarioRepository.findById(id);
        if (scenarioOptional.isPresent()) {
            return scenarioOptional.get();
        } else {
            return null;
        }
    }

    public Scenario findByTitoloAndStoria(String titolo, Storia storia) {
        return scenarioRepository.findByTitoloAndStoria(titolo, storia);
    }

    public boolean modificaScenario(long idScenario, ScenarioDTO nuovoScenario) {
        System.out.println("-------");
        System.out.println("Modifica dello scenario in corso: " + idScenario);
        Optional<Scenario> scenarioEsistente = scenarioRepository.findById(idScenario);

        if (scenarioEsistente.isPresent()) {
            Scenario scenario = scenarioEsistente.get();  // Recupera lo scenario se presente
            System.out.println("Scenario trovato: " + scenario.toString());
            scenario.setTesto(nuovoScenario.getTesto().trim());
            scenarioRepository.save(scenario);
            return true;
        } else {
            System.out.println("Lo scenario con id " + idScenario + " non esiste.");
            return false;
        }
    }

    public Map<String, Object> fetchScenario(Long idScenario) {
        long id = idScenario;
        Scenario scenario = findById(id);
        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("scenario", scenario);
        System.out.println("Scenario ottenuto: " + scenario.toString());
        return responseData;
    }

    public Map<String, Object> responseScenarioIniziale(String titolo) {
        Map<String, Object> responseData = new HashMap<>();
        Storia storia = storiaRepository.findStoriaByTitolo(titolo);
        Scenario scenario = scenarioRepository.findByStoriaAndInizialeTrue(storia);
        responseData.put("scenario", scenario);
        System.out.println("Scenario trovato: " + scenario.toString());
        return responseData;
    }
}

