package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndovinelloService {
    @Autowired
    private  IndovinelloRepository indovinelloRepository;
    @Autowired
    private ScenarioService scenarioService;

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
        if (idFiglio!=idMadre) {
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

    public Map<String, Object> responseFetchIndovinelli(Storia storia) {
        if (storia == null) {
            System.out.println("storia non trovata");
            throw new IllegalArgumentException("Storia non trovata.");
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Indovinello> listaIndovinelli = new ArrayList<>();
        listaIndovinelli = findByStoria(storia);
        if(listaIndovinelli.isEmpty()){
            System.out.println("nessun indovinello trovato");
            responseData.put("errorMessage","Nessun indovinello trovato.");
        }
        //debug
        System.out.println("Indovinelli trovati: "+ listaIndovinelli.size());
        for(int i = 0; i<listaIndovinelli.size(); i++){
            System.out.println(listaIndovinelli.get(i).toString());
        }
        responseData.put("listaIndovinelli", listaIndovinelli);
        return responseData;
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

    public List<Indovinello> findByStoria(Storia storia){
        return indovinelloRepository.findByStoria(storia);
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

    public List<Indovinello> findByScenarioMadre(long idScenario){
        Scenario scenario = scenarioService.findById(idScenario);
        return indovinelloRepository.findByScenarioMadre(scenario);
    }




}
