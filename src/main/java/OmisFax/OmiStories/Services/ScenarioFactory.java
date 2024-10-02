package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScenarioFactory {
    private final ScenarioService scenarioService;

    @Autowired
    public ScenarioFactory(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    public Scenario createScenarioById(long id) {
        Scenario scenario = scenarioService.findById(id);
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario con id " + id + " non trovato");
        }
        return scenario;
    }
}
