package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.DTOs.IndovinelloDTO;
import OmisFax.OmiStories.Entities.*;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
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
public class IndovinelloService {
    @Autowired
    private  IndovinelloRepository indovinelloRepository;
    @Autowired
    private ScenarioService scenarioService;

    public ResponseEntity<String> responseSalvaIndovinello(IndovinelloDTO infoIndovinello, HttpSession session) {
        String testoIndovinello = infoIndovinello.getTesto();
        String testoSoluzione = infoIndovinello.getSoluzione();
        long idMadre = infoIndovinello.getIdMadre();
        long idFiglio = infoIndovinello.getIdFiglio();

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Indovinello indovinello = new Indovinello(testoIndovinello, scenarioMadre, scenarioFiglio, testoSoluzione);

        if (scenarioMadre == null || scenarioFiglio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (idFiglio!=idMadre) {
            if (registraIndovinello(indovinello)) {
                System.out.println("indovinello salvato");
                return ResponseEntity.ok("Indovinello salvato");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }

        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

    public ResponseEntity<Map<String, Object>> responseFetchIndovinelli(Storia storia) {
        if (storia == null) {
            System.out.println("storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Indovinello> listaIndovinelli = new ArrayList<>();
        listaIndovinelli = findByStoria(storia);
        if(listaIndovinelli.isEmpty()){
            System.out.println("nessun indovinello trovato");
            responseData.put("errorMessage","Nessun indovinello trovato.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
        //debug
        System.out.println("Indovinelli trovati: "+ listaIndovinelli.size());
        for(int i = 0; i<listaIndovinelli.size(); i++){
            System.out.println(listaIndovinelli.get(i).toString());
        }
        responseData.put("listaIndovinelli", listaIndovinelli);
        return ResponseEntity.ok(responseData);
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
