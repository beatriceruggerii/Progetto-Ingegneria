package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.ScenarioDTO;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
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
}

