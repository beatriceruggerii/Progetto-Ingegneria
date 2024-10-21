package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScenarioFactory {
    @Autowired
    private final ScenarioRepository scenarioRepository;

    @Autowired
    public ScenarioFactory(ScenarioRepository scenarioRepository) {
        this.scenarioRepository = scenarioRepository;
    }

    public Scenario createScenarioIniziale(Storia storia, String testo){
        return createScenario(storia, "Scenario Iniziale", testo, true);
    }

    public Scenario createScenario(Storia storia, String titolo, String testo){
        return createScenario(storia, titolo, testo, false);
    }

    public Scenario createScenario(Storia storia, String titolo, String testo, boolean iniziale) {
        // Se lo scenario è "iniziale", verifica che non esista già uno scenario iniziale per la storia
        if (iniziale) {
            Scenario scenarioInizialeEsistente = scenarioRepository.findByStoriaAndInizialeTrue(storia);
            if (scenarioInizialeEsistente != null) {
                throw new RuntimeException("Esiste già uno scenario iniziale per questa storia");
            }
        }

        // Crea e restituisci il nuovo Scenario
        return new Scenario(storia, titolo, testo, iniziale);
    }

}
