package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Entities.Utente;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import OmisFax.OmiStories.Repositories.ScenarioRepository;
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

    public ResponseEntity<String> responseSalvaScelta(Map<String, String> infoScelta, HttpSession session){
        String testoScelta = infoScelta.get("testo");
        long idMadre = Long.parseLong(infoScelta.get("idMadre"));
        long idFiglio = Long.parseLong(infoScelta.get("idFiglio"));

        Scenario scenarioMadre = scenarioService.findById(idMadre);
        Scenario scenarioFiglio = scenarioService.findById(idFiglio);

        Scelta scelta = new Scelta(testoScelta, scenarioMadre, scenarioFiglio);

        if (scenarioMadre == null || scenarioFiglio == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: scenario madre o figlio mancante");
        }
        if (idFiglio!=idMadre) {
            if (registraScelta(scelta)) {
                System.out.println("scelta salvata");
                session.setAttribute("sceltaCorrente", scelta);
                return ResponseEntity.ok("Scelta salvata");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errore: Lo scenario di partenza e quello di destinazione non possono combaciare!");
        }
        System.out.println("errore generico");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Something went wrong");
    }

    public ResponseEntity<Map<String, Object>> responseFetchScelte(HttpSession session) {
        Storia storia = (Storia) session.getAttribute("storiaCorrente");
        if (storia == null) {
            System.out.println("Storia non trovata");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scelta> listaScelte = listaScelte(storia);
        if(listaScelte.isEmpty()){
            System.out.println("Scelte non trovate");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        System.out.println("scelte trovate: "+ listaScelte.size());
        for(int i = 0; i<listaScelte.size(); i++){
            System.out.println(listaScelte.get(i).toString());
        }
        responseData.put("listaScelte", listaScelte);
        return ResponseEntity.ok(responseData);
    }

    public boolean registraScelta(Scelta scelta) {
        try {
            System.out.println("fin qui tutto bene");
            sceltaRepository.save(scelta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Scelta> listaScelte(Storia storia){
        return sceltaRepository.findScelteByStoria(storia);
    }
}
