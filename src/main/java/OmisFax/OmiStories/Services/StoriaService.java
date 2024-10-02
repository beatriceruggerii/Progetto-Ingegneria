package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class StoriaService {
    @Autowired
    private StoriaRepository storiaRepository;

    @Autowired
    private StoriaFactory storiaFactory;

    @Autowired
    private ScenarioFactory scenarioFactory;

    @Autowired
    private ScenarioService scenarioService;

    public ResponseEntity<String> salvaStoria(StoriaDTO payload, HttpSession session) {
        String titolo = payload.getTitolo();
        String descrizioneIniziale = payload.getDescrizioneIniziale();
        String username = (String) session.getAttribute("loggedUsername");

        System.out.println("---------\n" +
                "dati ricevuti per il salvataggio della soria:\n" +
                "titolo: " + titolo +"\n" +
                "scen iniziale: " +descrizioneIniziale +"\n" +
                "username utente: " + username);

        if(storiaRepository.findStoriaByTitolo(titolo) != null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Esiste già una storia con questo titolo.");
        }
        Storia storia = storiaFactory.createStoria(titolo, username);
        Scenario scenarioIniziale = scenarioFactory.createScenarioIniziale(storia, descrizioneIniziale);

        try{
            storiaRepository.save(storia);
            scenarioService.salvaScenario(scenarioIniziale);
            System.out.println("Storia salvata");
            session.setAttribute("storiaCorrente", storia);
            return ResponseEntity.ok("Storia salvata con successo");
        } catch(Exception e){
            System.out.println("Storia non salvata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("C'è stato un errore con il salvataggio della tua storia, ritenta.");
        }
    }

    /*
    public boolean salvaStoria(Storia storia){
        if (storiaRepository.findStoriaByTitolo(storia.getTitolo()) != null) {
            return false;
        }
        try {
            storiaRepository.save(storia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

     */

    public Storia getStoria(String titolo){
        return storiaRepository.findStoriaByTitolo(titolo);
    }

}
