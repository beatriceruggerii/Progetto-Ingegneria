package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaCompletaDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
import OmisFax.OmiStories.Services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoriaService implements IStoriaService {
    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private StoriaFactory storiaFactory;

    @Autowired
    private ScenarioFactory scenarioFactory;

    @Autowired
    private IScenarioService scenarioService;

    @Autowired
    private IScenariService scenariService;

    @Autowired
    private IScelteService scelteService;

    @Autowired
    private IIndovinelliService indovinelliService;

    @Autowired
    private IOggettiService oggettiService;

    public Storia salvaStoria(StoriaDTO payload, String username) {
        String titolo = payload.getTitolo().trim();
        String descrizioneIniziale = payload.getDescrizioneIniziale().trim();

        System.out.println("---------\n" +
                "dati ricevuti per il salvataggio della soria:\n" +
                "titolo: " + titolo + "\n" +
                "scen iniziale: " + descrizioneIniziale + "\n" +
                "username utente: " + username);

        if (storiaRepository.findStoriaByTitolo(titolo) != null) {
            throw new IllegalArgumentException("Esiste già una storia con questo titolo.");
        }
        Storia storia = storiaFactory.createStoria(titolo, username);
        Scenario scenarioIniziale = scenarioFactory.createScenarioIniziale(storia, descrizioneIniziale);

        storiaRepository.save(storia);
        scenarioService.salvaScenario(scenarioIniziale);
        System.out.println("Storia salvata");
        return storia;

    }



    public Storia getStoria(String titolo) {
        return storiaRepository.findStoriaByTitolo(titolo);
    }

    public Map<String, Object> responseDatiStoria(String titolo) {
        //recupero tutti i dati relativi a quella storia
        Map<String, Object> responseData = new HashMap<>();

        //oggetto storia
        Storia storia = storiaRepository.findStoriaByTitolo(titolo);
        responseData.put("storia", storia);

        //scenari
        List<Scenario> scenari = scenariService.findByStoria(storia);
        responseData.put("scenari", scenari);

        //scelte
        List<Scelta> scelte = scelteService.findByStoria(storia);
        responseData.put("scelte", scelte);

        //indovinelli
        List<Indovinello> indovinelli = indovinelliService.findByStoria(storia);
        responseData.put("indovinelli", indovinelli);

        //oggetti
        List<Oggetto> oggetti = oggettiService.findByStoria(storia);
        responseData.put("oggetti", oggetti);

        return responseData;

    }

    public Storia findStoriaByTitolo(String titolo) {
        return storiaRepository.findStoriaByTitolo(titolo);
    }

    public List<Storia> findAll(){
        return storiaRepository.findAll();
    }

}
