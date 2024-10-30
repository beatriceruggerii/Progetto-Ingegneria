package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.OggettoRepository;
import OmisFax.OmiStories.Services.interfaces.IInventarioService;
import OmisFax.OmiStories.Services.interfaces.IOggettiService;
import OmisFax.OmiStories.Services.interfaces.IPartitaService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OggettiService implements IOggettiService {
    @Autowired
    private OggettoRepository oggettoRepository;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IInventarioService inventarioService;
    @Autowired
    private IPartitaService partitaService;

    public Map<String, Object> fetchOggettiStoria(Storia storia) {

        if (storia == null) {
            System.out.println("storia non trovata");
            throw new IllegalArgumentException("Storia non trovata");
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Oggetto> listaOggetti = oggettoRepository.findAllByStoria(storia);
        if (listaOggetti.isEmpty()) {
            System.out.println("nessun oggetto trovato");
            throw new NoSuchElementException("Nessun oggetto trovato");
        }
        responseData.put("listaOggetti", listaOggetti);
        return responseData;
    }

    public List<Oggetto> findByStoria(Storia storia) {
        return oggettoRepository.findAllByStoria(storia);
    }


    public Map<String, Object> getOggetti(long idScenario) {
        Scenario scenario = scenarioService.findById(idScenario);
        List<Oggetto> oggetti = oggettoRepository.findByScenarioMadre(scenario);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("oggetti", oggetti);
        return responseData;
    }

    public Map<String, Object> getOggettiControllori(long idScenario, long idPartita) {
        Scenario scenario = scenarioService.findById(idScenario);
        List<Oggetto> oggettiNecessari = oggettoRepository.findByScenarioControllore(scenario);

        //controllo che l'oggetto sia presente nell'inventario della partita
        Partita partita = partitaService.findById(idPartita).get();
        List<Inventario> inventario = inventarioService.findAllByPartita(partita);

        // Creo una lista per gli oggetti mancanti nell'inventario
        List<Oggetto> oggettiMancanti = new ArrayList<>();

        for (Oggetto oggetto : oggettiNecessari) {
            boolean presenteNellInventario = false;
            for (Inventario itemInventario : inventario) {
                if (itemInventario.getOggetto().equals(oggetto)) {
                    presenteNellInventario = true;
                    break;
                }
            }
            // Se l'oggetto non è presente nell'inventario, lo aggiungo agli oggetti mancanti
            if (!presenteNellInventario) {
                oggettiMancanti.add(oggetto);
            }
        }

        // Creo la risposta con gli oggetti necessari e quelli mancanti
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("oggettiNecessari", oggettiNecessari);
        responseData.put("oggettiMancanti", oggettiMancanti);
        return responseData;
    }
}
