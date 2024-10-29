package OmisFax.OmiStories.Services.interfaces;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.DTOs.ScenarioDTO;

import java.util.Map;

public interface IScenarioService {
    String salvaScenario(ScenarioDTO scenariodto, Storia storia);
    boolean salvaScenario(Scenario scenario);
    Scenario findById(long id);
    boolean modificaScenario(long idScenario, ScenarioDTO nuovoScenario);
    Map<String, Object> fetchScenario(Long idScenario);
    Map<String, Object> responseScenarioIniziale(String titolo);
    Scenario findByStoriaAndInizialeTrue(Storia storia);
}

