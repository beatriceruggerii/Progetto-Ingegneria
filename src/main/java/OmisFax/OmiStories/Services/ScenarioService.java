package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService {
    @Autowired
    private ScenarioRepository scenarioRepository;

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

    public Scenario findByTitoloAndStoria(String titolo, Storia storia) {
        return scenarioRepository.findByTitoloAndStoria(titolo, storia);
    }

    public List<Scenario> findByStoria(Storia storia) {
        return scenarioRepository.findByStoria(storia);
    }
}
