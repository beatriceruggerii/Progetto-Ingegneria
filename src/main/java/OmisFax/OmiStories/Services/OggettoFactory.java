package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OggettoFactory {
    @Autowired
    private ScenarioService scenarioService;
    @Autowired
    private OggettoRepository oggettoRepository;

    public Oggetto createOggetto(String nomeOggetto, long idMadre, long idControllore, Storia storia) {
        if (idControllore == idMadre) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza e quello di destinazione non possono essere uguali!");
        }

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioControllore = scenarioService.findById(idControllore);
                
        if (scenarioControllore.equals(scenarioService.findByStoriaAndInizialeTrue(storia))) {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza non può richiedere oggetti di accesso!");
        }

        return new Oggetto(nomeOggetto, scenarioMadre, scenarioControllore);
    }
}
