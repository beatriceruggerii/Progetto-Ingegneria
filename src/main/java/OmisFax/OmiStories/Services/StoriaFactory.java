package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import OmisFax.OmiStories.Services.interfaces.IStoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoriaFactory {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private StoriaRepository storiaRepository;
    @Autowired
    private ScenarioFactory scenarioFactory;

    public Storia createStoria(String titolo, String username){
        Utente utente = utenteService.trovaUtente(username);
        if(utente==null){
            throw new IllegalArgumentException("Utente non valido, rieffettua l'accesso.");
        }
        return new Storia(titolo,utente);
    }

    public Storia createStoriaCompleta(StoriaDTO payload, String username) {
        String titolo = payload.getTitolo().trim();
        String descrizioneIniziale = payload.getDescrizioneIniziale().trim();

        if (storiaRepository.findStoriaByTitolo(titolo) != null) {
            throw new IllegalArgumentException("Esiste già una storia con questo titolo.");
        }
        Storia storia = createStoria(titolo, username);
        scenarioFactory.createScenarioIniziale(storia, descrizioneIniziale);
        storiaRepository.save(storia);
        System.out.println("Storia salvata");
        return storia;

    }

}
