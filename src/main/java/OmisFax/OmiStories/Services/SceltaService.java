package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.SceltaDTO;
import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SceltaService {
    @Autowired
    private SceltaRepository sceltaRepository;
    @Autowired
    private ScenarioService scenarioService;

    public Scelta responseSalvaScelta(SceltaDTO infoScelta){
        String testoScelta = infoScelta.getTesto();
        long idMadre = infoScelta.getIdMadre();
        long idFiglio = infoScelta.getIdFiglio();

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Scelta scelta = new Scelta(testoScelta, scenarioMadre, scenarioFiglio);

        if (scenarioMadre == null || scenarioFiglio == null) {
            throw new IllegalArgumentException("Errore: scenario madre o figlio mancante");
        }
        if (idFiglio!=idMadre) {
            if (registraScelta(scelta)) {
                System.out.println("scelta salvata");
                return scelta;
            }
        } else {
            throw new IllegalArgumentException("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }
        System.out.println("errore generico");
        throw new IllegalArgumentException("Something went wrong");
    }


    public boolean registraScelta(Scelta scelta) {
        try {
            sceltaRepository.save(scelta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificaScelta(SceltaDTO nuovaScelta) {
        Scelta sceltaEsistente = sceltaRepository.findById(nuovaScelta.getId());
        if (sceltaEsistente == null) {
            return false;
        }
        sceltaEsistente.setDescrizione(nuovaScelta.getTesto());
        return registraScelta(sceltaEsistente);
    }

}
