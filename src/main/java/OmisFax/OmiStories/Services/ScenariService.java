package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScenariService {
    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private ScenarioFactory scenarioFactory;
    @Autowired
    private StoriaRepository storiaRepository;

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

    public List<Scenario> findByStoria(Storia storia) {
        return scenarioRepository.findByStoria(storia);
    }

}
