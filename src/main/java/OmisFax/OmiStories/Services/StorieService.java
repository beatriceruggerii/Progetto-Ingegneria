package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.StoriaCompletaDTO;
import OmisFax.OmiStories.DTOs.StoriaDTO;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.StoriaRepository;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import OmisFax.OmiStories.Services.interfaces.IStorieService;
import OmisFax.OmiStories.Services.interfaces.IUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StorieService implements IStorieService {
    @Autowired
    private StoriaRepository storiaRepository;
    @Autowired
    private IUtenteService utenteService;
    @Autowired
    private IScenarioService scenarioService;

    public List<Storia> listaStorie() {
        return storiaRepository.findAll();
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
            String descrizioneIniziale = scenarioService.findByStoriaAndInizialeTrue(storia).getTesto();
            StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
            StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());
            storiaCompletaDTOS.add(storiaCompletaDTO);
        }

        responseData.put("storiaCompletaDTOS", storiaCompletaDTOS);
        return responseData;
    }

    public Map<String, Object> responseStorieAutore(String username) {
        Map<String, Object> responseData = new HashMap<>();
        Utente user = utenteService.findByUsername(username);
        System.out.println("Utente: " + user.getUsername());

        List<Storia> listaStorieUtente = storiaRepository.findByAutore(user);
        List<StoriaCompletaDTO> listaStorieUtenteDTOs = new ArrayList<>();

        for (Storia storia : listaStorieUtente) {
            System.out.println("Storia: " + storia.getTitolo());
            String descrizioneIniziale = scenarioService.findByStoriaAndInizialeTrue(storia).getTesto();

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
                String descrizioneIniziale = scenarioService.findByStoriaAndInizialeTrue(storia).getTesto();

                StoriaDTO storiaDTO = new StoriaDTO(storia.getTitolo(), descrizioneIniziale);
                StoriaCompletaDTO storiaCompletaDTO = new StoriaCompletaDTO(storiaDTO, storia.getAutore().getUsername());

                listaFiltrataStoria.add(storiaCompletaDTO);
            }
        }

        responseData.put("listaFiltrataStoria", listaFiltrataStoria);
        return responseData;
    }

    public List<Storia> findAll(){
        return storiaRepository.findAll();
    }
}
