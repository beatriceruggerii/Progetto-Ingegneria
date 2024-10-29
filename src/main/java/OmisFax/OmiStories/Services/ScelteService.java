package OmisFax.OmiStories.Services;

import OmisFax.OmiStories.Entities.Scelta;
import OmisFax.OmiStories.Entities.Scenario;
import OmisFax.OmiStories.Entities.Storia;
import OmisFax.OmiStories.Repositories.SceltaRepository;
import OmisFax.OmiStories.Services.interfaces.IScelteService;
import OmisFax.OmiStories.Services.interfaces.IScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScelteService implements IScelteService {
    @Autowired
    private SceltaRepository sceltaRepository;
    @Autowired
    private IScenarioService scenarioService;

    public Map<String, Object> responseFetchScelte(Storia storia){
        if (storia == null) {
            throw new IllegalArgumentException("Storia non trovata");
        }
        Map<String, Object> responseData = new HashMap<>();
        List<Scelta> listaScelte = findByStoria(storia);
        if(listaScelte.isEmpty()){
            throw new IllegalArgumentException("Scelte non trovate");
        }
        System.out.println("scelte trovate: "+ listaScelte.size());
        for(int i = 0; i<listaScelte.size(); i++){
            System.out.println(listaScelte.get(i).toString());
        }
        responseData.put("listaScelte", listaScelte);
        return responseData;
    }

    public List<Scelta> findByStoria(Storia storia){
        return sceltaRepository.findScelteByStoria(storia);
    }

    public Map<String, Object> fetchScelteScenario(Long idScenario) {
        Scenario scenario = scenarioService.findById(idScenario);
        List<Scelta> scelte = sceltaRepository.findByScenarioMadre(scenario);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("scelte", scelte);
        return responseData;
    }
}
