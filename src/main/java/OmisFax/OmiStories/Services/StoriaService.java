package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaCompletaDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Repositories.UtenteRepository;
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
    private UtenteRepository utenteRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private StoriaFactory storiaFactory;

    @Autowired
    private ScenarioFactory scenarioFactory;

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private SceltaService sceltaService;

    @Autowired
    private OggettoService oggettoService;

    @Autowired
    private IndovinelloService indovinelloService;

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

    public Map<String, Object> responseFetchStorie() {
        Map<String, Object> responseData = new HashMap<>();
        List<Storia> listaStorie = listaStorie();
        if (listaStorie.isEmpty()) {
            System.out.println("Storie non trovate");
            throw new RuntimeException("Storie non trovate");
        }
        //debug
        System.out.println("storie trovate: " + listaStorie.size());
        for (int i = 0; i < listaStorie.size(); i++) {
            System.out.println(listaStorie.get(i).toString());
        }

        List<StoriaCompletaDTO> storiaCompletaDTOS = new ArrayList<>();
        for (Storia storia : listaStorie) {
            String descrizioneIniziale = scenarioRepository.findByStoriaAndInizialeTrue(storia).getTesto();
            StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
            StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());
            storiaCompletaDTOS.add(storiaCompletaDTO);
        }

        responseData.put("storiaCompletaDTOS", storiaCompletaDTOS);
        return responseData;
    }

    public List<Storia> listaStorie() {
        return storiaRepository.findAll();
    }

    public Map<String, Object> responseStorieAutore(String username) {
        Map<String, Object> responseData = new HashMap<>();
        Utente user = utenteRepository.findByUsername(username);
        System.out.println("Utente: " + user.getUsername());

        List<Storia> listaStorieUtente = storiaRepository.findByAutore(user);
        List<StoriaCompletaDTO> listaStorieUtenteDTOs = new ArrayList<>();

        for (Storia storia : listaStorieUtente) {
            System.out.println("Storia: " + storia.getTitolo());
            String descrizioneIniziale = scenarioRepository.findByStoriaAndInizialeTrue(storia).getTesto();

            StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
            StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, username);
            listaStorieUtenteDTOs.add(storiaCompletaDTO);
        }

        responseData.put("listaStorieUtenteDTOs", listaStorieUtenteDTOs);

        return responseData;
    }

    public Map<String, Object> responseFiltroTitolo(String titolo) {
        Map<String, Object> responseData = new HashMap<>();
        List<Storia> listaStorie = listaStorie();

        if (listaStorie == null || listaStorie.isEmpty()) {
            throw new IllegalArgumentException("Storie non trovate");
        }

        // Rimuove le virgolette e spazi bianchi dal titolo cercato
        titolo = titolo.replace("\"", "").trim();

        System.out.println(titolo);

        List<StoriaCompletaDTO> listaFiltrataStoria = new ArrayList<>();

        for (Storia storia : listaStorie) {
            if (storia.getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                String descrizioneIniziale = scenarioRepository.findByStoriaAndInizialeTrue(storia).getTesto();

                StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
                StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());

                listaFiltrataStoria.add(storiaCompletaDTO);
            }
        }

        responseData.put("listaFiltrataStoria", listaFiltrataStoria);
        return responseData;
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
        List<Scenario> scenari = scenarioService.findByStoria(storia);
        responseData.put("scenari", scenari);

        //scelte
        List<Scelta> scelte = sceltaService.findByStoria(storia);
        responseData.put("scelte", scelte);

        //indovinelli
        List<Indovinello> indovinelli = indovinelloService.findByStoria(storia);
        responseData.put("indovinelli", indovinelli);

        //oggetti
        List<Oggetto> oggetti = oggettoService.findByStoria(storia);
        responseData.put("oggetti", oggetti);

        return responseData;

    }

}
