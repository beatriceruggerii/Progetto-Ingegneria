package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScenarioService {
    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private ScenarioFactory scenarioFactory;

    @Autowired
    private SceltaRepository sceltaRepository;

    public ResponseEntity<String> salvaScenario(ScenarioDTO scenariodto, HttpSession session) {
        System.out.println("richiesta ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");

        Scenario scenario = scenarioFactory.createScenario(storia, scenariodto.getTitolo(), scenariodto.getTesto());

        try {
            scenarioRepository.save(scenario);
            System.out.println("Scenario salvato");
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Scenario salvato con successo");
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare lo scenario, prova a cambiare titolo.");
        }
    }

    public ResponseEntity<Map<String, Object>> fetchScenari(HttpSession session) {
        System.out.println("richiesta di fetch scenari ricevuta");
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scenario> listaScenari = new ArrayList<>();
        listaScenari = scenarioRepository.findByStoria(storia);
        if (listaScenari.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        //debud
        System.out.println("scenari trovati: " + listaScenari.size());
        for (int i = 0; i < listaScenari.size(); i++) {
            System.out.println(listaScenari.get(i).toString());
        }
        responseData.put("listaScenari", listaScenari);
        return ResponseEntity.ok(responseData);
    }

    public boolean salvaScenario(Scenario scenario){
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
        return scenarioRepository.findById(id);
    }

    public Scenario findByTitoloAndStoria(String titolo, Storia storia){
        return scenarioRepository.findByTitoloAndStoria(titolo, storia);
    }

    public boolean modificaScenario(Long idScenario, ScenarioDTO nuovoScenario) {
        System.out.println("-------");
        System.out.println("Modifica dello scenario in corso");
        Scenario scenarioEsistente = scenarioRepository.findById(nuovoScenario.getId());
        if (scenarioEsistente == null) {
            return false;
        }
        scenarioEsistente.setTesto(nuovoScenario.getTesto());
        return salvaScenario(scenarioEsistente);
    }

    public ResponseEntity<Map<String, Object>> fetchScenaroFiglio(Long idScelta, HttpSession session) {
        HashMap<String,Object> responseData = new HashMap<>();
        Scelta scelta = sceltaRepository.findById(Long.parseLong(String.valueOf(idScelta)));
        Scenario scenario = scelta.getScenarioFiglio();
        responseData.put("scenarioFiglio",scenario);
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<Map<String, Object>> fetchScenario(Long idScenario) {
        long id = idScenario;
        Scenario scenario = scenarioRepository.findById(id);
        HashMap<String,Object> responseData = new HashMap<>();
        responseData.put("scenario",scenario);
        return ResponseEntity.ok(responseData);
    }
}

