package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OggettoFactory {
    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private ScenarioRepository scenarioRepository;

    public Oggetto createOggetto(String nomeOggetto, long idMadre, long idControllore, Storia storia) {
        if (idControllore == idMadre) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza e quello di destinazione non possono essere uguali!");
        }

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioControllore = scenarioService.findById(idControllore);
                
        if (scenarioControllore.equals(scenarioRepository.findByStoriaAndInizialeTrue(storia))) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza non può richiedere oggetti di accesso!");
        }

        return new Oggetto(nomeOggetto, scenarioMadre, scenarioControllore);
    }
}
