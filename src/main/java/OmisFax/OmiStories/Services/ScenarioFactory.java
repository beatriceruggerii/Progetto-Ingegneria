package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScenarioFactory {
    private final ScenarioRepository scenarioRepository;

    @Autowired
    public ScenarioFactory(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }


    public Scenario createScenario(Storia storia, String titolo, String testo){
        return new Scenario(storia, titolo, testo);
    }

    public Scenario createScenarioById(long id) {
        Scenario scenario = scenarioRepository.findById(id);
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario con id " + id + " non trovato");
        }
        return scenario;
    }
}
