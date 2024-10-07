package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaCompletaDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Scelta;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ResponseEntity<Map<String, Object>> responseFetchStorie(HttpSession session) {
        Map<String, Object> responseData = new HashMap<>();
        List<Storia> listaStorie = listaStorie();
        session.setAttribute("listaStorie", listaStorie);
        if(listaStorie.isEmpty()){
            System.out.println("Storie non trovate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        System.out.println("storie trovate: "+ listaStorie.size());
        for(int i = 0; i<listaStorie.size(); i++){
            System.out.println(listaStorie.get(i).toString());
        }

        List<StoriaCompletaDTO> storiaCompletaDTOS = new ArrayList<>();
        for(Storia storia : listaStorie){
            String descrizioneIniziale = scenarioService.findByTitoloAndStoria("Scenario Iniziale", storia).getTesto();
            StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
            StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());
            storiaCompletaDTOS.add(storiaCompletaDTO);
        }

        responseData.put("storiaCompletaDTOS", storiaCompletaDTOS);
        return ResponseEntity.ok(responseData);
    }

    public List<Storia> listaStorie(){
        return storiaRepository.findAll();
    }


    public ResponseEntity<Map<String, Object>> responseFiltroAutore(String username, HttpSession session) {
        Map<String, Object> responseData = new HashMap<>();
        List<Storia> listaStorie = (List<Storia>)session.getAttribute("listaStorie");
        if(listaStorie.isEmpty()){
            System.out.println("Storie non trovate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        username = username.replace("\"", "").trim();

        List<StoriaCompletaDTO> listaFiltrataStoria = new ArrayList<>();

        for(Storia storia : listaStorie){
            if(storia.getAutore().getUsername().equals(username)){
                String descrizioneIniziale = scenarioService.findByTitoloAndStoria("Scenario Iniziale", storia).getTesto();

                StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
                StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, username);
                listaFiltrataStoria.add(storiaCompletaDTO);
            }
        }

        responseData.put("listaFiltrataStoria", listaFiltrataStoria);
        return ResponseEntity.ok(responseData);

    }

    public ResponseEntity<Map<String, Object>> responseFiltroTitolo(String titolo, HttpSession session){
        Map<String, Object> responseData = new HashMap<>();
        List<Storia> listaStorie = (List<Storia>) session.getAttribute("listaStorie");

        if(listaStorie == null || listaStorie.isEmpty()){
            System.out.println("Storie non trovate");
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Rimuove le virgolette e spazi bianchi dal titolo cercato
        titolo = titolo.replace("\"", "").trim();

        System.out.println(titolo);

        List<StoriaCompletaDTO> listaFiltrataStoria = new ArrayList<>();

        for(Storia storia : listaStorie){
            if (storia.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                String descrizioneIniziale = scenarioService.findByTitoloAndStoria("Scenario Iniziale", storia).getTesto();

                StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
                StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());

                listaFiltrataStoria.add(storiaCompletaDTO);
            }
        }

        responseData.put("listaFiltrataStoria", listaFiltrataStoria);
        return ResponseEntity.ok(responseData);
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
