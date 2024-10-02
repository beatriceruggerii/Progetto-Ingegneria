package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.OggettoDTO;
import OmisFax.OmiStories.Entities.Oggetto;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OggettoService {
    @Autowired
    private OggettoRepository oggettoRepository;

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private OggettoFactory oggettoFactory;



    public boolean salvaOggetto(Oggetto oggetto){
        try {
            oggettoRepository.save(oggetto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Oggetto> findByStoria(Storia storia){
        return oggettoRepository.findALlByStoria(storia);
    }

    public ResponseEntity<String> salvaOggetto(OggettoDTO payload, Storia storia) {
        String nomeOggetto = payload.getNomeOggetto();
        long idMadre = payload.getScenarioMadreOggetto();
        long idControllore = payload.getScenarioControlloreOggetto();

        //DEBUG
        System.out.println("MADRE: " + idMadre + " CONTROLLORE: " + idControllore);

        Oggetto oggetto = oggettoFactory.createOggetto(nomeOggetto, idMadre, idControllore, storia);
        try {
            oggettoRepository.save(oggetto);
            return ResponseEntity.ok("Oggetto salvato con successo: " + payload.getNomeOggetto());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Impossibile salvare l'oggetto.");
        }

    }


}
