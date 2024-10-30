package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Indovinello;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.IndovinelloRepository;
import OmisFax.OmiStories.Services.interfaces.IIndovinelliService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndovinelliService implements IIndovinelliService {
    @Autowired
    private IndovinelloRepository indovinelloRepository;

    @Autowired
    private IScenarioService scenarioService;

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

    public List<Indovinello> findByStoria(Storia storia) {
        return indovinelloRepository.findByStoria(storia);
    }
    public List<Indovinello> findByScenarioMadre(long idScenario){
        Scenario scenario = scenarioService.findById(idScenario);
        return indovinelloRepository.findByScenarioMadre(scenario);
    }
}
