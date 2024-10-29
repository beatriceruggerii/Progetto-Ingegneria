package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
import OmisFax.OmiStories.Services.interfaces.IIndovinelloService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndovinelloService implements IIndovinelloService {
    @Autowired
    private IndovinelloRepository indovinelloRepository;
    @Autowired
    private IScenarioService scenarioService;

    public String responseSalvaIndovinello(IndovinelloDTO infoIndovinello) {
        String testoIndovinello = infoIndovinello.getTesto();
        String testoSoluzione = infoIndovinello.getSoluzione();
        long idMadre = infoIndovinello.getIdMadre();
        long idFiglio = infoIndovinello.getIdFiglio();

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Indovinello indovinello = new Indovinello(testoIndovinello, scenarioMadre, scenarioFiglio, testoSoluzione);

        if (scenarioMadre == null || scenarioFiglio == null) {
            throw new IllegalArgumentException("Scenario madre o figlio mancante");
        }
        if (idFiglio != idMadre) {
            if (registraIndovinello(indovinello)) {
                System.out.println("indovinello salvato");
                return "indovinello salvato con successo";
            }
        } else {
            throw new IllegalArgumentException("Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }

        System.out.println("errore generico");
        throw new IllegalArgumentException("Something went wrong");
    }


    public boolean registraIndovinello(Indovinello indovinello) {
        try {
            indovinelloRepository.save(indovinello);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificaIndovinello(Long idIndovinello, IndovinelloDTO nuovoIndovinello) {
        Indovinello indovinelloEsistente = indovinelloRepository.findById(nuovoIndovinello.getId());
        if (indovinelloEsistente == null) {
            return false;
        }
        indovinelloEsistente.setDescrizione(nuovoIndovinello.getTesto());
        indovinelloEsistente.setRispostaCorretta(nuovoIndovinello.getSoluzione());
        return registraIndovinello(indovinelloEsistente);
    }



}
