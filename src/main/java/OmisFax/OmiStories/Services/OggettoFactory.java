package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OggettoFactory {

    private final ScenarioFactory scenarioFactory;

    private ScenarioService scenarioService;

    @Autowired
    public OggettoFactory(ScenarioFactory scenarioFactory, ScenarioService scenarioService) {
        this.scenarioFactory = scenarioFactory;
        this.scenarioService = scenarioService;
    }

    public Oggetto createOggetto(String nomeOggetto, long idMadre, long idControllore, Storia storia) {
        if (idControllore == idMadre) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza e quello di destinazione non possono essere uguali!");
        }

        Scenario scenarioMadre = scenarioFactory.createScenarioById(idMadre);
        Scenario scenarioControllore = scenarioFactory.createScenarioById(idControllore);
                
        if (scenarioControllore.equals(scenarioService.findByTitoloAndStoria("Scenario Iniziale", storia))) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza non può richiedere oggetti di accesso!");
        }

        return new Oggetto(nomeOggetto, scenarioMadre, scenarioControllore);
    }
}
